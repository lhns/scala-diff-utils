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
  def computeDiff(source: Seq[T], target: Seq[T],
                  progress: DiffAlgorithmListener = DiffAlgorithmListener.Empty): Seq[Change]
}
