package vn.com.phuclocbao.dto;

import vn.com.phuclocbao.dto.base.IBaseDTO;

public class ContractDto implements IBaseDTO
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

  private CompanyDto company;

  
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

  private CustomerDto customer;

  

  private TransportOwnerDto owner;

  

  private java.util.List<PaymentScheduleDto> paymentSchedules;


	public CompanyDto getCompany() {
		return company;
	}
	
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	
	public CustomerDto getCustomer() {
		return customer;
	}
	
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public TransportOwnerDto getOwner() {
		return owner;
	}

	public void setOwner(TransportOwnerDto owner) {
		this.owner = owner;
	}

	public java.util.List<PaymentScheduleDto> getPaymentSchedules() {
		return paymentSchedules;
	}

	public void setPaymentSchedules(java.util.List<PaymentScheduleDto> paymentSchedules) {
		this.paymentSchedules = paymentSchedules;
	}
}
