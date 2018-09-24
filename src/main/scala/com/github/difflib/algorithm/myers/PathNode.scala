package com.github.difflib.algorithm.myers

/**
  * A node in a diffpath.
  *
  * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
  * @see DiffNode
  * @see Snake
  *
  */
case class PathNode private(i: Int,
                            j: Int,
                            isSnake: Boolean,

                            /**
                              * Is this a bootstrap node?
                              * <p>
                              * In bottstrap nodes one of the two corrdinates is less than zero.
                              *
                              * @return tru if this is a bootstrap node.
                              */
                            isBootstrap: Boolean,
                            prev: PathNode) {
  /**
    * Skips sequences of {@link DiffNode DiffNodes} until a {@link Snake} or bootstrap node is found, or the end of the
    * path is reached.
    *
    * @return The next first { @link Snake} or bootstrap node in the path, or <code>null</code> if none found.
    */
  def previousSnake: PathNode =
    if (isBootstrap)
      null
    else if (!isSnake && prev != null)
      prev.previousSnake
    else
      this

  /**
    * {@inheritDoc }
    */
  override def toString: String = {
    val buf = new StringBuilder("[")
    var node = this
    while (node != null) {
      buf.append("(")
      buf.append(Integer.toString(node.i))
      buf.append(",")
      buf.append(Integer.toString(node.j))
      buf.append(")")
      node = node.prev
    }
    buf.append("]")
    buf.toString
  }
}

object PathNode {
  def apply(i: Int,
            j: Int,
            isSnake: Boolean,
            isBootstrap: Boolean,
            prev: PathNode): PathNode = {
    val newPrev =
      if (isSnake)
        prev
      else if (prev == null)
        null
      else
        prev.previousSnake

    new PathNode(i, j, isSnake, isBootstrap, newPrev)
  }
}
