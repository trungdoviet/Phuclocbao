StateEnum = {
    CHECKED : "Y",
    UNCHECKED : "N"
};
function initDateLocally(){
	$.fn.datepicker.dates['vi'] = {
		    days: ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"],
		    daysShort: ["CN", "Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy"],
		    daysMin: ["CN", "T2", "T3", "T4", "T5", "T6", "T7"],
		    months: ["Tháng một", "Tháng hai", "Tháng ba", "Tháng tư", "Tháng năm", "Tháng sáu", "Tháng bảy", "Tháng tám", "Tháng chín", "Tháng mười", "Tháng mười một", "Tháng mười hai"],
		    monthsShort: ["Một", "Hai", "Ba", "Bốn", "Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một", "Mười hai"],
		    today: "Hôm nay"
		};
}
function initInputEvent(){
	$("#periodOfPayment").change(function(){
		$("#payDateHidden").val("");
		populatePaymentSchedules();
    });
	$("#startDate").change(function(){
		$("#payDateHidden").val("");
		populatePaymentSchedules();
    });
	$("#expireDate").change(function(){
		$("#payDateHidden").val("");
		populatePaymentSchedules();
    });
}
function populatePaymentSchedules(){
	var savedPaymentValue = $("#payDateHidden").val();
	var periodOfPayments = [];
	if(savedPaymentValue != "" && savedPaymentValue != undefined){
		periodOfPayments = parseSchedulePaymentFromString(savedPaymentValue);
	} else {
		periodOfPayments = calculatePaymentSchedule();
	}
	if(periodOfPayments != undefined){
		var length = periodOfPayments.length;
		var content = "";
		for (i = 0; i < length; i++) {
			content +="<div class='funkyradio-success'>";
			content +="<input type='checkbox' name='checkbox"+(i+1)+"'" + "id='checkbox"+(i+1)+"' "+ "data-toggle='modal' data-target='#paymentModal' "+ "data-expectedPayDate='"+periodOfPayments[i].expectedPayDate +"' "+ "data-payDate='"+periodOfPayments[i].payDate +"' "+getStateByCode(periodOfPayments[i].state)+" " +getDisabledStateByCode(periodOfPayments[i].state)+"/>";
			content +="<label for='checkbox"+(i+1)+"'" + ">Trả phí ngày " + "<b>"+ periodOfPayments[i].expectedPayDate +"</b>" + "<span class='paymsgchk' id='paymsg-checkbox"+(i+1)+"'>"+ getPaymentStringByDate(periodOfPayments[i].payDate) +"</span>" +"</label>";
			content +="</div>";
		}
		$("#paymentPeriodPanel").html(content);
	}
}
function getStateByCode(code){
	if(code == StateEnum.CHECKED){
		return "checked";
	}
	return "";
}

function getDisabledStateByCode(code){
	if(code == StateEnum.CHECKED){
		return "disabled";
	}
	return "";
}

function getPaymentStringByDate(payDate){
	if(payDate != "" && payDate != "nil"){
		return "- Đã thanh toán vào ngày " + payDate;
	}
	return "";
}
function calculatePaymentSchedule(){
	var startDateString = $( "#startDate" ).val();
	var expireDateString = $( "#expireDate" ).val();
	var periodString = $("#periodOfPayment").val();
	var dateFormat = "dd/MM/yyyy";
	if(periodString != ""){
		var startDate = Date.parseExact ( startDateString,dateFormat);
		var expireDate = Date.parseExact ( expireDateString,dateFormat);
		var period = parseInt(periodString);
		var periodOfPayments = [];
		if(startDate.compareTo(expireDate) <= 0){
			var nextPayDay = startDate;
			var stop = false;
			do {
				nextPayDay = nextPayDay.addDays(period);
				if(nextPayDay.compareTo(expireDate) < 0){
					var obj = createPaymentSchedule(nextPayDay.toString(dateFormat),"",StateEnum.UNCHECKED);
					periodOfPayments.push(obj);
				} else {
					stop = true;
				}
			}while (!stop);
			var objTemp = createPaymentSchedule(expireDate.toString(dateFormat),"",StateEnum.UNCHECKED);
			periodOfPayments.push(objTemp);
			return periodOfPayments;
			console.log("5555:" + periodOfPayments);
		}
	}
	return undefined;
}

function createPaymentSchedule(expectedDateString, dateString,stateString){
	return {expectedPayDate:expectedDateString, payDate:dateString, state:stateString};
}

