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

var defaultNumberConf =  {
        aSep: '.',
        aDec: ',', 
        pSign: 's',
        aSign: ' VNĐ',
        vMin: 0, 
        vMax: 9999999999
    };
var defaultAllRangeNumber = {
        aSep: '.',
        aDec: ',', 
        pSign: 's',
        aSign: ' VNĐ',
        vMin: -9999999999, 
        vMax: 9999999999
    };
var isProcessingRequest = false;
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
	initDateLocally();
	initInput();
	initNewContractPageButtons();
	 populatePaymentSchedules();
	 initPaymentPopup();
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
	 var unExponentialAmount = parseFloat($("#totalAmount").attr("value"));
	 $("#totalAmount").attr("value", unExponentialAmount);
	 $("#totalAmount").autoNumeric("init",defaultNumberConf);
		 $("#periodOfPayment").autoNumeric("init", {
		        aSep: '.',
		        aDec: ',', 
		        pSign: 's',
		        vMin: 0, 
		        vMax: 30
		    });
		 $("#feeADay").autoNumeric("init", defaultNumberConf);
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
		
		var processStage = $("#processStagingHidden").val();
		if(processStage == "paid"){
			 $("#customerDebt").autoNumeric("init", defaultAllRangeNumber);
			 $("#companyDebt").autoNumeric("init", defaultAllRangeNumber);
		}
		if(processStage == "payoff"){
			var payoffDate = $( "#payoffDate" );
			if(payoffDate != undefined) {
				var startCalDateString = findLastPaymentDate();
				if(startCalDateString == "" || startCalDateString == undefined){
					startCalDateString = $( "#startDate" ).val();
				}
				var dateFormat = "dd/MM/yyyy";
				if(startCalDateString != ""){
					var startCalDate = Date.parseExact ( startCalDateString,dateFormat);
					payoffDate.datepicker({
					    format: 'dd/mm/yyyy',
					    todayHighlight: true,
					    autoclose:true,
					    startDate: startCalDate,
					    language: 'vi'
					});
				} else {
					payoffDate.datepicker({
								    format: 'dd/mm/yyyy',
								    todayHighlight: true,
								    autoclose:true,
								    language: 'vi'
								});
				}
			}
		 $("#customerDebt").autoNumeric("init",defaultAllRangeNumber);
		 $("#companyDebt").autoNumeric("init", defaultAllRangeNumber);
		}
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
		    			  text +=   "<div class='list-group-item-label'><a class='list-group-link' onclick='"+_getContractFunc(contract.id)+"'  href='#' data-id='"+contract.id+"'>Hợp đồng ngày " +contract.startDate+ "</a><br></div>";
		    			  text += "</div>";
		    		  }
		    		  $("#plbContractPanel").html(text);
		    		  $("#plbAvailableContract").show();
		    	  }
		        console.log( "Selected: " + ui.item.value + " aka " + ui.item.id );
		      }
		});
		
}
function _getContractFunc(id){
	return "openContractDetail("+id+")";
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
	var processStage = $("#processStagingHidden").val();
	if(processStage == "update") return;
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
			content +="<label for='checkbox"+(i+1)+"'" + ">Trả phí ngày " + "<b>"+ periodOfPayments[i].expectedPayDate +"</b>" + "<span class='"+getPaymentStyleClassByDate(periodOfPayments[i].payDate)+"' id='paymsg-checkbox"+(i+1)+"'>"+ getPaymentStringByDate(periodOfPayments[i].payDate) +"</span>" +"</label>";
			content +="</div>";
		}
		$("#paymentPeriodPanel").html(content);
	}else {
		$("#paymentPeriodPanel").html("");
	}
}
function getStateByCode(code){
	if(code == StateEnum.CHECKED){
		return "checked";
	}
	return "";
}

function getDisabledStateByCode(code){
	var processStage = $("#processStagingHidden").val();
	if(code == StateEnum.CHECKED || processStage == "payoff"){
		return "disabled";
	}
	return "";
}

function getPaymentStringByDate(payDate){
	var processStage = $("#processStagingHidden").val();
	
	if(payDate != "" && payDate != "nil"){
		return "- Đã thanh toán vào ngày " + payDate;
	} else if (processStage == "payoff"){
		return " - Chưa thanh toán";
	}
	return "";
}

