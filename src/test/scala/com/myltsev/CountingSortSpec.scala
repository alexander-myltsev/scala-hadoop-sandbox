package com.myltsev

import org.specs2.mutable.Specification
import java.util.{ List ⇒ JList }
import org.apache.hadoop.io.{ NullWritable, LongWritable, Text }
import org.apache.hadoop.mapred.Mapper
import org.apache.hadoop.mrunit.MapDriver
import org.apache.hadoop.mrunit.types.{ Pair ⇒ MRPair }

class CountingSortSpec extends Specification {

  case class Input(input: String*) {
    def mustBeMappedTo(expectedOutput: List[(BulkType.BulkType, Int)]) = {
      val mapper: Mapper[LongWritable, Text, Bulk, NullWritable] = new CountingSort.Map()
      val mapDriver: MapDriver[LongWritable, Text, Bulk, NullWritable] =
        new MapDriver[LongWritable, Text, Bulk, NullWritable](mapper)

      import scala.collection.JavaConversions._

      val jInput: JList[MRPair[LongWritable, Text]] =
        input
          .zipWithIndex
          .map { case (line, offset) ⇒ new MRPair(new LongWritable(offset), new Text(line)) }

      val jOutput: JList[MRPair[Bulk, NullWritable]] =
        expectedOutput.map(x ⇒ new MRPair(Bulk(x._1, x._2), NullWritable.get))

      mapDriver.withAll(jInput).withAllOutput(jOutput).runTest(false)
    }
  }

  "mapper" should {
    "work correctly for single input" in {
      Input("0") mustBeMappedTo List((BulkType.Zero, 1))
    }

    "sort two ints" in {
      Input("1", "-1") mustBeMappedTo List((BulkType.MinusOne, 1), (BulkType.PlusOne, 1))
    }

    "sort three separate ints" in {
      Input("0", "1", "-1") mustBeMappedTo List((BulkType.MinusOne, 1), (BulkType.Zero, 1), (BulkType.PlusOne, 1))
    }

    "sort two repeating ints" in {
      Input("1", "1", "-1") mustBeMappedTo List((BulkType.MinusOne, 1), (BulkType.PlusOne, 2))
    }
  }

  "reducer" should {
    "work correctly" in todo
  }
}
