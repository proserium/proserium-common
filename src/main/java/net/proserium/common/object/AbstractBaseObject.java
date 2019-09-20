/*
 * AbstractBaseObject.java
 *
 * Copyright by proserium, all rights reserved.
 * MIT License: https://mit-license.org
 */
package net.proserium.common.object;

import java.io.Serializable;
import jptools.exception.ReadOnlyModeException;
import jptools.pattern.to.TransferObject;
import jptools.pattern.vo.SerialCloneHelper;
import jptools.pattern.vo.ValueObject;
import jptools.pattern.vo.ValueObjectHelper;


/**
 * Defines the base abstract base object.
 *
 * @author Patrick Meier
 */
public class AbstractBaseObject implements ValueObject, TransferObject, Serializable, Cloneable {
  private static final long serialVersionUID = 314871692465334371L;

  /** It is used to seperate the fields in the toString() method in each value object. */
  protected static final String SEPARATOR = "\n";
  private boolean readOnly;


  /**
   * Constructor.
   */
  protected AbstractBaseObject() {
    this.readOnly = false;
  }


  /**
   * @see jptools.pattern.vo.ValueObject#readOnly()
   */
  @Override
  public void readOnly() {
    this.readOnly = true;
  }


  /**
   * @see jptools.pattern.vo.ValueObject#isReadOnly()
   */
  @Override
  public boolean isReadOnly() {
    return this.readOnly;
  }


  /**
   * Test, if the class is in read only mode, if <code>true</code> it throws a <code>ReadOnlyModeException</code>.
   *
   * @exception ReadOnlyModeException Throws an exception if the value object is read only.
   */
  protected void checkReadOnlyMode() {
    if (this.isReadOnly()) {
      throw new ReadOnlyModeException();
    }
  }


  /**
   * Returns the hashCode.
   *
   * @return The hashCode.
   */
  @Override
  public int hashCode() {
    return 42;
  }


  /**
   * Implements the default functionality of the equals method. Subclasses should implements their own equals method like:<br>
   *
   * <pre>
   * public boolean equals( java.lang.Object other )
   * {
   *     if( this == other )
   *         return true;
   *
   *     if( !super.equals( other ) )
   *         return false;
   *
   *     if( x !=((MyObject)other).x )
   *         return false;
   *
   *     if( !y.equals( ((MyObject)other).y ) )
   *         return false;
   *
   *     ...
   *     return true;
   * }
   * </pre>
   *
   * @return <code>true</code> if the objects are equals; <code>false</code> otherwise.
   * @param other the object to compare
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (other.getClass() != this.getClass()) {
      return false;
    }

    // AbstractBaseObject oth = (AbstractBaseObject)other;
    return true;
  }


  /**
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() {
    AbstractBaseObject obj;
    try {
      obj = (AbstractBaseObject) super.clone();
    } catch (CloneNotSupportedException e) {
      // this shouldn't happen, since we are Cloneable
      InternalError ex = new InternalError("Could not clone object " + getClass().getName() + ": " + e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    }

    // the readOnly flag will not cloned!
    obj.readOnly = false;
    return obj;
  }


  /**
   * This method returns a String representation of this <code>AbstractBaseObject</code>.
   *
   * @return The <code>AbstractBaseObject</code> as String.
   */
  @Override
  public String toString() {
    return "readOnly: '" + readOnly + "'";
  }


  /**
   * Compares two double values to see if they are equal.
   *
   * @param d1 The first double to compare.
   * @param d2 The second double to compare.
   * @return Returns true if the double values are equals, false otherwise.
   */
  protected boolean isEqual(double d1, double d2) {
    if (compareToDoubleHelper(d1, d2) == 0) {
      return true;
    }

    return false;
  }


  /**
   * Compares two float values to see if they are equal.
   *
   * @param f1 The first float to compare.
   * @param f2 The second float to compare.
   * @return Returns true if the float values are equals, false otherwise.
   */
  protected boolean isEqual(float f1, float f2) {
    if (compareToFloatHelper(f1, f2) == 0) {
      return true;
    }

    return false;
  }


  /**
   * Helper method to compute the hashCode of arbitrary arrays.
   *
   * @param o the object to compute the hashCode.
   * @return Returns the hash code.
   */
  protected int hashCodeHelper(Object o) {
    return ValueObjectHelper.getInstance().hashCodeHelper(o);
  }


  /**
   * Helper method to compare two arbitrary arrays.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return Returns <code>true</code> if the objects are the same; <code>false</code> otherwise.
   */
  protected boolean equalsHelper(Object o1, Object o2) {
    return ValueObjectHelper.getInstance().equalsHelper(o1, o2);
  }


  /**
   * Helper method to compare two objects.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return Returns <code>true</code> if the objects are the same; <code>false</code> otherwise.
   */
  protected boolean equalsObjectHelper(Object o1, Object o2) {
    return ValueObjectHelper.getInstance().equalsObjectHelper(o1, o2);
  }


  /**
   * Helper method to test compare two double values.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return A negative integer, zero, or a positive integer as the first argument is less than,
   *         equal to, or greater than the second.
   */
  protected int compareToDoubleHelper(double o1, double o2) {
    return ValueObjectHelper.getInstance().compareToDoubleHelper(o1, o2);
  }


  /**
   * Helper method to test compare two float values.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return A negative integer, zero, or a positive integer as the first argument is less than,
   *         equal to, or greater than the second.
   */
  protected int compareToFloatHelper(float o1, float o2) {
    return ValueObjectHelper.getInstance().compareToFloatHelper(o1, o2);
  }


  /**
   * Helper method to compare two arbitrary arrays.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return A negative integer, zero, or a positive integer as the first argument is less than,
   *         equal to, or greater than the second.
   */
  protected int compareToArrayHelper(Object o1, Object o2) {
    return ValueObjectHelper.getInstance().compareToArrayHelper(o1, o2);
  }


  /**
   * Helper method to test compare two arbitrary arrays.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return A negative integer, zero, or a positive integer as the first argument is less than,
   *         equal to, or greater than the second.
   */
  protected int compareToObjectHelper(Object o1, Object o2) {
    return ValueObjectHelper.getInstance().compareToObjectHelper(o1, o2);
  }


  /**
   * Helper method to create a string of arbitrary arrays.
   *
   * @param o the object to convert to a string.
   * @return Returns the created string.
   */
  protected StringBuilder toStringHelper(Object o) {
    return ValueObjectHelper.getInstance().toStringHelper(o);
  }


  /**
   * Helper method to clone a given object.
   *
   * @param o the object to clone
   * @return The cloned object.
   */
  protected Object cloneHelper(Object o) {
    return ValueObjectHelper.getInstance().cloneHelper(o);
  }


  /**
   * Helper method to clone a given object.
   *
   * @param <T> the type
   * @param o the object to clone
   * @return The cloned object.
   */
  protected <T> T serialCloneHelper(T o) {
    return SerialCloneHelper.clone(o);
  }
}