function getPaymentStyleClassByDate(payDate){
	var processStage = $("#processStagingHidden").val();
	
	if(payDate != "" && payDate != "nil"){
		return "paymsgchk";
	} else if (processStage == "payoff"){
		return "paymsguchk";
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
	$( "#btnNewContract" ).off( "click");
	$( "#btnNewContract" ).on( "click", function() {
		 var totalAmount = $("#totalAmount").autoNumeric("get");
		 $("#totalAmount").val(totalAmount);
		 
		 var feeAday =  $("#feeADay").autoNumeric("get");
		 $("#feeADay").val(feeAday);
		 
		 collectPaymentSchedule();
	});
}

function initContractPageButtons(){
	$( "#btnSaveContract" ).off( "click");
	$( "#btnSaveContract" ).on( "click", function() {
		 ctr_updateNumber();
		 collectPaymentSchedule();
	});
	
	var btnPayoffContractOk = $("#btnPayoffContractOk");
	if(btnPayoffContractOk != null && btnPayoffContractOk != undefined){
		btnPayoffContractOk.off( "click");
		btnPayoffContractOk.on( "click", function() {
			var totalMoneyRefundingAmount = parseFloat($("#totalMoneyRefundingAmount").attr("refunding"))
			if(totalMoneyRefundingAmount < 0){
				$("#payOffCustomerMsg").show();
				$("#payOffCompanyMsg").hide();
			} else {
				$("#payOffCompanyMsg").show();
				$("#payOffCustomerMsg").hide();
			}
			$("#totalRefundAmount").text($("#totalMoneyRefundingAmount").text().replace("-",""));
			$("#payOffOk").off("click");
			$("#payOffOk").on("click", function(){
				$("#btnPayoffContract").trigger("click");
			});
			$("#payOffModal").modal("show");
		});
	}
	var processStage = $("#processStagingHidden").val();
   if(processStage == "payoff"){
		$( "#btnPayoffContract" ).off( "click");
		$( "#btnPayoffContract" ).on( "click", function() {
			ctr_updateNumber();
		});
		$( "#btnSaveAsDraftContract" ).off( "click");
		$( "#btnSaveAsDraftContract" ).on( "click", function() {
			ctr_updateNumber();
		});
		
		$("#payoffDate").off("change");
		$("#payoffDate").change(function(){ 
			var startCalDateString = findLastPaymentDate();
			if(startCalDateString == "" || startCalDateString == undefined){
				startCalDateString = $( "#startDate" ).val();
			}
			var endCalDateString = $( "#payoffDate" ).val();
			
			var dateFormat = "dd/MM/yyyy";
			if(endCalDateString != ""){
				var startCalDate = Date.parseExact ( startCalDateString,dateFormat);
				var endCalDate = Date.parseExact ( endCalDateString,dateFormat);
				var difDay = Math.abs(endCalDate - startCalDate)/(1000*3600*24);
				var refundMinus = 1;
				if(endCalDate - startCalDate > 0){
					refundMinus = -1;
				}
				if(difDay >= 0){ //if date is valid
					var feeAdayValue =  parseFloat($("#feeADay").autoNumeric("get"));
					var customerDebtValue = parseFloat($("#companyDebt").autoNumeric("get"));
					var companyDebtValue = parseFloat($("#customerDebt").autoNumeric("get"));
					var totalRefund = (difDay * feeAdayValue) *refundMinus ;
					$("#totalMoneyRefundingAmount").attr('refunding', totalRefund);
					totalRefund = totalRefund - customerDebtValue + companyDebtValue
					$("#totalMoneyRefundingAmount").text(ctr_formatNumeric(totalRefund));
				}
				
			}
		});
	} else if (processStage == "update"){
		$( "#btnSaveOldContract" ).off( "click");
		$( "#btnSaveOldContract" ).on( "click", function() {
			ctr_updateNumber();
		});
	}
}
function ctr_updateNumber(){
	 var totalAmount = $("#totalAmount").autoNumeric("get");
	 $("#totalAmount").val(totalAmount);
	 
	 var feeAday =  $("#feeADay").autoNumeric("get");
	 $("#feeADay").val(feeAday);
	 var companyDebt = $("#companyDebt").autoNumeric("get");
	$("#companyDebt").val(companyDebt);
	var customerDebt = $("#customerDebt").autoNumeric("get");
	$("#customerDebt").val(customerDebt);
}

function openContractDetail(contractId){
	if(contractId == ""){
		return false;
	}
	var search = {};
	search["contractId"] = contractId;
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "search/getContractDetail",
		data : JSON.stringify(search),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log(data);
			populateContractDetail(data);
		},
		error : function(e) {
			
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}
function populateContractDetail(data){
	if(data.code == null){
		var numberConf = {
		        aSep: '.',
		        aDec: ',', 
		        pSign: 's',
		        aSign: ' VNĐ',
		        vMin: 0, 
		        vMax: 99999999999
		};
		var contract = data.contract;
		//customer
		$("#cdCustomerName").text(contract.customerView.name);
		$("#cdCustomerPhone").text(contract.customerView.phone);
		$("#cdIdNo").text(contract.customerView.idNo);
		$("#cdCustomerProvince").text(contract.customerView.province);
		$("#cdCustomerBirthyear").text(contract.customerView.birthYear);
		$("#cdCustomerAddress").text(contract.customerView.address);
		//contract
		$("#cdCompanyName").text(contract.companyName);
		$("#cdCompanyPhone").text(contract.phone);
		$("#cdContractType").text(contract.contractType);
		$("#cdExpireDate").text(contract.expireDate);
		$("#cdStartDate").text(contract.startDate);
		$("#cdTotalAmount").text($.fn.autoFormat(contract.totalAmount,numberConf));
		if(contract.state == 'FINISH'){
			$("#cdPayoffDate").text(contract.payOffDate);
			$("#payoffDatePanel").show();
		} else {
			$("#payoffDatePanel").hide();
		}
		if(contract.state == 'FINISH'){
			$("#labelInprogress").hide();
			$("#labelFinish").show();
			$("#labelBad").hide();
		} else if(contract.state == 'IN_PROGRESS'){
			$("#labelInprogress").show();
			$("#labelFinish").hide();
			$("#labelBad").hide();
		} else if(contract.state == 'BAD'){
			$("#labelInprogress").hide();
			$("#labelFinish").hide();
			$("#labelBad").show();
		}
		var payments = contract.paymentScheduleDetails;
		var paymentText = "";
		for(var i = 0; i < payments.length; i++){
			paymentText += "<div>"
			paymentText += payments[i];
			paymentText += "</div>"
		}
		$("#cdPaymentSchedule").html(paymentText);
		//additional info
		$("#cdTransportType").text(contract.propertyDetail.type);
		$("#cdNumberPlate").text(contract.propertyDetail.numberPlate);
		$("#cdChassisFrameNumber").text(contract.propertyDetail.chassisFrameNumber);
		$("#cdChassisNumber").text(contract.propertyDetail.chassisNumber);
		$('#contractDetail').modal("show");
	} else {
		showErrorDialog(msg);
	}
}
function showErrorDialog(msg){
	$("#messageContent").text("Đã có lỗi xảy ra:" + msg);
	$("#errorModal").modal("show");
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
		var extraDay = 0;
		var startDateString = $( "#startDate" ).val();
		var startCalculationDate = undefined;
		if(lastPaymentDate != ""){
			startCalculationDate = Date.parseExact ( lastPaymentDate,dateFormat);
		} else {
			extraDay = 1;
			startCalculationDate = Date.parseExact ( startDateString,dateFormat);
		}
		var endCalculationDate =  Date.parseExact ( expectedPayDate,dateFormat);
		if(startCalculationDate.compareTo(endCalculationDate) <= 0){
			var numberOfDay = ((endCalculationDate - startCalculationDate))/(24*60*60*1000) + extraDay;
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
	$("#paymentOk").off( "click");
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
	$("#paymentClose").off( "click");
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


function mc_contractDetail(id){
	console.log(id);
}

function ctr_openCustomerDebtDialog(){
	 $("#customerDebtInput").autoNumeric("init", {
	        aSep: '.',
	        aDec: ',', 
	        pSign: 's',
	        aSign: ' VNĐ',
	        vMin: 0, 
	        vMax: 9999999999
	    });
	 $("#customerDebtInput").autoNumeric("set", 0);
	 $("#addCustomerDebtOk").off( "click");
	 $("#addCustomerDebtOk").on( "click", function() {
			var customerDebt = parseFloat($("#customerDebtInput").autoNumeric("get"));
			ctr_calCustomerDebt(customerDebt, true);
			
		});
	 $("#subtractCustomerDebtOk").off("click");
	 $("#subtractCustomerDebtOk").on( "click", function() {
			var customerDebt = parseFloat($("#customerDebtInput").autoNumeric("get"));
			ctr_calCustomerDebt(customerDebt, false);
		});
	$("#addCustomerDebt").modal("show");
}

function ctr_formatNumeric(value){
	var numberConf = {
	        aSep: '.',
	        aDec: ',', 
	        vMin: -99999999999, 
	        vMax: 99999999999
	};
	return $.fn.autoFormat(value,numberConf); 
	
}

function ctr_openCompanyDebtDialog(){
	 $("#addCompanyDebtInput").autoNumeric("init", {
	        aSep: '.',
	        aDec: ',', 
	        pSign: 's',
	        aSign: ' VNĐ',
	        vMin: 0, 
	        vMax: 9999999999
	    });
	 $("#addCompanyDebtInput").autoNumeric("set", 0);
	 $("#addCompanyDebtOk").off( "click");
	 $("#subtractCompanyDebtOk").off("click");
	 $("#addCompanyDebtOk").on( "click", function() {
			var companyDebt = parseFloat($("#addCompanyDebtInput").autoNumeric("get"));
			ctr_calcCompanyDebt(companyDebt, true);
			$('#addCompanyDebt').modal('hide');
		});
	 $("#subtractCompanyDebtOk").on( "click", function() {
			var companyDebt = -parseFloat($("#addCompanyDebtInput").autoNumeric("get"));
			ctr_calcCompanyDebt(companyDebt, false);
			$('#addCompanyDebt').modal('hide');
		});
	$("#addCompanyDebt").modal("show");
}
function ctr_calTotalRefunding(){
	var processStage = $("#processStagingHidden").val();
	if(processStage == "payoff"){
		var customerDebt = parseFloat($("#customerDebt").autoNumeric("get"));
		var companyDebt =  parseFloat($("#companyDebt").autoNumeric("get"));
		 var totalRefunding = parseFloat($("#totalMoneyRefundingAmount").attr('refunding'));
		 totalRefunding = totalRefunding - customerDebt + companyDebt;
		 $("#totalMoneyRefundingAmount").text(ctr_formatNumeric(totalRefunding));
	}
}
function ctr_calCustomerDebt(customerDebt, isIncrease){
	if(customerDebt != 0) {
		var currentDebt = parseFloat($("#customerDebt").autoNumeric("get"));
		if(isIncrease){
			currentDebt = currentDebt + customerDebt;
		} else {
			currentDebt = currentDebt - customerDebt;
		}
		$("#customerDebt").autoNumeric('set', currentDebt);
		ctr_calTotalRefunding();
	}
	$('#addCustomerDebt').modal('hide');
	return;
}

function ctr_calcCompanyDebt(companyDebt, isIncrease){
	if(companyDebt != 0) {
		var currentDebt = parseFloat($("#companyDebt").autoNumeric("get"));
		if(isIncrease){
			currentDebt = currentDebt + companyDebt;
		} else {
			currentDebt = currentDebt + companyDebt;
		}
		$("#companyDebt").autoNumeric('set', currentDebt);
		ctr_calTotalRefunding();
	}
	return;
}
function nc_initNotificationPage(){
	 initDateLocally();
	$( "#selectedDate" ).datepicker({
	    format: 'dd/mm/yyyy',
	    todayHighlight: true,
	    autoclose:true,
	    language: 'vi'
	});
}

function ph_initPage(){
	 initDateLocally();
	 var dateConf = {
			    format: 'dd/mm/yyyy',
			    todayHighlight: true,
			    autoclose:true,
			    language: 'vi'
			};
	 
	$( "#startDateHistory" ).datepicker(dateConf);
	$( "#endDateHistory" ).datepicker(dateConf);
	$( "#createdPaymentDate").datepicker(dateConf);
	var unExponentialAmount = parseFloat($("#paymentAmount").attr("value"));
	 $("#paymentAmount").attr("value", unExponentialAmount);
	 $("#paymentAmount").autoNumeric("init",defaultNumberConf);
	 
	 try{
			$("#newPaymentForm").validate();
		} catch(err) {
		    console.log(err.message);
		}
	$("#paymentAmount").change(function(){
		var totalAmount = $("#paymentAmount").autoNumeric("get");
	     if(totalAmount == 0){
	    	 $("#addMorePaymentOK").prop("disabled",true);
	     } else {
	    	 $("#addMorePaymentOK").prop("disabled",false);
	     }
		
	});
	$( "#btnNewPayment" ).off( "click");
	$( "#btnNewPayment" ).on( "click", function() {
		 $("#newPaymentForm .form-input").val("");
		 $( "#addMorePaymentOK" ).off( "click");
		 $( "#addMorePaymentOK" ).on( "click", function() {
		     var totalAmount = $("#paymentAmount").autoNumeric("get");
		     if(totalAmount == 0){
		    	 $("#paymentAmount").val("");
		     } else {
		    	 $("#paymentAmount").val(totalAmount);
		     }
		  });
		$('#addPayment').modal("show");
	});
}

function uh_initPage(){
	 initDateLocally();
	 var dateConf = {
			    format: 'dd/mm/yyyy',
			    todayHighlight: true,
			    autoclose:true,
			    language: 'vi'
			};
	$( "#startDateUserHistory" ).datepicker(dateConf);
	$( "#endDateUserHistory" ).datepicker(dateConf);
}

function cf_init(){
	cf_initInputs();
	cf_initButtons();
}

function companyProfit_init(){
	$("td").not("[class*='valid']").remove();
	$('[data-toggle="tooltip"]').tooltip(); 
}

function cf_initInputs(){
	initDateLocally();
	$( "#companyStartDate" ).datepicker({
	    format: 'dd/mm/yyyy',
	    todayHighlight: true,
	    autoclose:true,
	    language: 'vi'
	});
	
	var numberConf = {
	        aSep: '.',
	        aDec: ',', 
	        pSign: 's',
	        aSign: ' VNĐ',
	        vMin: 0, 
	        vMax: 999999999999
	    };
	 var unExponentialAmount = parseFloat($("#originalFund").attr("value"));
	 $("#originalFund").attr("value", unExponentialAmount);
	 
	 unExponentialAmount = parseFloat($("#totalFund").attr("value"));
	 $("#totalFund").attr("value", unExponentialAmount);
	 
	 unExponentialAmount = parseFloat($("#revenueBeforeStartDate").attr("value"));
	 $("#revenueBeforeStartDate").attr("value", unExponentialAmount);
	 
	 unExponentialAmount = parseFloat($("#costBeforeStartDate").attr("value"));
	 $("#costBeforeStartDate").attr("value", unExponentialAmount);
	 
	 unExponentialAmount = parseFloat($("#investBeforeStartDate").attr("value"));
	 $("#investBeforeStartDate").attr("value", unExponentialAmount);
	 
	 $("#originalFund").autoNumeric("init", numberConf);
	 $("#totalFund").autoNumeric("init", numberConf);
	 $("#revenueBeforeStartDate").autoNumeric("init", numberConf);
	 $("#costBeforeStartDate").autoNumeric("init", numberConf);
	 $("#investBeforeStartDate").autoNumeric("init", numberConf);
}

function cf_initButtons(){
	$( "#btnSaveCompanyFinancial" ).off( "click");
	$( "#btnSaveCompanyFinancial" ).on( "click", function() {
		 var originalFund = $("#originalFund").autoNumeric("get");
		 $("#originalFund").val(originalFund);
		 
		 var totalFund = $("#totalFund").autoNumeric("get");
		 $("#totalFund").val(totalFund);
		 
		 var costBeforeStartDate = $("#costBeforeStartDate").autoNumeric("get");
		 $("#costBeforeStartDate").val(costBeforeStartDate);
		 
		 var revenueBeforeStartDate = $("#revenueBeforeStartDate").autoNumeric("get");
		 $("#revenueBeforeStartDate").val(revenueBeforeStartDate);
		 
		 var investBeforeStartDate = $("#investBeforeStartDate").autoNumeric("get");
		 $("#investBeforeStartDate").val(investBeforeStartDate);
	});
}

function general_formatTotalFunding(){
	var totalFundString = $("#companyTotalAmount").text();
	if(totalFundString != undefined || totalFundString != ''){
		var totalFund = parseFloat(totalFundString);
		var numberConf = {
		        aSep: '.',
		        aDec: ',', 
		        pSign: 's',
		        aSign: ' VNĐ',
		        vMin: -999999999999, 
		        vMax: 999999999999
		    };
		$("#companyTotalAmount").text($.fn.autoFormat(totalFund,numberConf)); 
	}
}

// Digital clock

function initDigitalClock(){
	// Create two variable with the names of the months and days in an array
	var monthNames = [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]; 
	var dayNames= ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"]

	// Create a newDate() object
	var newDate = new Date();
	// Extract the current date from Date object
	newDate.setDate(newDate.getDate());
	// Output the day, date, month and year    
	$('#Date').html(dayNames[newDate.getDay()] + ", " + newDate.getDate() + '/' + monthNames[newDate.getMonth()] + '/' + newDate.getFullYear() +' -');

	setInterval( function() {
		// Create a newDate() object and extract the seconds of the current time on the visitor's
		var seconds = new Date().getSeconds();
		// Add a leading zero to seconds value
		$("#sec").html(( seconds < 10 ? "0" : "" ) + seconds);
		},1000);
		
	setInterval( function() {
		// Create a newDate() object and extract the minutes of the current time on the visitor's
		var minutes = new Date().getMinutes();
		// Add a leading zero to the minutes value
		$("#min").html(( minutes < 10 ? "0" : "" ) + minutes);
	    },1000);
		
	setInterval( function() {
		// Create a newDate() object and extract the hours of the current time on the visitor's
		var hours = new Date().getHours();
		// Add a leading zero to the hours value
		$("#hours").html(( hours < 10 ? "0" : "" ) + hours);
	    }, 1000);
}

function cb_init(){
	$( "#btnNewBranch" ).off( "click");
	$( "#btnNewBranch" ).on( "click", function() {
		 $("#newBranchForm .form-input").val("");
		$('#addCompanyBranch').modal("show");
	});
	$("#companyName").change(function(){
		cb_validate();
	});
	$("#companyType").change(function(){
		cb_validate();
	});
	$("#phoneNumber").change(function(){
		cb_validate();
	});
	
}

function cb_deleteCompany(branchName, url){
	$("#companyNameField").text(branchName);
	$("#deleteCompanyOk").attr("onclick", "location.href='"+url+"'");
	$('#deleteCompanyDlg').modal("show");	
}

function cb_validate(){
	if($("#companyName").val() != "" && $("#companyType").val() != "" 
			&& $("#phoneNumber").val() != ""){
		$("#addBranch").prop("disabled",false);
	} else {
		$("#addBranch").prop("disabled",true);
	}
}

function mu_init(){
	try{
		$("#userForm").validate();
	} catch(err) {
	    console.log(err.message);
	}
	$( "#btnNewUser" ).off( "click");
	$( "#btnNewUser" ).on( "click", function() {
		 $("#userForm .form-input").val("");
		 $("#userCheckingMessage").text("");
		$('#addUser').modal("show");
	});
	$("#username").change(function(){
		var search = {}
		search["username"] = $("#username").val();
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "search/getUser",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				if(data.code == "200" && data.isExist == false){
					$("#addUser").prop("disabled",false);
					$("#userCheckingMessage").removeClass("state_alert");
					$("#userCheckingMessage").removeClass("state_good");
					$("#userCheckingMessage").addClass("state_good");
				} else {
					$("#addUser").prop("disabled",true);
					$("#userCheckingMessage").removeClass("state_alert");
					$("#userCheckingMessage").removeClass("state_good");
					$("#userCheckingMessage").addClass("state_alert");
				}
				$("#userCheckingMessage").text(data.msg);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				$("#addUser").prop("disabled",true);
				$("#userCheckingMessage").removeClass("state_alert");
				$("#userCheckingMessage").removeClass("state_good");
				$("#userCheckingMessage").addClass("state_alert");
				$("#userCheckingMessage").text("Javascript error");
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	});
}

function home_init(){
	var curTime = new Date();
	var curMonth = curTime.getMonth() + 1;
	var curYear = curTime.getFullYear();
	var curTimeStr = curMonth +"/" + curYear;
	$("#curTimeIncome").text(curTimeStr);
	$("#curTimeProfit").text(curTimeStr);
	
	$("#availableYears").change(function(){
		var search = {}
		search["year"] = $("#availableYears").val();
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "search/profitByYear",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				if(data.code == "200" && barChartData.datasets.length == 2){
					barChartData.datasets[0].data = data.statistic.profitByMonth;
					barChartData.datasets[1].data = data.statistic.rentingCostByMonth;
					onLoadChart();
				} else {
					
				}
				
			},
			error : function(e) {
				
			},
			done : function(e) {
				//console.log("DONE");
			}
		});
	});
}

