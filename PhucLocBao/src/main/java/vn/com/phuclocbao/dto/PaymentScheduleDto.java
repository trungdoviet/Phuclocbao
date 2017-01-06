package vn.com.phuclocbao.dto;

import java.util.Date;

import vn.com.phuclocbao.dto.base.IBaseDTO;

/**
 */
public class PaymentScheduleDto implements IBaseDTO
{

  /**
	 * 
	 */
	private static final long serialVersionUID = -2899315636278632718L;
/**
   * Identifier
   */
  private java.lang.Integer id;
  private Date expectedPayDate;
  
  
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

  private Date payDate;

  private Date notifiedDate;

  
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
