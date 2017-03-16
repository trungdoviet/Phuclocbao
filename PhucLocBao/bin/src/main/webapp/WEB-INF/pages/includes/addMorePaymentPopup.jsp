<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="jstl.jsp"%>
<div class="modal fade add-payment" id="addPayment" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
    <form:form role="form" id="newPaymentForm" method="post" action="addPayment" modelAttribute="historyView">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       	<div style="height:24px">
			     <h4 class="modal-title">Thêm thu chi</h4>
			</div>
	      </div>
	      <div class="modal-body">
	        
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="col-md-12">
								<spring:bind path="paymentType">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Loại chi phí(<span class="state_alert">*</span>):</label><br/>
										<c:if test="${sessionScope.isUserAdmin == 'Y'}">
											<form:radiobutton path="paymentType" value="INVEST_FUNDING"/>Nhập vốn
											<form:radiobutton path="paymentType" value="TAKE_OUT_FUNDING"/>Xuất vốn
										</c:if>
										<form:radiobutton path="paymentType" value="OTHER_INCOME"/>Thu khác...
										<form:radiobutton path="paymentType" value="OTHER_PAY"/>Chi khác...
										<form:errors path="paymentType" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="paymentAmount">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Số tiền(<span class="state_alert">*</span>):</label>
										<form:input id="paymentAmount" class="form-control form-input" required="true" min="1"
											style="text-align: right;" placeholder="VNĐ" name="paymentAmount" 
											path="paymentAmount" />
										<form:errors path="paymentAmount" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="createdPaymentDate">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Ngày tạo:</label>
										<form:input id="createdPaymentDate" class="form-control"
											readonly="true" placeholder="Ngày/Tháng/Năm" name="createdPaymentDate"
											path="createdPaymentDate" />
										<form:errors path="createdPaymentDate" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="additionalInfo">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Ghi chú(<span class="state_alert">*</span>):</label>
										<form:textarea class="form-control form-input" maxlength="1000" placeholder="thông tin thêm" required="true"
											rows="6" name="additionalInfo" path="additionalInfo" />
										<form:errors path="additionalInfo" cssClass="error" />
									</div>
								</spring:bind>
							</div>
						</div>
							
					</div>
				</div><!--/.row-->
	        
	        
	      </div>
	      <div class="modal-footer">
	      <small>(<span class="state_alert">*</span>) là bắt buộc nhập để tiếp tục </small>
	      	<button type="submit" class="btn btn-primary" disabled="disabled"  id="addMorePaymentOK" name="save">Thêm</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="addMorePaymentClose">Đóng</button>
	      </div>
      </form:form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->