package com.myltsev

import org.apache.hadoop.conf.Configured
import org.apache.hadoop.util.Tool
import org.apache.hadoop.mapred._
import org.apache.hadoop.io.{ NullWritable, Text, LongWritable }

object CountingSort extends Configured with Tool {
  class Map extends MapReduceBase with Mapper[LongWritable, Text, Bulk, NullWritable] {
    var zeroCount, minusCount, plusCount = 0L

    def map(key: LongWritable, value: Text, output: OutputCollector[Bulk, NullWritable], reporter: Reporter): Unit = ???

    override def close() = ???
  }

  class Reduce extends MapReduceBase with Reducer[Bulk, NullWritable, Text, NullWritable] {
    def reduce(key: Bulk, valuesCounts: java.util.Iterator[NullWritable], output: OutputCollector[Text, NullWritable], reporter: Reporter): Unit = ???

    override def close() = ???
  }

  def run(args: Array[String]): Int = ???
}
