<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="includes/jstl.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main phuclocbao">			
	<div class="row">
		<ol class="breadcrumb">
			<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
			<li class="active">Cài đặt Tài khoản</li>
		</ol>
	</div><!--/.row-->
	<c:if test="${not empty msg}">
		<div id="uhAlert" class="alert alert-${css} alert-dismissible fade in" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>
	<form:form role="form" id="userSettingForm" method="post" action="saveUserSetting" modelAttribute="usBean">
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>Cài đặt Tài khoản
				</div>
				<div class="panel-body">
					<div class="col-md-6">
						<div class="form-group ">
							<label>Tên đăng nhập:</label>
							<form:input id="username" class="form-control" disabled="true"
								name="username"
								path="user.username" />
						</div>
						<spring:bind path="user.fullname">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label>Họ Tên:</label>
								<form:input id="userfullname" 
									class="form-control" placeholder="vd: Nguyễn Văn A"
									name="userfullname" path="user.fullname" />
								<form:errors path="user.fullname" cssClass="error" />
							</div>
						</spring:bind>
						<spring:bind path="user.email">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label>Email:</label>
								<form:input id="userEmail" class="form-control"
									placeholder="vd:someone@mail.com" name="userEmail"
									path="user.email" />
								<form:errors path="user.email" cssClass="error" />
							</div>
						</spring:bind>
					</div>
					<div class="col-md-6">
						<spring:bind path="oldPassword">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label>Mật khẩu cũ:</label>
								<form:input id="oldPassword" class="form-control" type="password"
									name="oldPassword"
									path="oldPassword" />
								<form:errors path="oldPassword" cssClass="error" />
							</div>
						</spring:bind>
						<spring:bind path="newPassword">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label>Mật khẩu mới:</label>
								<form:input id="newPassword" class="form-control" type="password"
									name="newPassword"
									path="newPassword" />
								<form:errors path="newPassword" cssClass="error" />
							</div>
						</spring:bind>
						<spring:bind path="retypePassword">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label>Nhập lại mật khẩu mới:</label>
								<form:input id="retypePassword" class="form-control" type="password"
									name="retypePassword"
									path="retypePassword" />
								<form:errors path="retypePassword" cssClass="error" />
							</div>
						</spring:bind>
					</div>
				</div>
			</div>
		</div>
		<!-- /.col-->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="pull-right">
				<button id="btnNewContract" type="submit" class="btn btn-primary" name="save">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					Lưu
				</button>
			</div>
		</div>
	</div>
	</form:form>
</div>