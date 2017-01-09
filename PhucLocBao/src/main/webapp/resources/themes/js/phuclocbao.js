var Phuclocbao = Phuclocbao || {};
Phuclocbao.GlobalVar=Phuclocbao.GlobalVar || {};
Phuclocbao.GlobalVar.Enums = Phuclocbao.GlobalVar.Enums || {}; 
StateEnum = {
    CHECKED : "Y",
    UNCHECKED : "N"
};
Phuclocbao.GlobalVar.Enums.ContractState = {
		IN_PROGRESS:"IN_PROGRESS",
		FINISH:"FINISH",
		BAD:"BAD"
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
function initNewContractPage(){
	initInput();
	initNewContractPageButtons();
	 populatePaymentSchedules();
	 initPaymentPopup();
	 initContractPopup();
	 initInputEvent();
}
function showhideAvailableContractPanel(){
	if($("#plbContractPanel").html().trim() != ""){
		$("#plbAvailableContract").show();
	} else{
		$("#plbAvailableContract").hide();
	}
}
function initInput(){
	 $("#totalAmount").autoNumeric("init", {
	        aSep: '.',
	        aDec: ',', 
	        pSign: 's',
	        aSign: ' VNĐ',
	        vMin: 0, 
	        vMax: 9999999999
	    });
		 $("#periodOfPayment").autoNumeric("init", {
		        aSep: '.',
		        aDec: ',', 
		        pSign: 's',
		        vMin: 0, 
		        vMax: 30
		    });
		 $("#feeADay").autoNumeric("init", {
		        aSep: '.',
		        aDec: ',', 
		        pSign: 's',
		        aSign: ' VNĐ',
		        vMin: 0, 
		        vMax: 999999999
		    });
		 $("#customerBirthYear").autoNumeric("init", {
		        aSep: '',
		        aDec: '.', 
		        vMin: 0, 
		        vMax: 9999
		    });
		//$("#periodOfPayment").inputmask('Regex', { regex: "^[1-2][0-9]?$|^30$", rightAlign: true, "oncomplete": function(){ console.log('inputmask complete'); }})
		$( "#startDate" ).datepicker({
		    format: 'dd/mm/yyyy',
		    todayHighlight: true,
		    autoclose:true,
		    language: 'vi'
		});
		$( "#expireDate" ).datepicker({
			format: 'dd/mm/yyyy',
		    autoclose:true,
		    language: 'vi'
		});
		$("#startDate").inputmask("99/99/9999",{ "oncomplete": function(){ console.log('inputmask complete'); } });
		$("#customerIdNo").autocomplete({
			source: function( request, response ) {
				var search = {}
				search["customerId"] = $("#customerIdNo").val();
				Phuclocbao.GlobalVar.customerData = undefined;
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "search/getContract",
					data : JSON.stringify(search),
					dataType : 'json',
					timeout : 100000,
					success : function(data) {
						Phuclocbao.GlobalVar.customerData = data;
						response($.map(data.customers, function (item) {
				            return {
				                label: item.idNo,
				                value: item.idNo
				            };
				        }));
					},
					error : function(e) {
						console.log("ERROR: ", e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});

		      },
		      minLength: 2,
		      select: function( event, ui ) {
		    	  var selectedCustomer = findSelectedCustomer(ui.item.value);
		    	  if(selectedCustomer != undefined){
		    		  $("#customerName").val(selectedCustomer.name);
		    		  $("#customerBirthYear").autoNumeric('set', selectedCustomer.birthYear);
		    		  $("#customerPhone").val(selectedCustomer.phone);
		    		  $("#customerAddress").val(selectedCustomer.address);
		    		  $("#customerProvince").val(selectedCustomer.province);
		    	  }
		    	  var selectedContract = findSelectedCustomerContract(ui.item.value);
		    	  if(selectedContract != undefined){
		    		  var text = "";
		    		  for(var i = 0; i < selectedContract.contracts.length; i++){
		    			  var contract = selectedContract.contracts[i];
		    			  text += "<div class='group width-100p'>";
		    			  text +=   "<div class='list-group-item list-group-item-"+getContractStateClass(contract.state)+" '>"+getContractStateLabel(contract.state)+"</div>";
		    			  text +=   "<div class='list-group-item-label'><a class='list-group-link' data-toggle='modal' data-target='#contractDetail'  href='#' data-id='"+contract.id+"'>Hợp đồng ngày " +contract.startDate+ "</a><br></div>";
		    			  text += "</div>";
		    		  }
		    		  $("#plbContractPanel").html(text);
		    		  $("#plbAvailableContract").show();
		    	  }
		        console.log( "Selected: " + ui.item.value + " aka " + ui.item.id );
		      }
		});
		
}
function getContractStateClass(state){
	if(Phuclocbao.GlobalVar.Enums.ContractState.IN_PROGRESS == state){
		return "warning"
	} else if(Phuclocbao.GlobalVar.Enums.ContractState.FINISH == state){
		return "success"
	}else if(Phuclocbao.GlobalVar.Enums.ContractState.BAD == state){
		return "danger"
	}
}
function getContractStateLabel(state){
	if(Phuclocbao.GlobalVar.Enums.ContractState.IN_PROGRESS == state){
		return "HĐ Mới"
	} else if(Phuclocbao.GlobalVar.Enums.ContractState.FINISH == state){
		return "HĐ Cũ"
	}else if(Phuclocbao.GlobalVar.Enums.ContractState.BAD == state){
		return "HĐ Xấu"
	}
}
function findSelectedCustomer(idNo){
	if(Phuclocbao.GlobalVar.customerData != undefined){
		var customers = Phuclocbao.GlobalVar.customerData.customers;
		for(var i = 0; i < customers.length; i++){
			if(customers[i].idNo == idNo){
				return customers[i];
			}
		}

	}
	return undefined;
}
function findSelectedCustomerContract(idNo){
	if(Phuclocbao.GlobalVar.customerData != undefined){
		var customers = Phuclocbao.GlobalVar.customerData.customerContracts;
		for(var i = 0; i < customers.length; i++){
			if(customers[i].idNo == idNo){
				return customers[i];
			}
		}

	}
	return undefined;
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
		var period = parseInt(periodString) - 1;
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
				if(periodOfPayments.length == 1){
					period++;
				}
			}while (!stop);
			var objTemp = createPaymentSchedule(expireDate.toString(dateFormat),"",StateEnum.UNCHECKED);
			periodOfPayments.push(objTemp);
			return periodOfPayments;
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
function initContractPopup(){
	$('#contractDetail').on('shown.bs.modal', function (event) {
		var linkTag = $(event.relatedTarget);
		var contractId = $(linkTag).attr('data-id');
		if(contractId == ""){
			return;
		}
		var search = {}
		search["contractId"] = contractId;
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "search/getContractDetail",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("Contract" + data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
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

function hideAlert(id){
	$("#"+id).fadeTo(2000, 500).slideUp(500, function(){
        $("#"+id).slideUp(500);
         });   
     
}
