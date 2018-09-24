package com.github.difflib.algorithm.myers

import com.github.difflib.algorithm.{DiffAlgorithmListener, DiffException}
import com.github.difflib.patch.Patch

import scala.collection.mutable

object MyersDiffTest {
  def assertNotNull(e: Any): Unit = require(e != null)
  def assertEquals[T](a: T, b: T): Unit = require(a == b)

  @throws[DiffException]
  def testDiffMyersExample1Forward(): Unit = {
    val original = Seq("A", "B", "C", "A", "B", "B", "A")
    val revised = Seq("C", "B", "A", "B", "A", "C")
    val patch = Patch.generate(original, revised, new MyersDiff[String]().computeDiff(original, revised))
    assertNotNull(patch)
    assertEquals(4, patch.deltas.size)
    println(patch.toString)
    require("Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}" == patch.toString)
  }

  def main(args: Array[String]): Unit = {
    testDiffMyersExample1ForwardWithListener()
  }

  @throws[DiffException]
  def testDiffMyersExample1ForwardWithListener(): Unit = {
    val original = Seq("A", "B", "C", "A", "B", "B", "A")
    val revised = Seq("C", "B", "A", "B", "A", "C")
    val logdata = new mutable.ListBuffer[String]()
    val patch = Patch.generate(original, revised, new MyersDiff[String]().computeDiff(original, revised, new DiffAlgorithmListener() {
      override def diffStart(): Unit = logdata += "start"
      override def diffStep(value: Int, max: Int): Unit = logdata += (value + " - " + max)
      override def diffEnd(): Unit = logdata += "end"
    }))
    assertNotNull(patch)
    assertEquals(4, patch.deltas.size)
    assertEquals("Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}", patch.toString)
    println(logdata)
    assertEquals(8, logdata.size)
  }
}
