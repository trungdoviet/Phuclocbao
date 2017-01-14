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