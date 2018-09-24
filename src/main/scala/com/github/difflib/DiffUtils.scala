package com.github.difflib

import com.github.difflib.algorithm.myers.MyersDiff
import com.github.difflib.algorithm.{DiffAlgorithm, DiffAlgorithmListener, DiffException}
import com.github.difflib.patch.{Patch, PatchFailedException}

object DiffUtils {
  /**
    * Computes the difference between the original and revised list of elements with default diff algorithm
    *
    * @param original  The original text. Must not be { @code null}.
    * @param revised   The revised text. Must not be { @code null}.
    * @param algorithm The diff algorithm. Must not be { @code null}.
    * @param progress  The diff algorithm listener.
    * @return The patch describing the difference between the original and revised sequences. Never { @code null}.
    */
  @throws[DiffException]
  def diff[T](original: Seq[T],
              revised: Seq[T],
              algorithm: DiffAlgorithm[T] = new MyersDiff[T](),
              progress: DiffAlgorithmListener = DiffAlgorithmListener.Empty): Patch[T] =
    Patch.generate(
      original.toList,
      revised.toList,
      algorithm.computeDiff(original, revised, progress)
    )

  /**
    * Patch the original text with given patch
    *
    * @param original the original text
    * @param patch    the given patch
    * @return the revised text
    * @throws PatchFailedException if can't apply patch
    */
  @throws[PatchFailedException]
  def patch[T](original: List[T],
               patch: Patch[T]): List[T] =
    patch.applyTo(original)

  /**
    * Unpatch the revised text for a given patch
    *
    * @param revised the revised text
    * @param patch   the given patch
    * @return the original text
    */
  def unpatch[T](revised: List[T],
                 patch: Patch[T]): List[T] =
    patch.restore(revised)

}
