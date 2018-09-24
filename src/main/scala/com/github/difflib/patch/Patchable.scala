package com.github.difflib.patch

trait Patchable[F, T] {
  def remove(self: F, position: Int, size: Int): F
  def insert(self: F, position: Int, elements: Seq[T]): F
  def toSeq(self: F): Seq[T]
}

object Patchable {
  implicit def SeqPatchable[T]: Patchable[Seq[T], T] = new Patchable[Seq[T], T] {
    override def remove(self: Seq[T], position: Int, size: Int): Seq[T] =
      self.take(position) ++ self.drop(position + size)

    override def insert(self: Seq[T], position: Int, elements: Seq[T]): Seq[T] =
      self.take(position) ++ elements ++ self.drop(position)

    override def toSeq(self: Seq[T]): Seq[T] = self
  }
}
