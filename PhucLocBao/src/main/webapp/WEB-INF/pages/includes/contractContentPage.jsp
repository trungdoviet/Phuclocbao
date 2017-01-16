<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="jstl.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-user" aria-hidden="true"></span>Thông
				tin khách hàng
			</div>
			<div class="panel-body">
				<div class="col-md-6">
					<spring:bind path="contractDto.customer.idNo">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Số CMND/Số GPLX:</label>
							<form:input id="customerIdNo" class="form-control"
								readonly="${contractBean.isReadOnly('contractDto.customer.idNo')}"
								placeholder="vd: BA-012345678" name="customerIdNo"
								path="contractDto.customer.idNo" />
							<form:errors path="contractDto.customer.idNo" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.customer.name">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Tên khách hàng:</label>
							<form:input id="customerName" autocomplete="new-password"
								readonly="${contractBean.isReadOnly('contractDto.customer.name')}"
								class="form-control" placeholder="vd: Nguyễn Văn A"
								name="customerName" path="contractDto.customer.name" />
							<form:errors path="contractDto.customer.name" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.customer.birthYear">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Năm sinh:</label>
							<form:input id="customerBirthYear" class="form-control"
								placeholder="vd: 1970" name="customerBirthYear"
								readonly="${contractBean.isReadOnly('contractDto.customer.idNo')}"
								path="contractDto.customer.birthYear" />
							<form:errors path="contractDto.customer.birthYear"
								cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.customer.phone">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Số điện thoại:</label>
							<form:input id="customerPhone" class="form-control"
								placeholder="vd: 0909 123456" name="customerPhone"
								readonly="${contractBean.isReadOnly('contractDto.customer.phone')}"
								path="contractDto.customer.phone" />
							<form:errors path="contractDto.customer.phone" cssClass="error" />
						</div>
					</spring:bind>
				</div>
				<div class="col-md-6">
					<spring:bind path="contractDto.customer.address">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Địa chỉ:</label>
							<form:textarea id="customerAddress" class="form-control"
								readonly="${contractBean.isReadOnly('contractDto.customer.address')}"
								placeholder="vd: B64 Bạch Đằng" rows="6" name="customerAddress"
								path="contractDto.customer.address" />
							<form:errors path="contractDto.customer.address" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.customer.province">
						<div class="form-group ${status.error ? 'has-error' : ''}"
							style="margin-top: -6px;">
							<label>Tỉnh/Thành phố:</label>
							<form:select class="form-control" id="customerProvince"  
								disabled="${contractBean.getMeta('contractDto.customer.province').isDisabled()}"
								name="customerProvince" path="contractDto.customer.province">
								<form:option value="" label="--Chọn Tỉnh/TP--" />
								<form:options items="${contractBean.cities}" itemValue="code"
									itemLabel="name" />
							</form:select>
							<form:errors path="contractDto.customer.province"
								cssClass="error" />
						</div>
					</spring:bind>
				</div>
				<div id="plbAvailableContract" class="col-md-12"
					style="display: none">
					<div class="form-group">
						<label>Danh sách hợp đồng tìm được</label>
					</div>
					<div id="plbContractPanel" class="list-group plb-contract">
						<c:if test="${contractBean.searchedCustomerContract != null}">
							<c:forEach var="contract"
								items="${contractBean.searchedCustomerContract.contracts}"
								varStatus="loop">
								<div class="group width-100p">
									<c:if test="${contract.state == 'IN_PROGRESS'}">
										<div class="list-group-item list-group-item-warning">HĐ
											Mới</div>
									</c:if>
									<c:if test="${contract.state == 'FINISH'}">
										<div class="list-group-item list-group-item-success">HĐ
											Cũ</div>
									</c:if>
									<c:if test="${contract.state == 'BAD'}">
										<div class="list-group-item list-group-item-bad">HĐ Xấu</div>
									</c:if>
									<div class="list-group-item-label">
										<a class="list-group-link"
											onclick="openContractDetail(${contract.id})"
											data-id='${contract.id}' href="#">Hợp đồng ngày
											${contract.startDate}</a><br>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- /.col-->
</div>
<!-- /.row -->

