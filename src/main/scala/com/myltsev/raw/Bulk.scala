package com.myltsev.raw

import org.apache.hadoop.io._
import java.io.{ DataOutput, DataInput }

object BulkType extends Enumeration {
  type BulkType = Value
  val Undefined = Value(Int.MaxValue, "undefined")
  val MinusOne = Value(-1, "-1")
  val Zero = Value(0, "0")
  val PlusOne = Value(1, "1")
}

class Bulk extends WritableComparable[Bulk] {
  var count = 0L
  var tp = BulkType.Undefined

  def readFields(in: DataInput) = {
    tp = in.readInt() match {
      case x if x == BulkType.MinusOne.id ⇒ BulkType.MinusOne
      case x if x == BulkType.Zero.id     ⇒ BulkType.Zero
      case x if x == BulkType.PlusOne.id  ⇒ BulkType.PlusOne
      case _                              ⇒ ???
    }
    count = in.readLong()
  }

  def write(out: DataOutput) = {
    out.writeInt(tp.id)
    out.writeLong(count)
  }

  def compareTo(b: Bulk): Int = (tp, b.tp) match {
    case (BulkType.Undefined, _) | (_, BulkType.Undefined) ⇒ ???
    case (thisTp, thatTp) ⇒
      val tpComp = thisTp.compareTo(thatTp)
      if (tpComp == 0) count.compareTo(b.count)
      else tpComp
  }

  override def hashCode() =
    tp.hashCode + count.hashCode * 17

  override def equals(o: Any) = o match {
    case (b: Bulk) ⇒ tp == b.tp && count == b.count
    case _         ⇒ false
  }

  override def toString() = s"Bulk($tp, $count)"
}

object Bulk {
  def apply(tp: BulkType.BulkType, count: Long) = {
    val bulk = new Bulk
    bulk.count = count
    bulk.tp = tp
    bulk
  }

  def apply() = new Bulk

  def unapply(b: Bulk) = Option(b).map(x ⇒ (x.tp, x.count))
}
