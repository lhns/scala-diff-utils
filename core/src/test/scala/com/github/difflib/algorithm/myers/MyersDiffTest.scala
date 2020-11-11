package com.github.difflib.algorithm.myers

import com.github.difflib.algorithm.DiffAlgorithmListener
import com.github.difflib.patch.Patch

import scala.collection.mutable

class MyersDiffTest extends munit.FunSuite {
  test("testDiffMyersExample1Forward") {
    val original = Seq("A", "B", "C", "A", "B", "B", "A")
    val revised = Seq("C", "B", "A", "B", "A", "C")
    val patch = Patch.generate(original, revised, new MyersDiff[String]().computeDiff(original, revised))
    assertNotEquals(patch, null: Patch[String])
    assertEquals(4, patch.deltas.size)
    assertEquals("Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}", patch.toString)
    assertEquals(revised, patch.applyTo(original))
  }

  test("testDiffMyersExample1ForwardWithListener") {
    val original = Seq("A", "B", "C", "A", "B", "B", "A")
    val revised = Seq("C", "B", "A", "B", "A", "C")
    val logdata = new mutable.ListBuffer[String]()
    val patch = Patch.generate(original, revised, new MyersDiff[String]().computeDiff(original, revised, new DiffAlgorithmListener() {
      override def diffStart(): Unit = logdata += "start"

      override def diffStep(value: Int, max: Int): Unit = logdata += (value + " - " + max)

      override def diffEnd(): Unit = logdata += "end"
    }))
    assertNotEquals(patch, null: Patch[String])
    assertEquals(4, patch.deltas.size)
    assertEquals("Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}", patch.toString)
    assertEquals(logdata.toSeq, Seq("start", "0 - 14", "1 - 14", "2 - 14", "3 - 14", "4 - 14", "5 - 14", "end"))
    assertEquals(8, logdata.size)
  }
}