<!-- Contract Info -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-link" aria-hidden="true"></span>Thông
				tin hợp đồng
			</div>
			<div class="panel-body">
				<div class="col-md-6">
					<spring:bind path="contractDto.contractType">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Loại hình:</label>
							<form:select readonly="true" class="form-control"
								name="contractType" path="contractDto.contractType">
								<option value="RMB">Cho thuê xe</option>
								<!-- <option value="HN"></option> -->
							</form:select>
							<form:errors path="contractDto.contractType" cssClass="error" />
						</div>
					</spring:bind>

					<spring:bind path="contractDto.company.id">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Công ty:</label>
							<form:select disabled="true" class="form-control"
								name="customerProvince" path="contractDto.company.id">
								<option value="${contractBean.currentCompany.id}">${contractBean.currentCompany.name}</option>
								<!-- <option value="HN"></option> -->
							</form:select>
							<form:errors path="contractDto.company.id" cssClass="error" />
						</div>
					</spring:bind>

					<spring:bind path="contractDto.totalAmount">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Số tiền:</label>
							<form:input id="totalAmount" class="form-control"
								readonly="${contractBean.isReadOnly('contractDto.totalAmount')}"
								style="text-align: right;" placeholder="VNĐ" name="totalAmount"
								path="contractDto.totalAmount" />
							<form:errors path="contractDto.totalAmount" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.feeADay">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Phí thuê/1 ngày:</label>
							<form:input id="feeADay" class="form-control"
							readonly="${contractBean.isReadOnly('contractDto.feeADay')}"
								style="text-align: right;" placeholder="VNĐ" name="feeADay"
								path="contractDto.feeADay" />
							<form:errors path="contractDto.feeADay" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.note">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Ghi chú:</label>
							<form:textarea class="form-control" placeholder="thông tin thêm"
							readonly="${contractBean.isReadOnly('contractDto.note')}"
								rows="6" name="contractNote" path="contractDto.note" />
							<form:errors path="contractDto.note" cssClass="error" />
						</div>
					</spring:bind>
				</div>
				<div class="col-md-6">
					<spring:bind path="contractDto.startDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Ngày thuê:</label>
							<form:input id="startDate" class="form-control readonlyDate"
								disabled="${contractBean.getMeta('contractDto.startDate').isDisabled()}"
								readonly="true" placeholder="Ngày/Tháng/Năm" name="startDate"
								path="contractDto.startDate" />
							<form:errors path="contractDto.startDate" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.expireDate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Ngày hết hạn:</label>
							<form:input id="expireDate" class="form-control readonlyDate"
							disabled="${contractBean.getMeta('contractDto.expireDate').isDisabled()}"
								readonly="true" placeholder="Ngày/Tháng/Năm" name="expireDate"
								path="contractDto.expireDate" />
							<form:errors path="contractDto.expireDate" cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.periodOfPayment">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Khoảng ngày thanh toán:</label><cite>(Tối đa 30
								ngày)</cite>
							<form:input id="periodOfPayment" class="form-control"
							readonly="${contractBean.isReadOnly('contractDto.periodOfPayment')}"
								placeholder="Ngày" name="periodOfPayment"
								path="contractDto.periodOfPayment" />
							<form:errors path="contractDto.periodOfPayment" cssClass="error" />
						</div>
					</spring:bind>

					<div class="form-group">
						<label>Lịch trả phí:</label>
						<div id="paymentPeriodPanel" class="funkyradio"></div>

						<form:hidden path="paidInfo" id="payDateHidden" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.col-->
</div>
<!-- /.row -->

<!-- Owner -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Thông
				tin thêm
			</div>
			<div class="panel-body">
				<div class="col-md-6">
					<spring:bind path="contractDto.owner.name">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Người xác minh:</label>
							<form:input class="form-control" placeholder="vd: Nguyễn Văn A"
							readonly="${contractBean.isReadOnly('contractDto.owner.name')}"
								name="ownerName" path="contractDto.owner.name" />
							<form:errors path="contractDto.owner.name" cssClass="error" />
						</div>
					</spring:bind>

					<spring:bind path="contractDto.owner.transportType">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Loại xe:</label>
							<form:input class="form-control" placeholder="vd: Airblade, SH"
							readonly="${contractBean.isReadOnly('contractDto.owner.transportType')}"
								name="transportType" path="contractDto.owner.transportType" />
							<form:errors path="contractDto.owner.transportType"
								cssClass="error" />
						</div>
					</spring:bind>

					<spring:bind path="contractDto.owner.numberPlate">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Biển kiểm soát:</label>
							<form:input class="form-control" placeholder="vd: 99V3-999.00"
							readonly="${contractBean.isReadOnly('contractDto.owner.numberPlate')}"
								name="numberPlate" path="contractDto.owner.numberPlate" />
							<form:errors path="contractDto.owner.numberPlate"
								cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.owner.chassisFrameNumber">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Số khung:</label>
							<form:input class="form-control" placeholder="vd: FJ900..."
							readonly="${contractBean.isReadOnly('contractDto.owner.chassisFrameNumber')}"
								name="chassisFrameNumber"
								path="contractDto.owner.chassisFrameNumber" />
							<form:errors path="contractDto.owner.chassisFrameNumber"
								cssClass="error" />
						</div>
					</spring:bind>
				</div>
				<div class="col-md-6">
					<spring:bind path="contractDto.owner.chassisNumber">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Số máy:</label>
							<form:input class="form-control" placeholder="vd: AXB88..."
							readonly="${contractBean.isReadOnly('contractDto.owner.chassisNumber')}"
								name="chassisNumber" path="contractDto.owner.chassisNumber" />
							<form:errors path="contractDto.owner.chassisNumber"
								cssClass="error" />
						</div>
					</spring:bind>
					<spring:bind path="contractDto.owner.detail">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label>Chi tiết:</label>
							<form:textarea class="form-control" placeholder="thông tin thêm"
							readonly="${contractBean.isReadOnly('contractDto.owner.detail')}"
								rows="6" name="ownerDetail" path="contractDto.owner.detail" />
							<form:errors path="contractDto.owner.detail" cssClass="error" />
						</div>
					</spring:bind>
				</div>
			</div>
		</div>
	</div>
	<!-- /.col-->
</div>
<!-- /.row -->
