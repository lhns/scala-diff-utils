package com.github.difflib.algorithm.myers

import com.github.difflib.algorithm._
import com.github.difflib.patch.DeltaType

/**
  * A clean-room implementation of Eugene Myers greedy differencing algorithm.
  */
class MyersDiff[T](equals: (T, T) => Boolean = (_: T) == (_: T)) extends DiffAlgorithm[T] {
  /**
    * {@inheritDoc }
    *
    * Return empty diff if get the error while procession the difference.
    */
  @throws[DiffException]
  override def computeDiff(source: Seq[T], target: Seq[T],
                           progress: DiffAlgorithmListener): List[Change] = {
    progress.diffStart()
    val path: PathNode = buildPath(source, target, progress)
    val result: List[Change] = buildRevision(path)
    progress.diffEnd()
    result
  }

  /**
    * Computes the minimum diffpath that expresses de differences between the original and revised
    * sequences, according to Gene Myers differencing algorithm.
    *
    * @param orig The original sequence.
    * @param rev  The revised sequence.
    * @return A minimum { @link PathNode Path} accross the differences graph.
    * @throws DifferentiationFailedException if a diff path could not be found.
    */
  @throws[DifferentiationFailedException]
  private def buildPath(orig: Seq[T], rev: Seq[T],
                        progress: DiffAlgorithmListener): PathNode = {
    // these are local constants
    val N = orig.size
    val M = rev.size

    val MAX = N + M + 1
    val size = 1 + 2 * MAX
    val middle = size / 2
    val diagonal = new Array[PathNode](size)

    diagonal(middle + 1) = PathNode(0, -1, isSnake = true, isBootstrap = true, null)

    for (d <- 0 until MAX) {
      progress.diffStep(d, MAX)

      for (k <- -d to d by 2) {
        val kmiddle = middle + k
        val kplus = kmiddle + 1
        val kminus = kmiddle - 1

        var i = 0

        val prev =
          if ((k == -d) || (k != d && diagonal(kminus).i < diagonal(kplus).i)) {
            i = diagonal(kplus).i
            diagonal(kplus)
          } else {
            i = diagonal(kminus).i + 1
            diagonal(kminus)
          }

        diagonal(kminus) = null // no longer used

        var j = i - k

        diagonal(kmiddle) = {
          val node = PathNode(i, j, isSnake = false, isBootstrap = false, prev)

          while (i < N && j < M && equals(orig(i), rev(j))) {
            i += 1
            j += 1
          }

          if (i != node.i)
            PathNode(i, j, isSnake = true, isBootstrap = false, node)
          else
            node
        }

        if (i >= N && j >= M)
          return diagonal(kmiddle)
      }
      diagonal(middle + d - 1) = null
    }
    // According to Myers, this cannot happen
    throw new DifferentiationFailedException("could not find a diff path")
  }

  /**
    * Constructs a {@link Patch} from a difference path.
    *
    * @param path The path.
    * @return A { @link Patch} script corresponding to the path.
    * @throws DifferentiationFailedException if a { @link Patch} could not be built from the given
    *                                        path.
    */
  private def buildRevision(actualPath: PathNode): List[Change] = {
    var path = actualPath
    var changes: List[Change] = Nil

    if (path.isSnake)
      path = path.prev

    while (path != null && path.prev != null && path.prev.j >= 0) {
      if (path.isSnake)
        throw new IllegalStateException("bad diffpath: found snake when looking for diff")

      val i = path.i
      val j = path.j

      path = path.prev
      val ianchor = path.i
      val janchor = path.j

      if (ianchor == i && janchor != j)
        changes = Change(DeltaType.Insert, ianchor, i, janchor, j) :: changes
      else if (ianchor != i && janchor == j)
        changes = Change(DeltaType.Delete, ianchor, i, janchor, j) :: changes
      else
        changes = Change(DeltaType.Change, ianchor, i, janchor, j) :: changes

      if (path.isSnake)
        path = path.prev
    }
    changes //.reverse
  }
}
