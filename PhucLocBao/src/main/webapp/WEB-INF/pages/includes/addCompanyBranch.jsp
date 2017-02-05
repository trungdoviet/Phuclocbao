<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="jstl.jsp"%>
<div class="modal fade company-branch" id="addCompanyBranch" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
    <form:form role="form" id="newBranchForm" method="post" action="addBranch" modelAttribute="cbBean">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       	<div style="height:24px">
			     <h4 class="modal-title">Thêm công ty</h4>
			</div>
	      </div>
	      <div class="modal-body">
	        
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="col-md-12">
								<spring:bind path="company.name">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Tên chi nhánh(<span class="state_alert">*</span>):</label>
										<form:input id="companyName" class="form-control form-input"
											placeholder="vd: Phúc Lộc Bảo Sài Gòn" name="companyName"
											path="company.name" />
										<form:errors path="company.name" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="company.type.id">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Loại hình(<span class="state_alert">*</span>):</label>
										<form:select class="form-control form-input" id="companyType"  
											name="companyType" path="company.type.id">
											<form:option value="" label="--Chọn Loại hình công ty--" />
											<form:options items="${cbBean.availableCompanyTypes}" itemValue="id"
												itemLabel="name" />
										</form:select>
										<form:errors path="company.type.id" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="company.phoneNumber">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Số ĐT(<span class="state_alert">*</span>):</label>
										<form:input id="phoneNumber" class="form-control form-input"
											placeholder="vd: 099 0099009" name="phoneNumber"
											path="company.phoneNumber" />
										<form:errors path="company.phoneNumber" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="company.address">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Địa chỉ:</label>
										<form:textarea id="companyAddress" class="form-control form-input"
											placeholder="vd: B64 Bạch Đằng" rows="6" name="companyAddress"
											path="company.address" />
										<form:errors path="company.address" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="company.city">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Tỉnh/Thành phố:</label>
										<form:select class="form-control form-input" id="companyCity"  
											name="companyCity" path="company.city">
											<form:option value="" label="--Chọn Tỉnh/TP--" />
											<form:options items="${cbBean.cities}" itemValue="code"
												itemLabel="name" />
										</form:select>
										<form:errors path="company.city"
											cssClass="error" />
									</div>
								</spring:bind>
							</div>
						</div>
							
					</div>
				</div><!--/.row-->
	        
	        
	      </div>
	      <div class="modal-footer">
	      <small>(<span class="state_alert">*</span>) là bắt buộc nhập để tiếp tục </small>
	      	<button type="submit" disabled="disabled" class="btn btn-primary"  id="addBranch" name="save">Tạo</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="branchingClose">Đóng</button>
	      </div>
      </form:form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->