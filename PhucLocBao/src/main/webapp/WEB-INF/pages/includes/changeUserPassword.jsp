<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="jstl.jsp"%>
<div class="modal fade company-branch" id="changePassword" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
    <form:form role="form" id="changePassForm" method="post" action="changePassword" modelAttribute="upBean">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       	<div style="height:24px">
			     <h4 class="modal-title">Thay đổi mật khẩu</h4>
			</div>
	      </div>
	      <div class="modal-body">
	        
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<div class="col-md-12">
									<div class="form-group">
										<label>Tài khoản:</label>
										<label id="usernameLabel"></label>
										<form:hidden path="userid" id="userId"/>
									</div>
							</div>
							<div class="col-md-12">
									<div class="form-group">
										<label>Mật khẩu mới(<span class="state_alert">*</span>):</label>
										<form:input id="newPassword" class="form-control form-input"
											name="newPassword" type="password"
											path="newPassword" required="true" minlength="6" maxLength="100"/>
									</div>
							</div>
							<div class="col-md-12">
									<div class="form-group ">
										<label>Nhập lại mật khẩu mới(<span class="state_alert">*</span>):</label>
										<form:input id="retypePassword" class="form-control form-input passwordMatch"
											name="retypePassword" type="password"
											path="retypePassword" required="true" />
									</div>
							</div>
						</div>
							
					</div>
				</div><!--/.row-->
	        
	        
	      </div>
	      <div class="modal-footer">
	      <small>(<span class="state_alert">*</span>) là bắt buộc nhập để tiếp tục </small>
	      	<button type="submit"  class="btn btn-primary"  id="changePassword" name="save">Lưu</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="changePasswordClose">Đóng</button>
	      </div>
      </form:form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->