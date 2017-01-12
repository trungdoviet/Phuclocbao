package vn.com.phuclocbao.entity;

import vn.com.phuclocbao.entity.base.IBaseEntity;

/**
 */
@SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name="tblContractHistory")
public class ContractHistory implements IBaseEntity
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 4092344397315630099L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue
  private java.lang.Integer id;
  private java.lang.Double fee;
  private java.lang.Double rentingAmount;
  private java.lang.Double payoff;
  private java.util.Date logDate;
  @javax.persistence.ManyToOne(cascade={}, fetch=javax.persistence.FetchType.EAGER)
  private vn.com.phuclocbao.entity.Contract contract;
  
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

  

  /**
   * Gets the field fee.
   * @return the value of the field fee; may be null.
   */
  public java.lang.Double getFee()
  {
    return fee;
  }

  /**
   * Sets the field fee.
   * @param _fee the new value of the field fee.
   */
  public void setFee(java.lang.Double _fee)
  {
    fee = _fee;
  }

  

  /**
   * Gets the field rentingAmount.
   * @return the value of the field rentingAmount; may be null.
   */
  public java.lang.Double getRentingAmount()
  {
    return rentingAmount;
  }

  /**
   * Sets the field rentingAmount.
   * @param _rentingAmount the new value of the field rentingAmount.
   */
  public void setRentingAmount(java.lang.Double _rentingAmount)
  {
    rentingAmount = _rentingAmount;
  }

  

  /**
   * Gets the field payoff.
   * @return the value of the field payoff; may be null.
   */
  public java.lang.Double getPayoff()
  {
    return payoff;
  }

  /**
   * Sets the field payoff.
   * @param _payoff the new value of the field payoff.
   */
  public void setPayoff(java.lang.Double _payoff)
  {
    payoff = _payoff;
  }

  

  /**
   * Gets the field logDate.
   * @return the value of the field logDate; may be null.
   */
  public java.util.Date getLogDate()
  {
    return logDate;
  }

  /**
   * Sets the field logDate.
   * @param _logDate the new value of the field logDate.
   */
  public void setLogDate(java.util.Date _logDate)
  {
    logDate = _logDate;
  }

  private java.lang.String note;

  /**
   * Gets the field note.
   * @return the value of the field note; may be null.
   */
  public java.lang.String getNote()
  {
    return note;
  }

  /**
   * Sets the field note.
   * @param _note the new value of the field note.
   */
  public void setNote(java.lang.String _note)
  {
    note = _note;
  }

  

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
