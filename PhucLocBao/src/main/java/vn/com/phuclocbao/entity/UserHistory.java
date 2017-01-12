package vn.com.phuclocbao.entity;

import vn.com.phuclocbao.entity.base.IBaseEntity;

/**
 */
@SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name="tblUserHistory")
public class UserHistory implements IBaseEntity
{
  /** SerialVersionUID */
  private static final long serialVersionUID = 119425837352802167L;

  /**
   * Identifier
   */
  @javax.persistence.Id
  @javax.persistence.GeneratedValue
  private java.lang.Integer id;
  @javax.persistence.Column(length=255)
  private java.lang.String actionType;
  @javax.persistence.Column(length=30)
  private java.lang.String accountName;
  @javax.persistence.Column(length=255)
  private java.lang.String companyName;
  private java.util.Date happenTime;
  @javax.persistence.Column(length=1000)
  private java.lang.String detail;

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
   * Gets the field actionType.
   * @return the value of the field actionType; may be null.
   */
  public java.lang.String getActionType()
  {
    return actionType;
  }

  /**
   * Sets the field actionType.
   * @param _actionType the new value of the field actionType.
   */
  public void setActionType(java.lang.String _actionType)
  {
    actionType = _actionType;
  }

  

  /**
   * Gets the field accountName.
   * @return the value of the field accountName; may be null.
   */
  public java.lang.String getAccountName()
  {
    return accountName;
  }

  /**
   * Sets the field accountName.
   * @param _accountName the new value of the field accountName.
   */
  public void setAccountName(java.lang.String _accountName)
  {
    accountName = _accountName;
  }

  

  /**
   * Gets the field companyName.
   * @return the value of the field companyName; may be null.
   */
  public java.lang.String getCompanyName()
  {
    return companyName;
  }

  /**
   * Sets the field companyName.
   * @param _companyName the new value of the field companyName.
   */
  public void setCompanyName(java.lang.String _companyName)
  {
    companyName = _companyName;
  }

  

  /**
   * Gets the field happenTime.
   * @return the value of the field happenTime; may be null.
   */
  public java.util.Date getHappenTime()
  {
    return happenTime;
  }

  /**
   * Sets the field happenTime.
   * @param _happenTime the new value of the field happenTime.
   */
  public void setHappenTime(java.util.Date _happenTime)
  {
    happenTime = _happenTime;
  }

  

  /**
   * Gets the field detail.
   * @return the value of the field detail; may be null.
   */
  public java.lang.String getDetail()
  {
    return detail;
  }

  /**
   * Sets the field detail.
   * @param _detail the new value of the field detail.
   */
  public void setDetail(java.lang.String _detail)
  {
    detail = _detail;
  }

}
