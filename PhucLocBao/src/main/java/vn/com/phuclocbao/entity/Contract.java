package vn.com.phuclocbao.entity;

import vn.com.phuclocbao.entity.base.IBaseEntity;

/**
 */
@javax.persistence.Entity
@javax.persistence.Table(name="tblContract")
public class Contract implements IBaseEntity
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -4415214540458437510L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue()
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

  @javax.persistence.ManyToOne(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE}, fetch=javax.persistence.FetchType.EAGER)
  private vn.com.phuclocbao.entity.CompanyEntity company;

  /**
   * Gets the field company.
   * @return the value of the field company; may be null.
   */
  public vn.com.phuclocbao.entity.CompanyEntity getCompany()
  {
    return company;
  }

  /**
   * Sets the field company.
   * @param _company the new value of the field company.
   */
  public void setCompany(vn.com.phuclocbao.entity.CompanyEntity _company)
  {
    company = _company;
  }

  @javax.persistence.Column(nullable=false)
  private java.lang.Double totalAmount;

  /**
   * Gets the field totalAmount.
   * @return the value of the field totalAmount; may be null.
   */
  public java.lang.Double getTotalAmount()
  {
    return totalAmount;
  }

  /**
   * Sets the field totalAmount.
   * @param _totalAmount the new value of the field totalAmount.
   */
  public void setTotalAmount(java.lang.Double _totalAmount)
  {
    totalAmount = _totalAmount;
  }

  @javax.persistence.Column(nullable=false)
  private java.lang.Double feeADay;

  /**
   * Gets the field feeADay.
   * @return the value of the field feeADay; may be null.
   */
  public java.lang.Double getFeeADay()
  {
    return feeADay;
  }

  /**
   * Sets the field feeADay.
   * @param _feeADay the new value of the field feeADay.
   */
  public void setFeeADay(java.lang.Double _feeADay)
  {
    feeADay = _feeADay;
  }

  @javax.persistence.Column(nullable=false)
  private java.util.Date startDate;

  /**
   * Gets the field startDate.
   * @return the value of the field startDate; may be null.
   */
  public java.util.Date getStartDate()
  {
    return startDate;
  }

  /**
   * Sets the field startDate.
   * @param _startDate the new value of the field startDate.
   */
  public void setStartDate(java.util.Date _startDate)
  {
    startDate = _startDate;
  }

  @javax.persistence.Column(nullable=false)
  private java.util.Date expireDate;

  /**
   * Gets the field expireDate.
   * @return the value of the field expireDate; may be null.
   */
  public java.util.Date getExpireDate()
  {
    return expireDate;
  }

  /**
   * Sets the field expireDate.
   * @param _expireDate the new value of the field expireDate.
   */
  public void setExpireDate(java.util.Date _expireDate)
  {
    expireDate = _expireDate;
  }

  @javax.persistence.Column(nullable=false)
  private java.lang.Integer periodOfPayment;

  /**
   * Gets the field periodOfPayment.
   * @return the value of the field periodOfPayment; may be null.
   */
  public java.lang.Integer getPeriodOfPayment()
  {
    return periodOfPayment;
  }

  /**
   * Sets the field periodOfPayment.
   * @param _periodOfPayment the new value of the field periodOfPayment.
   */
  public void setPeriodOfPayment(java.lang.Integer _periodOfPayment)
  {
    periodOfPayment = _periodOfPayment;
  }
  @javax.persistence.Column(length=1)
  private String notifyBeforePeriod;

  /**
   * Gets the field notifyBeforePeriod.
   * @return the value of the field notifyBeforePeriod; may be null.
   */
  public String getNotifyBeforePeriod()
  {
    return notifyBeforePeriod;
  }

  /**
   * Sets the field notifyBeforePeriod.
   * @param _notifyBeforePeriod the new value of the field notifyBeforePeriod.
   */
  public void setNotifyBeforePeriod(String _notifyBeforePeriod)
  {
    notifyBeforePeriod = _notifyBeforePeriod;
  }

  @javax.persistence.Column(length=1024)
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

  @javax.persistence.Column(nullable=false)
  private java.lang.String contractType;

  /**
   * Gets the field contractType.
   * @return the value of the field contractType; may be null.
   */
  public java.lang.String getContractType()
  {
    return contractType;
  }

  /**
   * Sets the field contractType.
   * @param _contractType the new value of the field contractType.
   */
  public void setContractType(java.lang.String _contractType)
  {
    contractType = _contractType;
  }

  @javax.persistence.OneToOne(cascade={javax.persistence.CascadeType.ALL}, fetch=javax.persistence.FetchType.EAGER, optional=false, mappedBy="contract", orphanRemoval=true)
  private vn.com.phuclocbao.entity.Customer customer;

  /**
   * Gets the field customer.
   * @return the value of the field customer; may be null.
   */
  public vn.com.phuclocbao.entity.Customer getCustomer()
  {
    return customer;
  }

  /**
   * Sets the field customer.
   * @param _customer the new value of the field customer.
   */
  public void setCustomer(vn.com.phuclocbao.entity.Customer _customer)
  {
    customer = _customer;
  }

  @javax.persistence.OneToOne(cascade={javax.persistence.CascadeType.ALL}, fetch=javax.persistence.FetchType.EAGER, mappedBy="contract", orphanRemoval=true)
  private vn.com.phuclocbao.entity.TransportOwner owner;

  /**
   * Gets the field owner.
   * @return the value of the field owner; may be null.
   */
  public vn.com.phuclocbao.entity.TransportOwner getOwner()
  {
    return owner;
  }

  /**
   * Sets the field owner.
   * @param _owner the new value of the field owner.
   */
  public void setOwner(vn.com.phuclocbao.entity.TransportOwner _owner)
  {
    owner = _owner;
  }

  @javax.persistence.OneToMany(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.REMOVE}, fetch=javax.persistence.FetchType.EAGER, mappedBy="contract", orphanRemoval=false)
  private java.util.Set<vn.com.phuclocbao.entity.PaymentSchedule> paymentSchedules;

  /**
   * Gets the field paymentSchedules.
   * @return the value of the field paymentSchedules; may be null.
   */
  public java.util.Set<vn.com.phuclocbao.entity.PaymentSchedule> getPaymentSchedules()
  {
    return paymentSchedules;
  }

  /**
   * Sets the field paymentSchedules.
   * @param _paymentSchedules the new value of the field paymentSchedules.
   */
  public void setPaymentSchedules(java.util.Set<vn.com.phuclocbao.entity.PaymentSchedule> _paymentSchedules)
  {
    paymentSchedules = _paymentSchedules;
  }

}
