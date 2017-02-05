<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="jstl.jsp"%>
<div class="modal fade company-branch" id="addUser" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
    <form:form role="form" id="userForm" method="post" action="addUser" modelAttribute="muBean">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       	<div style="height:24px">
			     <h4 class="modal-title">Thêm tài khoản</h4>
			</div>
	      </div>
	      <div class="modal-body">
	        
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="col-md-12">
								<spring:bind path="user.username">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Tên đăng nhập(<span class="state_alert">*</span>):</label>
										<form:input id="username" class="form-control"
											placeholder="vd: phuclocbao" name="username"
											path="user.username" />
										<form:errors path="user.username" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="user.isAdmin">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Loại tài khoản(<span class="state_alert">*</span>):</label>
										<form:radiobutton path="user.isAdmin" value="Y"/>Admin
										<form:radiobutton path="user.isAdmin" value="N"/>Thường
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="user.companyEntity.id">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Thuộc công ty:</label>
										<form:select class="form-control" id="userCompany"  
											name="userCompany" path="user.companyEntity.id">
											<form:option value="" label="--Chọn Công ty--" />
											<form:options items="${muBean.companies}" itemValue="id"
												itemLabel="name" />
										</form:select>
										<form:errors path="user.companyEntity.id"
											cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="user.fullname">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Họ Tên(<span class="state_alert">*</span>):</label>
										<form:input id="fullname" class="form-control"
											placeholder="vd: Nguyễn Văn A" name="fullname"
											path="user.fullname" />
										<form:errors path="user.fullname" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="user.password">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Mật khẩu(<span class="state_alert">*</span>):</label>
										<form:input id="password" class="form-control"
											type="password" name="password" autocomplete="new-password"
											path="user.password" />
										<form:errors path="user.password" cssClass="error" />
									</div>
								</spring:bind>
							</div>
							<div class="col-md-12">
								<spring:bind path="user.email">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<label>Email:</label>
										<form:input id="email" class="form-control"
											placeholder="vd: someone@gmail.com" name="email"
											path="user.email" />
										<form:errors path="user.email" cssClass="error" />
									</div>
								</spring:bind>
							</div>
						</div>
							
					</div>
				</div><!--/.row-->
	        
	        
	      </div>
	      <div class="modal-footer">
	      <small>(<span class="state_alert">*</span>) là bắt buộc nhập để tiếp tục </small>
	      	<button type="submit"  class="btn btn-primary"  id="addUser" name="save">Tạo</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="addingUserClose">Đóng</button>
	      </div>
      </form:form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->