<div class="modal fade" id="paymentModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Xác nhận</h4>
      </div>
      <div class="modal-body">
        <p>Xác nhận thanh toán tới ngày: <span id="expectedPayDateString"></span></p>
        <p>Tổng số tiền phải thanh toán<span id="amountSession"></span> <b><span id="totalPaidAmount"></span></b></p>
      </div>
      <input type="hidden" id="paymentDateIndentity">
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="paymentClose">Thoát</button>
        <button type="button" class="btn btn-primary" id="paymentOk">Đồng ý</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- Customer Debt -->
<div class="modal fade" id="addCustomerDebt" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Thêm tiền khách nợ</h4>
      </div>
      <div class="modal-body">
       		<div class="row">
				<div class="col-md-12">
					<div class="form-group">
							<label>Số tiền:</label>
							<input id="customerDebtInput" class="form-control" style="text-align: right;" placeholder="VNĐ" name="customerDebtInput" />
					</div>
				</div>
			</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="addCustomerDebtClose">Thoát</button>
        <button type="button" class="btn btn-primary" id="addCustomerDebtOk">Đồng ý</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Company Debt -->
<div class="modal fade" id="addCompanyDebt" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Thêm tiền công ty nợ</h4>
      </div>
      <div class="modal-body">
       		<div class="row">
				<div class="col-md-12">
					<div class="form-group">
							<label>Số tiền:</label>
							<input id="addCompanyDebtInput" class="form-control" style="text-align: right;" placeholder="VNĐ" name="addCompanyDebtInput" />
					</div>
				</div>
			</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="addCompanyDebtClose">Thoát</button>
        <button type="button" class="btn btn-primary" id="addCompanyDebtOk">Đồng ý</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
