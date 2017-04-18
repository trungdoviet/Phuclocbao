package vn.com.phuclocbao.entity;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import vn.com.phuclocbao.entity.base.IBaseEntity;

/**
 */
@SuppressWarnings("all")
@javax.annotation.Generated(comments="This is the java file of the ivy data class AtomicCounter", value={"ch.ivyteam.ivy.scripting.streamInOut.IvyScriptJavaClassBuilder"})
@javax.persistence.Entity
@javax.persistence.Table(name="tblCounter")
@NamedQueries({
	@NamedQuery(name = "getCounterByKeyAndCompanyId", query = "SELECT counter FROM AtomicCounter counter WHERE counter.keyName = :keyName AND counter.content = :companyId"),
})

public class AtomicCounter implements IBaseEntity
{
  /** SerialVersionUID */
  private static final long serialVersionUID = -8453845827319350975L;

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

  @javax.persistence.Column(length=255)
  private java.lang.String keyName;

  /**
   * Gets the field keyName.
   * @return the value of the field keyName; may be null.
   */
  public java.lang.String getKeyName()
  {
    return keyName;
  }

  /**
   * Sets the field keyName.
   * @param _keyName the new value of the field keyName.
   */
  public void setKeyName(java.lang.String _keyName)
  {
    keyName = _keyName;
  }

  private java.util.Date setTime;

  /**
   * Gets the field setTime.
   * @return the value of the field setTime; may be null.
   */
  public java.util.Date getSetTime()
  {
    return setTime;
  }

  /**
   * Sets the field setTime.
   * @param _setTime the new value of the field setTime.
   */
  public void setSetTime(java.util.Date _setTime)
  {
    setTime = _setTime;
  }

  @javax.persistence.Column(length=1024)
  private java.lang.String content;

  /**
   * Gets the field content.
   * @return the value of the field content; may be null.
   */
  public java.lang.String getContent()
  {
    return content;
  }

  /**
   * Sets the field content.
   * @param _content the new value of the field content.
   */
  public void setContent(java.lang.String _content)
  {
    content = _content;
  }

}
