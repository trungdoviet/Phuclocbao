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

function populatePaymentSchedules(){
	var periodOfPayments = calculatePaymentSchedule();
	if(periodOfPayments != undefined){
		var length = periodOfPayments.length;
		var content = "";
		for (i = 0; i < length; i++) {
			content +="<div class='funkyradio-success'>";
			content +="<input type='checkbox' name='checkbox"+(i+1)+"'" + "id='checkbox"+(i+1)+"' "+ "data-toggle='modal' data-target='#paymentModal' "+ "data-payDate='"+periodOfPayments[i] +"'/>";
			content +="<label for='checkbox"+(i+1)+"'" + ">Trả phí ngày " + "<b>"+ periodOfPayments[i] +"</b>" + "</label>";
			content +="</div>";
		}
		$("#paymentPeriodPanel").html(content);
	}
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
					periodOfPayments.push(nextPayDay.toString(dateFormat));
				} else {
					stop = true;
				}
			}while (!stop);
			periodOfPayments.push(expireDate.toString(dateFormat));
			return periodOfPayments;
			console.log("5555:" + periodOfPayments);
		}
	}
	return undefined;
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
		 var checkbox = $(event.relatedTarget);
		 var selected = $(checkbox).is(':checked');
		 $(checkbox).prop('checked', !selected);
		 $("#paymentDateIndentity").val($(checkbox).attr('id'));
		 var payDate = $(checkbox).attr("data-payDate");
		$("#payDateString").text(payDate); 
	 });
	
	$("#paymentOk").on( "click", function() {
		var payDateId = $("#paymentDateIndentity").val();
		if(payDateId != "") {
			 var selected = $("#"+payDateId).is(':checked');
			 $("#"+payDateId).prop('checked', !selected);
		}
		$('#paymentModal').modal('hide');
	});
	$("#paymentClose").on( "click", function() {
		 $("#paymentDateIndentity").val("");
	});
}

function collectPaymentSchedule(){
	var checkboxes = $("#paymentPeriodPanel").find("input[type='checkbox']");
	var paidDate = "";
	if(checkboxes.length > 0){
		checkboxes.each(function(){
			var paid = $(this).attr("data-payDate");
			paidDate+=paid;
			if($(this).is(':checked')){
				paidDate+=":Y";
			} else {
				paidDate+=":N";
			}
			paidDate+=",";
		});
	}
	$("#payDateHidden").val(paidDate);
}
