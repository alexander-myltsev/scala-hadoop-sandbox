import com.myltsev.raw.{ CountingSort, Bulk, BulkType }
import java.util.{ List ⇒ JList }
import org.apache.hadoop.io.{ NullWritable, LongWritable, Text }
import org.apache.hadoop.mapred.Mapper
import org.apache.hadoop.mrunit.MapDriver
import org.apache.hadoop.mrunit.types.{ Pair ⇒ MRPair }

package object ops {
  implicit class ListCompanionOps(val input: List[String]) extends AnyVal {
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
}