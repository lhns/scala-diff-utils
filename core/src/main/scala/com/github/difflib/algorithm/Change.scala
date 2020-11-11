package com.github.difflib.algorithm

import com.github.difflib.patch.DeltaType

/**
  *
  * @author <a href="t.warneke@gmx.net">Tobias Warneke</a>
  */
case class Change(deltaType: DeltaType,
                  startOriginal: Int,
                  endOriginal: Int,
                  startRevised: Int,
                  endRevised: Int)
