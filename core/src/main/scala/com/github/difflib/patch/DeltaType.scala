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
  object Change extends DeltaType

  /**
    * A delete from the original.
    */
  object Delete extends DeltaType

  /**
    * An insert into the original.
    */
  object Insert extends DeltaType

  /**
    * An do nothing.
    */
  //object Equal extends DeltaType
}
