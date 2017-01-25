<div class="modal fade" id="contractDetail" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
       	<div style="height:24px">
		     <div class="pull-left"><h4 class="modal-title">Thông tin hợp đồng</h4></div>
		     <div class="pull-left label-contract-margin"><span id="labelInprogress"  class="label label-primary hide-me">Còn hiệu lực</span></div>
			<div class="pull-left label-contract-margin"><span id="labelFinish" class="label label-success hide-me">Đã thanh lý</span></div>
			<div class="pull-left label-contract-margin"><span id="labelBad" class="label label-danger hide-me">Xấu</span></div>
		</div>
      </div>
      <div class="modal-body">
        
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-default">
						<h5 class="text-center"><span class="glyphicon glyphicon-user" aria-hidden="true"></span><strong class="bottom-line">Thông tin khách hàng</strong></h5>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Số CMND/GPLX:</strong></div><div id="cdIdNo" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Tên khách hàng:</strong></div><div id="cdCustomerName" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Năm sinh</strong></div><div id="cdCustomerBirthyear" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Số điện thoại:</strong></div><div id="cdCustomerPhone" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Địa chỉ:</strong></div><div id="cdCustomerAddress" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Tỉnh/Thành phố:</strong></div><div id="cdCustomerProvince" class="col-md-8 text-left"></div>
							</div>
						</div>
						<h5 class="text-center"><span class="glyphicon glyphicon-link" aria-hidden="true"></span><strong class="bottom-line">Thông tin hợp đồng</strong></h5>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Công ty:</strong></div><div id="cdCompanyName" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Số điện thoại Công ty:</strong></div><div id="cdCompanyPhone" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Loại hình:</strong></div><div id="cdContractType" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Số tiền:</strong></div><strong></strong><div id="cdTotalAmount" class="col-md-8 text-right" style="font-weight:bold"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Ngày thuê:</strong></div><div id="cdStartDate" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Lịch trả phí:</strong></div><div id="cdPaymentSchedule" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Ngày hết hạn:</strong></div><div id="cdExpireDate" class="col-md-8 text-left"></div>
							</div>
							<div id="payoffDatePanel" class="row" style="display:none">
								<div class="col-md-4 text-right"><strong class="bottom-line">Ngày thanh lý:</strong></div><div id="cdPayoffDate" class="col-md-8 text-left"></div>
							</div>
						</div>
						<h5 class="text-center"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span><strong class="bottom-line">Thông tin tài sản</strong></h5>
						<div class="panel-body">
							<div class="row">
								<div class="col-md-4 text-right"><strong class="bottom-line">Loại xe:</strong></div><div id="cdTransportType" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right "><strong class="bottom-line">Biển kiểm soát:</strong></div><div id="cdNumberPlate" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right "><strong class="bottom-line">Số khung:</strong></div><div id="cdChassisFrameNumber" class="col-md-8 text-left"></div>
							</div>
							<div class="row">
								<div class="col-md-4 text-right "><strong class="bottom-line">Số máy:</strong></div><div id="cdChassisNumber" class="col-md-8 text-left"></div>
							</div>
						</div>
					</div>
						
				</div>
			</div><!--/.row-->
        
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="paymentClose">Đóng</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->