function cp_init(){
	try{
		$.validator.addMethod("passwordMatch", function(value, element) {
		  return this.optional(element) || false;
		},"Mật khẩu không trùng khớp");
		jQuery.validator.classRuleSettings.passwordMatch = { passwordMatch: true };
		$("#changePassForm").validate({
			 rules : {
				 retypePassword : { passwordMatch : true }
			    }
		});
	} catch(err) {
	    console.log(err.message);
	}
}
function cp_openDialog(username, id){
	$('#usernameLabel').text(username);	
	$('#userId').val(id);	
	
	$('#changePassword').modal("show");	
}

function openProfitDetail(companyName, companyId, month){
	var year = $("#profitByYear").val();
	
	$("#profitCompanyName").text(companyName);
	
	companyProfit_setStateNavigator(parseInt(month),parseInt(year));
	
	$("#monthlyProfitBack").off("click");
	$("#monthlyProfitBack").on("click", function(){
		if(isProcessingRequest==true){
			return;
		}
		var prevMonth = companyProfit_getPrevMonth($("#profitMonth").val());
		companyProfit_setStateNavigator(prevMonth, parseInt($("#profitYear").val()));
		if(prevMonth >= 1) {
			$("#profitMonth").val(prevMonth);
			var curMonth = $("#profitMonth").val();
			var curYear = $("#profitYear").val();
			var curComp = $("#profitCompany").val();
			sendProfitDetailAjax(curComp, curYear, curMonth);
		} 
	});
	
	$("#monthlyProfitNext").off("click");
	$("#monthlyProfitNext").on("click", function(){
		if(isProcessingRequest==true){
			return;
		}
		var nextMonth = companyProfit_getNextMonth($("#profitMonth").val());
		companyProfit_setStateNavigator(nextMonth, parseInt($("#profitYear").val()));
		if(nextMonth != -1) {
			$("#profitMonth").val(nextMonth);
			$("#monthlyProfitBack").attr("disabled", false);
			var curMonth = $("#profitMonth").val();
			var curYear = $("#profitYear").val();
			var curComp = $("#profitCompany").val();
			sendProfitDetailAjax(curComp, curYear, curMonth);
		} 
	});
	
	storeProfitKey(companyId, year, month);
	sendProfitDetailAjax(companyId, year, month);
}
function companyProfit_setStateNavigator(curMonth, year){
	if(curMonth == 1){
		$("#btnProfitBack").text("Năm trước");
		$("#btnProfitNext").text("Tháng " + (curMonth +1));
		$("#monthlyProfitNext").attr("disabled", false);
		$("#monthlyProfitBack").attr("disabled", true);
		$("#profitTime").text("tháng "+ curMonth + "/" + year);
	} else if(curMonth == 13){
		$("#btnProfitBack").text("Tháng " + (curMonth -1));
		$("#btnProfitNext").text("Năm sau");
		$("#monthlyProfitNext").attr("disabled", true);
		$("#monthlyProfitBack").attr("disabled", false);
		$("#profitTime").text("năm "+year);
	} else if(curMonth == 12) {
		$("#btnProfitBack").text("Tháng " +(curMonth -1));
		$("#btnProfitNext").text("Cả năm");
		$("#monthlyProfitNext").attr("disabled", false);
		$("#monthlyProfitBack").attr("disabled", false);
		$("#profitTime").text("tháng "+ curMonth + "/" + year);
	} else {
		$("#btnProfitBack").text("Tháng " +(curMonth -1));
		$("#btnProfitNext").text("Tháng " + (curMonth +1));
		$("#monthlyProfitNext").attr("disabled", false);
		$("#monthlyProfitBack").attr("disabled", false);
		$("#profitTime").text("tháng "+ curMonth + "/" + year);
	}
	
}
function companyProfit_getPrevMonth(monthStr){
	var month = parseInt(monthStr);
	if(month - 1 <= 0){
		return -1;
	} else {
		return month-1;
	}
}

