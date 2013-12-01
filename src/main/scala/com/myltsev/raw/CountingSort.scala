package com.myltsev.raw

import org.apache.hadoop.conf.Configured
import org.apache.hadoop.util.Tool
import org.apache.hadoop.mapred._
import org.apache.hadoop.io.{ NullWritable, Text, LongWritable }
import java.text.SimpleDateFormat
import java.util.Date
import org.apache.hadoop.fs.Path

object CountingSort extends Configured with Tool {
  class Map extends MapReduceBase with Mapper[LongWritable, Text, Bulk, NullWritable] {
    var zeroCount, minusCount, plusCount = 0L
    var outputCollector: OutputCollector[Bulk, NullWritable] = _

    def map(key: LongWritable, value: Text, output: OutputCollector[Bulk, NullWritable], reporter: Reporter): Unit = {
      if (outputCollector == null)
        outputCollector = output
      value.charAt(0) match {
        case '1' ⇒ plusCount += 1
        case '-' ⇒ minusCount += 1
        case '0' ⇒ zeroCount += 1
        case _   ⇒ ???
      }
    }

    override def close() = {
      if (zeroCount != 0) outputCollector.collect(Bulk(BulkType.Zero, zeroCount), NullWritable.get)
      if (minusCount != 0) outputCollector.collect(Bulk(BulkType.MinusOne, minusCount), NullWritable.get)
      if (plusCount != 0) outputCollector.collect(Bulk(BulkType.PlusOne, plusCount), NullWritable.get)
    }
  }

  class Reduce extends MapReduceBase with Reducer[Bulk, NullWritable, Text, NullWritable] {
    def reduce(key: Bulk, valuesCounts: java.util.Iterator[NullWritable], output: OutputCollector[Text, NullWritable], reporter: Reporter): Unit = {
      while (valuesCounts.hasNext()) {
        valuesCounts.next()
        key match {
          case Bulk(tp, count) ⇒ (1L to count).foreach(_ ⇒ output.collect(new Text(tp.toString), NullWritable.get))
          case null            ⇒ ???
        }
      }
    }
  }

  def run(args: Array[String]): Int = {
    val conf = new JobConf(this.getClass)
    conf.setJobName("Counting sort")

    conf.setJarByClass(this.getClass)

    val currentTime = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss_zzz").format(new Date)
    val (inputPath, outputPath) =
      if (args.size < 2) ("data/in/data_sample.txt", s"data/out-$currentTime")
      else (args(0), s"${args(1)}-$currentTime")
    FileInputFormat.addInputPath(conf, new Path(inputPath))
    FileOutputFormat.setOutputPath(conf, new Path(outputPath))

    conf.setMapOutputKeyClass(classOf[Bulk])
    conf.setMapOutputValueClass(classOf[NullWritable])

    conf.setMapperClass(classOf[Map])
    conf.setReducerClass(classOf[Reduce])

    JobClient.runJob(conf)
    0
  }
}
