package com.github.difflib.patch

sealed trait DeltaType

/**
  * Specifies the type of the delta.
  *
  */
object DeltaType {

  /**
    * A change in the original.
    */
  object CHANGE extends DeltaType

  /**
    * A delete from the original.
    */
  object DELETE extends DeltaType

  /**
    * An insert into the original.
    */
  object INSERT extends DeltaType

  /**
    * An do nothing.
    */
  //object EQUAL extends DeltaType
}
