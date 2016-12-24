package vn.com.phuclocbao.entity;

import vn.com.phuclocbao.entity.base.IBaseEntity;

/**
 */
@javax.persistence.Entity
@javax.persistence.Table(name="tblPaymentSchedule")
public class PaymentSchedule implements IBaseEntity
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -1259888367033050336L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue
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
  @javax.persistence.Column(length=1)
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

  @javax.persistence.ManyToOne(cascade={javax.persistence.CascadeType.ALL}, fetch=javax.persistence.FetchType.EAGER)
  private vn.com.phuclocbao.entity.Contract contract;

  /**
   * Gets the field contract.
   * @return the value of the field contract; may be null.
   */
  public vn.com.phuclocbao.entity.Contract getContract()
  {
    return contract;
  }

  /**
   * Sets the field contract.
   * @param _contract the new value of the field contract.
   */
  public void setContract(vn.com.phuclocbao.entity.Contract _contract)
  {
    contract = _contract;
  }

}
