package com.github.difflib.algorithm

/**
  * Interface of a diff algorithm.
  *
  * @author Tobias Warneke (t.warneke@gmx.net)
  */
trait DiffAlgorithm[T] {
  /**
    * Computes the changeset to patch the source list to the target list.
    *
    * @param source   source data
    * @param target   target data
    * @param progress progress listener
    * @return
    * @throws DiffException
    */
  @throws[DiffException]
  def computeDiff(source: List[T], target: List[T], progress: DiffAlgorithmListener): List[Change]

  /**
    * Simple extension to compute a changeset using arrays.
    *
    * @param source
    * @param target
    * @param progress
    * @return
    * @throws com.github.difflib.algorithm.DiffException
    */
  @throws[DiffException]
  def computeDiff(source: Seq[T], target: Seq[T],
                  progress: DiffAlgorithmListener = DiffAlgorithmListener.Empty): List[Change] =
    computeDiff(source.toList, target.toList, progress)
}
