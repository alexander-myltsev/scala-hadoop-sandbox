package com.myltsev

import org.specs2.mutable.Specification

class BulkSpec extends Specification {
  "companion object" should {
    "construct correctly with parameters" in {
      val bulk = Bulk(BulkType.PlusOne, 7)
      bulk.tp === BulkType.PlusOne
      bulk.count === 7
    }

    "construct correctly without parameters" in {
      val bulk = Bulk()
      bulk.tp === BulkType.Undefined
      bulk.count === 0
    }

    "unapply successfully if not null" in {
      val bulk = Bulk(BulkType.PlusOne, 7)
      val Bulk(tp, count) = bulk
      tp === bulk.tp
      count === bulk.count
    }

    "unapply successfully to None if null" in {
      val bulk: Bulk = null
      bulk match {
        case Bulk(_, _) ⇒ failure
        case _          ⇒ success
      }
    }
  }

  "compareTo" should {
    "compare undefined types" in todo

    "compare by type and then by count" in {
      val bulk1 = Bulk(BulkType.PlusOne, 7)
      val bulk2 = Bulk(BulkType.PlusOne, 7)
      val bulk3 = Bulk(BulkType.MinusOne, 4)
      val bulk4 = Bulk(BulkType.MinusOne, 7)
      val bulk5 = Bulk(BulkType.Zero, 7)
      val bulk6 = Bulk(BulkType.Zero, 0)

      bulk1.compareTo(bulk2) === 0
      bulk3.compareTo(bulk4) must be_<(0)
      bulk5.compareTo(bulk6) must be_>(0)

      bulk1.compareTo(bulk3) must be_>(0)
      bulk5.compareTo(bulk3) must be_>(0)
      bulk1.compareTo(bulk5) must be_>(0)
    }
  }
}
