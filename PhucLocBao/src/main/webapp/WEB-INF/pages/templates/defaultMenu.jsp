<%@ page import="vn.com.phuclocbao.bean.PLBSession" %>
<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>
		<ul class="nav menu">
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("home")%>'><a href="${pageContext.request.contextPath}/home"><svg class="glyph stroked dashboard-dial"><use xlink:href="#stroked-dashboard-dial"></use></svg> Tổng Quan</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("newContract")%>'><a href="${pageContext.request.contextPath}/newContract"><svg class="glyph stroked pencil"><use xlink:href="#stroked-pencil"></use></svg>Tạo hợp đồng</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("mngContracts")%>'><a href="${pageContext.request.contextPath}/mngContracts"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg>Quản lý hợp đồng</a></li>
			<li class='<%=((PLBSession)request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY)).getMenuBean().getStateClass("oldContracts")%>'><a href="${pageContext.request.contextPath}/oldContracts"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg>Hợp đồng cũ</a></li>
			<li><a href="tables.html"><svg class="glyph stroked table"><use xlink:href="#stroked-table"></use></svg> Tables</a></li>
			<li><a href="forms.html"><svg class="glyph stroked pencil"><use xlink:href="#stroked-pencil"></use></svg> Forms</a></li>
			<li><a href="panels.html"><svg class="glyph stroked app-window"><use xlink:href="#stroked-app-window"></use></svg> Alerts &amp; Panels</a></li>
			<li><a href="icons.html"><svg class="glyph stroked star"><use xlink:href="#stroked-star"></use></svg> Icons</a></li>
			<li class="parent ">
				<a href="#">
					<span data-toggle="collapse" href="#sub-item-1"><svg class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg></span> Dropdown 
				</a>
				<ul class="children collapse" id="sub-item-1">
					<li>
						<a class="" href="#">
							<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Sub Item 1
						</a>
					</li>
					<li>
						<a class="" href="#">
							<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Sub Item 2
						</a>
					</li>
					<li>
						<a class="" href="#">
							<svg class="glyph stroked chevron-right"><use xlink:href="#stroked-chevron-right"></use></svg> Sub Item 3
						</a>
					</li>
				</ul>
			</li>
			<li role="presentation" class="divider"></li>
			<li><a href="login.html"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg> Login Page</a></li>
		</ul>

	</div><!--/.sidebar-->