function initNewContractPageButtons(){
	$( "#btnNewContract" ).on( "click", function() {
		 var totalAmount = $("#totalAmount").autoNumeric("get");
		 $("#totalAmount").val(totalAmount);
		 
		 var feeAday =  $("#feeADay").autoNumeric("get");
		 $("#feeADay").val(feeAday);
		 
		 collectPaymentSchedule();
	});
}
function initPaymentPopup(){
	$('#paymentModal').on('shown.bs.modal', function (event) {
		var dateFormat = "dd/MM/yyyy"; 
		var checkbox = $(event.relatedTarget);
		 var selected = $(checkbox).is(':checked');
		 $(checkbox).prop('checked', !selected);
		 $("#paymentDateIndentity").val($(checkbox).attr('id'));
		 var expectedPayDate = $(checkbox).attr("data-expectedPayDate");
		$("#expectedPayDateString").text(expectedPayDate);
		var lastPaymentDateIndex = $(checkbox).parent().prevAll().length - findLastPaymentDateIndex();
		if(lastPaymentDateIndex >= 0){
			$("#amountSession").text("(" + (lastPaymentDateIndex+1) +" Kỳ): ");
		} else {
			$("#amountSession").text(":");
		}
		var lastPaymentDate = findLastPaymentDate();
		var startDateString = $( "#startDate" ).val();
		var startCalculationDate = undefined;
		if(lastPaymentDate != ""){
			startCalculationDate = Date.parseExact ( lastPaymentDate,dateFormat);
		} else {
			startCalculationDate = Date.parseExact ( startDateString,dateFormat);
		}
		var endCalculationDate =  Date.parseExact ( expectedPayDate,dateFormat);
		if(startCalculationDate.compareTo(endCalculationDate) <= 0){
			var numberOfDay = (endCalculationDate - startCalculationDate)/(24*60*60*1000);
			var feeAday =  $("#feeADay").autoNumeric("get");
			if(numberOfDay > 0 && feeAday > 0 ){
				var paymentAmount = numberOfDay*feeAday;
				var numberConf = {
				        aSep: '.',
				        aDec: ',', 
				        pSign: 's',
				        aSign: ' VNĐ',
				        vMin: 0, 
				        vMax: 99999999999
				};
				
				$("#totalPaidAmount").text($.fn.autoFormat(paymentAmount, numberConf));
			}else {
				$("#totalPaidAmount").text("Không xác định");
			}
		}else{
			$("#totalPaidAmount").text("Không xác định");
		}
	 });
	
	$("#paymentOk").on( "click", function() {
		var payDateId = $("#paymentDateIndentity").val();
		if(payDateId != "") {
			 var selected = $("#"+payDateId).is(':checked');
			 if(!selected){
				 var nowDateString = Date.now().toString("dd/MM/yyyy");
				 $("#"+payDateId).attr("data-paydate", nowDateString);
				 $("#paymsg-"+payDateId).text(getPaymentStringByDate(nowDateString));
				 $("#"+payDateId).attr("disabled", "")
				 selectPreviousPaymentDate($("#"+payDateId), nowDateString);
			 }else{
				 $("#"+payDateId).attr("data-paydate", "");
			 }
			 $("#"+payDateId).prop('checked', !selected);
			 
		}
		$('#paymentModal').modal('hide');
	});
	$("#paymentClose").on( "click", function() {
		 $("#paymentDateIndentity").val("");
	});
}

function selectPreviousPaymentDate(currentDateObj, nowDateString){
	var parentItems = currentDateObj.parent().prevAll();
	if(parentItems.length > 0){
		parentItems.each(function(){
			var input = $(this).find("input[type='checkbox']");
			 var selected = $(input).is(':checked');
			 if(!selected){
				 $(input).attr("data-paydate", nowDateString);
				 var inputId = $(input).attr("id");
				 $("#paymsg-"+inputId).text(getPaymentStringByDate(nowDateString));
				 $(input).attr("disabled", "")
				 $(input).prop('checked', true);
			 }
		});
	}
}

function collectPaymentSchedule(){
	var checkboxes = $("#paymentPeriodPanel").find("input[type='checkbox']");
	var paidDate = "";
	if(checkboxes.length > 0){
		checkboxes.each(function(){
			var expectedPaid = $(this).attr("data-expectedPayDate");
			paidDate+=expectedPaid;
			if($(this).is(':checked')){
				paidDate+=":"+StateEnum.CHECKED;
				var paid = $(this).attr("data-payDate");
				paidDate+=":"+paid;
			} else {
				paidDate+=":"+StateEnum.UNCHECKED;
				paidDate+=":nil";
			}
			paidDate+=",";
		});
	}
	$("#payDateHidden").val(paidDate);
}

function findLastPaymentDate(){
	var checkboxes = $("#paymentPeriodPanel").find("input[type='checkbox']");
	if(checkboxes.length > 0){
		var lastPaymentDate = "";
		checkboxes.each(function(){
			var expectedPaid = $(this).attr("data-expectedPayDate");
			if($(this).is(':checked')){
				lastPaymentDate = expectedPaid;
			} else {
				return lastPaymentDate;
			}
		});
		return lastPaymentDate;
	}
}

function findLastPaymentDateIndex(){
	var checkboxes = $("#paymentPeriodPanel").find("input[type='checkbox']");
	if(checkboxes.length > 0){
		var lastPaymentDateIndex = 0;
		checkboxes.each(function(){
			var expectedPaid = $(this).attr("data-expectedPayDate");
			if($(this).is(':checked')){
				lastPaymentDateIndex++;
			} else {
				return lastPaymentDateIndex;
			}
		});
		return lastPaymentDateIndex;
	}
}

function parseSchedulePaymentFromString(paymentString){
	if(paymentString != ""){
		var items = paymentString.split(",");
		var periodOfPayments = [];
		for(var i = 0; i < items.length; i++){
			var details = items[i].split(":");
			if(details.length == 3){
				periodOfPayments.push(createPaymentSchedule(details[0], details[2], details[1]));
			}
		}
		return periodOfPayments;
	}
	return undefined;
}
