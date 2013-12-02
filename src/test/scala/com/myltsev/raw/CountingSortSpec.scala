package com.myltsev.raw

import org.specs2.mutable.Specification

class CountingSortSpec extends Specification {
  import ops._

  "mapper" should {
    "work correctly for single input" in {
      List("0") mustBeMappedTo List((BulkType.Zero, 1))
    }

    "sort two ints" in {
      List("1", "-1") mustBeMappedTo List((BulkType.MinusOne, 1), (BulkType.PlusOne, 1))
    }

    "sort three separate ints" in {
      List("0", "1", "-1") mustBeMappedTo List((BulkType.MinusOne, 1), (BulkType.Zero, 1), (BulkType.PlusOne, 1))
    }

    "sort two repeating ints" in {
      List("1", "1", "-1") mustBeMappedTo List((BulkType.MinusOne, 1), (BulkType.PlusOne, 2))
    }
  }

  "reducer" should {
    "work correctly" in todo
  }
}
