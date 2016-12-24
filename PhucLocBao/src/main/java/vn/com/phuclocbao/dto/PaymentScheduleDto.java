package vn.com.phuclocbao.dto;

import vn.com.phuclocbao.dto.base.IBaseDTO;

/**
 */
public class PaymentScheduleDto implements IBaseDTO
{

  /**
   * Identifier
   */
  private java.lang.Integer id;

  /**
   * Gets the field id.
   * @return the value of the field id; may be null.
   */
  public java.lang.Integer getId()
  {
    return id;
  }

  /**
   * Sets the field id.
   * @param _id the new value of the field id.
   */
  public void setId(java.lang.Integer _id)
  {
    id = _id;
  }

  private java.lang.String payDate;

  /**
   * Gets the field payDate.
   * @return the value of the field payDate; may be null.
   */
  public java.lang.String getPayDate()
  {
    return payDate;
  }

  /**
   * Sets the field payDate.
   * @param _payDate the new value of the field payDate.
   */
  public void setPayDate(java.lang.String _payDate)
  {
    payDate = _payDate;
  }

  private java.lang.String notifiedDate;

  /**
   * Gets the field notifiedDate.
   * @return the value of the field notifiedDate; may be null.
   */
  public java.lang.String getNotifiedDate()
  {
    return notifiedDate;
  }

  /**
   * Sets the field notifiedDate.
   * @param _notifiedDate the new value of the field notifiedDate.
   */
  public void setNotifiedDate(java.lang.String _notifiedDate)
  {
    notifiedDate = _notifiedDate;
  }

  private String finish;

  /**
   * Gets the field finish.
   * @return the value of the field finish; may be null.
   */
  public String getFinish()
  {
    return finish;
  }

  /**
   * Sets the field finish.
   * @param _finish the new value of the field finish.
   */
  public void setFinish(String _finish)
  {
    finish = _finish;
  }

  public ContractDto getContract() {
	return contract;
}

public void setContract(ContractDto contract) {
	this.contract = contract;
}

private ContractDto contract;

  
}
