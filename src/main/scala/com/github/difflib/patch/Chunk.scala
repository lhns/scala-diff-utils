package com.github.difflib.patch

/**
  * Holds the information about the part of text involved in the diff process
  *
  * <p>
  * Text is represented as <code>Object[]</code> because the diff engine is capable of handling more than plain ascci. In
  * fact, arrays or lists of any type that implements {@link java.lang.Object#hashCode hashCode()} and
  * {@link java.lang.Object#equals equals()} correctly can be subject to differencing using this library.
  * </p>
  *
  * @author <a href="dm.naumenko@gmail.com>Dmitry Naumenko</a>
  * @param T The type of the compared elements in the 'lines'.
  */
case class Chunk[T](position: Int, lines: List[T]) {
  val size: Int = lines.size

  /**
    * Returns the index of the last line of the chunk.
    */
  def last: Int = position + size - 1

  /**
    * Verifies that this chunk's saved text matches the corresponding text in the given sequence.
    *
    * @param target the sequence to verify against.
    * @throws com.github.difflib.patch.PatchFailedException
    */
  @throws[PatchFailedException]
  def verify(target: List[T]): Unit = {
    if (position > target.size || last > target.size)
      throw new PatchFailedException("Incorrect Chunk: the position of chunk > target size")

    for (i <- 0 until size)
      if (target(position + i) != lines(i))
        throw new PatchFailedException("Incorrect Chunk: the chunk content doesn't match the target")
  }

  override def toString =
    s"[position: ${position}, size: ${size}, lines: ${lines}]"
}
