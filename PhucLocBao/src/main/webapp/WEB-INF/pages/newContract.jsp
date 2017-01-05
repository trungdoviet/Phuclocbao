<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Tạo hợp đồng mới</li>
		</ol>
	</div><!--/.row-->
	
	<!-- <div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Tạo hợp đồng mới</h1>
		</div>
	</div> --><!--/.row-->
<form:form role="form" id="newContractForm" method="post" action="newContractAction" modelAttribute="contractBean">
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>Thông tin khách hàng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="contractDto.customer.idNo">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số CMND/Số GPLX:</label>
									<form:input class="form-control" placeholder="vd: BA-012345678" name="customerIdNo" path="contractDto.customer.idNo"/>
									<form:errors path="contractDto.customer.idNo" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.name">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Tên khách hàng:</label>
									<form:input class="form-control" placeholder="vd: Nguyễn Văn A" name="customerName" path="contractDto.customer.name"/>
									<form:errors path="contractDto.customer.name" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.phone">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số điện thoại:</label>
									<form:input class="form-control" placeholder="vd: 0909 123456" name="customerPhone" path="contractDto.customer.phone"/>
									<form:errors path="contractDto.customer.phone" cssClass="error" />
								</div>		
							</spring:bind>						
						</div>
						<div class="col-md-6">
							<spring:bind path="contractDto.customer.address">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Địa chỉ:</label>
									<form:textarea class="form-control" placeholder="vd: B64 Bạch Đằng" rows="6" name="customerAddress" path="contractDto.customer.address"/>
									<form:errors path="contractDto.customer.address"  cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.customer.province">		
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-top: -6px;">
									<label>Tỉnh/Thành phố:</label>
									<form:select class="form-control" name="customerProvince" path="contractDto.customer.province">
										<form:option value="" label="--Chọn Tỉnh/TP--" />
										<form:options items="${contractBean.cities}" itemValue="code" itemLabel="name"  />
									</form:select>
									<form:errors path="contractDto.customer.province"  cssClass="error" />
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
		</div><!-- /.col-->
	</div><!-- /.row -->
	
	<!-- Contract Info -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>Thông tin hợp đồng</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="contractDto.contractType">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Loại hình:</label>
									<form:select readonly="true" class="form-control" name="customerProvince" path="contractDto.contractType">
										<option value="RMB">Cho thuê xe</option>
										<!-- <option value="HN"></option> -->
									</form:select>
									<form:errors path="contractDto.contractType"  cssClass="error" />
								</div>
							</spring:bind>
							
							<spring:bind path="contractDto.company.id">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Công ty:</label>
									<form:select disabled="true" class="form-control" name="customerProvince" path="contractDto.company.id">
										<option value="${contractBean.currentCompany.id}">${contractBean.currentCompany.name}</option>
										<!-- <option value="HN"></option> -->
									</form:select>
									<form:errors path="contractDto.company.id"  cssClass="error" />
								</div>
							</spring:bind>
							
							 <spring:bind path="contractDto.totalAmount">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số tiền:</label>
									<form:input id="totalAmount" class="form-control" style="text-align: right;" placeholder="VNĐ" name="totalAmount" path="contractDto.totalAmount"/>
									<form:errors path="contractDto.totalAmount" cssClass="error" />
								</div>
							</spring:bind>
							 <spring:bind path="contractDto.feeADay">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Phí thuê/1 ngày:</label>
									<form:input id="feeADay" class="form-control" style="text-align: right;" placeholder="VNĐ" name="feeADay" path="contractDto.feeADay"/>
									<form:errors path="contractDto.feeADay" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.note">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ghi chú:</label>
									<form:textarea class="form-control" placeholder="thông tin thêm" rows="6" name="contractNote" path="contractDto.note"/>
									<form:errors path="contractDto.note"  cssClass="error" />
								</div>
							</spring:bind>			
						</div>
						<div class="col-md-6">
							<spring:bind path="contractDto.startDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ngày thuê:</label>
									<form:input id="startDate" class="form-control readonlyDate" readonly="true" placeholder="Ngày/Tháng/Năm" name="startDate" path="contractDto.startDate"/>
									<form:errors path="contractDto.startDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.expireDate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Ngày hết hạn:</label>
									<form:input id="expireDate" class="form-control readonlyDate" readonly="true" placeholder="Ngày/Tháng/Năm" name="expireDate" path="contractDto.expireDate"/>
									<form:errors path="contractDto.expireDate" cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.periodOfPayment">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Khoảng ngày thanh toán:</label><cite>(Tối đa 30 ngày)</cite>
									<form:input id="periodOfPayment" class="form-control" placeholder="Ngày" name="periodOfPayment" path="contractDto.periodOfPayment"/>
									<form:errors path="contractDto.periodOfPayment" cssClass="error" />
								</div>
							</spring:bind>
							
							<div class="form-group">
								<label>Lịch trả phí:</label>
								<div id="paymentPeriodPanel" class="funkyradio">
							       
							    </div>

							    <form:hidden path="paidInfo" id="payDateHidden" />
							</div>
						</div>
					</div>
				</div>
		</div><!-- /.col-->
	</div><!-- /.row -->
	
	<!-- Owner -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Thông tin thêm</div>
					<div class="panel-body">
						<div class="col-md-6">
							<spring:bind path="contractDto.owner.name">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Người xác minh:</label>
									<form:input class="form-control" placeholder="vd: Nguyễn Văn A" name="ownerName" path="contractDto.owner.name"/>
									<form:errors path="contractDto.owner.name"  cssClass="error" />
								</div>
							</spring:bind>
							
							<spring:bind path="contractDto.owner.transportType">		
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Loại xe:</label>
									<form:input class="form-control" placeholder="vd: Airblade, SH" name="transportType" path="contractDto.owner.transportType"/>
									<form:errors path="contractDto.owner.transportType"  cssClass="error" />
								</div>
							</spring:bind>
							
							 <spring:bind path="contractDto.owner.numberPlate">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Biển kiểm soát:</label>
									<form:input class="form-control" placeholder="vd: 99V3-999.00" name="numberPlate" path="contractDto.owner.numberPlate"/>
									<form:errors path="contractDto.owner.numberPlate"  cssClass="error" />
								</div>
							</spring:bind>
							 <spring:bind path="contractDto.owner.chassisFrameNumber">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số khung:</label>
									<form:input class="form-control" placeholder="vd: FJ900..." name="chassisFrameNumber" path="contractDto.owner.chassisFrameNumber"/>
									<form:errors path="contractDto.owner.chassisFrameNumber"  cssClass="error" />
								</div>
							</spring:bind>
						</div>
						<div class="col-md-6">
							<spring:bind path="contractDto.owner.chassisNumber">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Số máy:</label>
									<form:input class="form-control" placeholder="vd: AXB88..." name="chassisNumber" path="contractDto.owner.chassisNumber"/>
									<form:errors path="contractDto.owner.chassisNumber"  cssClass="error" />
								</div>
							</spring:bind>
							<spring:bind path="contractDto.owner.detail">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label>Chi tiết:</label>
									<form:textarea class="form-control" placeholder="thông tin thêm" rows="6" name="ownerDetail" path="contractDto.owner.detail"/>
									<form:errors path="contractDto.owner.detail"  cssClass="error" />
								</div>
							</spring:bind>		
						</div>
					</div>
				</div>
		</div><!-- /.col-->
	</div><!-- /.row -->
	
	<div class="row">
		<div class="col-lg-12">
			<div class="pull-right">
				<spring:url value="/newContractAction/cancel" var="cancelUrl" />
				<button id="btnNewContract" type="submit" class="btn btn-primary" name="save">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					Tạo hợp đồng
				</button>
				<button type="button" class="btn btn-slave" name="cancel" onclick="location.href='${cancelUrl}'">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					Đóng
				</button>
			</div>
		</div>
	</div>
</form:form>
<div class="modal fade" id="paymentModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Xác nhận</h4>
      </div>
      <div class="modal-body">
        <p>Xác nhận thanh toán tới ngày: <span id="payDateString"></span></p>
      </div>
      <input type="hidden" id="paymentDateIndentity">
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="paymentClose">Đóng</button>
        <button type="button" class="btn btn-primary" id="paymentOk">Đồng ý</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
$( document ).ready(function() {
	 initNewContractPageButtons();
	 populatePaymentSchedules();
	 initPaymentPopup();
});
</script>
</div>
