package com.github.difflib.algorithm.myers

import com.github.difflib.algorithm.DiffAlgorithmListener
import com.github.difflib.algorithm.DiffException
import com.github.difflib.patch.Patch

object MyersDiffTest {
  @throws[DiffException]
  def testDiffMyersExample1Forward(): Unit = {
    val original = List("A", "B", "C", "A", "B", "B", "A")
    val revised = List("C", "B", "A", "B", "A", "C")
    val patch = Patch.generate(original, revised, new MyersDiff[String]().computeDiff(original, revised))
    require(patch != null)
    require(4 == patch.deltas.size)
    println(patch.toString)
    require("Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}" == patch.toString)
  }

  def main(args: Array[String]): Unit = {
    testDiffMyersExample1Forward()
  }

  /*@throws[DiffException]
  def testDiffMyersExample1ForwardWithListener(): Unit = {
    val original = Arrays.asList("A", "B", "C", "A", "B", "B", "A")
    val revised = Arrays.asList("C", "B", "A", "B", "A", "C")
    val logdata = new ArrayList[String]
    val patch = Patch.generate(original, revised, new MyersDiff[String]().computeDiff(original, revised, new DiffAlgorithmListener() {
      override def diffStart(): Unit = {
        logdata.add("start")
      }
      override
      def diffStep(value: Int, max: Int): Unit = {
        logdata.add(value + " - " + max)
      }
      override
      def diffEnd(): Unit = {
        logdata.add("end")
      }
    }))
    assertNotNull(patch)
    assertEquals(4, patch.getDeltas.size)
    assertEquals("Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}", patch.toString)
    System.out.println(logdata)
    assertEquals(8, logdata.size)
  }*/
}
