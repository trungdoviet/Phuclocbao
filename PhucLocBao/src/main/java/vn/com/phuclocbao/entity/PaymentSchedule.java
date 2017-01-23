package vn.com.phuclocbao.entity;

import java.util.Date;

import javax.persistence.GenerationType;

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
  @javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
  private java.lang.Integer id;
  private Date payDate;
  private Date notifiedDate;
  private Date expectedPayDate;
  @javax.persistence.Column(length=1)
  private String finish;
  
  public Date getExpectedPayDate() {
	return expectedPayDate;
  }
	
  public void setExpectedPayDate(Date expectedPayDate) {
		this.expectedPayDate = expectedPayDate;
  }

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

  

  
  public Date getPayDate() {
	return payDate;
}

public void setPayDate(Date payDate) {
	this.payDate = payDate;
}

public Date getNotifiedDate() {
	return notifiedDate;
}

public void setNotifiedDate(Date notifiedDate) {
	this.notifiedDate = notifiedDate;
}



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
