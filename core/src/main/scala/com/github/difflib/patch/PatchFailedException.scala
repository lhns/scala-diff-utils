package com.github.difflib.patch

/**
  * Thrown whenever a delta cannot be applied as a patch to a given text.
  *
  * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
  */
class PatchFailedException(msg: String) extends DiffException(msg)