function companyProfit_getNextMonth(monthStr){
	var month = parseInt(monthStr);
	if(month + 1 > 13){
		return -1;
	} else {
		return month+1;
	}
}

function storeProfitKey(companyId, year, month){
	$("#profitMonth").val(month);
	$("#profitYear").val(year);
	$("#profitCompany").val(companyId);
}


function sendProfitDetailAjax(companyId, year, month){
	//send request
	var search = {}
	search["year"] = year;
	search["companyId"] = companyId;
	search["month"] = month;
	isProcessingRequest = true;
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "get/profitDetail",
		data : JSON.stringify(search),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			if(data.code == "200"){
				$("#totalRunningContractAmount").text(ctr_formatNumeric(data.monthlyProfitDetail.totalInProgressContractAmount) +" VNĐ");
				$("#totalFinishContractAmount").text(ctr_formatNumeric(data.monthlyProfitDetail.totalFinishContractAmount) +" VNĐ");
				$("#totalBadContractAmount").text(ctr_formatNumeric(data.monthlyProfitDetail.totalBadContractAmount) +" VNĐ");
				$("#totalContractAmount").text(ctr_formatNumeric(data.monthlyProfitDetail.totalContractAmount) +" VNĐ");
				$("#totalRentingNew").text(ctr_formatNumeric(data.monthlyProfitDetail.totalRentingNew) +" VNĐ");
				$("#totalPayoff").text(ctr_formatNumeric(data.monthlyProfitDetail.totalPayOff) +" VNĐ");
				$("#totalRevenueContractAmount").text(ctr_formatNumeric(data.monthlyProfitDetail.totalRevenue) +" VNĐ");
				$("#totalOtherCost").text(ctr_formatNumeric(data.monthlyProfitDetail.totalOtherCost) +" VNĐ");
				$("#totalOtherIncome").text(ctr_formatNumeric(data.monthlyProfitDetail.totalOtherIncome) +" VNĐ");
				$("#totalInvest").text(ctr_formatNumeric(data.monthlyProfitDetail.totalInvest) +" VNĐ");
				$("#totalRunningContractInDateRange").text(ctr_formatNumeric(data.monthlyProfitDetail.totalRunningContractInDateRange) +" VNĐ");
				$("#totalTakeOffRefund").text(ctr_formatNumeric(data.monthlyProfitDetail.totalTakeOffRefund) +" VNĐ");
				$("#totalProfit").text(ctr_formatNumeric(data.monthlyProfitDetail.totalProfit) +" VNĐ");
				$("#totalRefundingForCompanyOfBadContract").text(ctr_formatNumeric(data.monthlyProfitDetail.totalRefundingOfBadContract) +" VNĐ");
				$('#monthlyProfit').modal("show");	
				
			} else {
				alert("Có lỗi xảy ra. Vui lòng thử lại sau:" + data.msg);
			}
			isProcessingRequest = false;
		},
		error : function(e) {
			alert("Có lỗi xảy ra. Vui lòng thử lại sau" );
			isProcessingRequest = false;
		},
		done : function(e) {
			isProcessingRequest = false;
		}
	});
}