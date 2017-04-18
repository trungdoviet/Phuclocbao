package vn.com.phuclocbao.dto;

import vn.com.phuclocbao.dto.base.IBaseDTO;

/**
 */

public class AtomicCounterDto implements IBaseDTO
{

	private static final long serialVersionUID = 5599628985298270981L;
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
