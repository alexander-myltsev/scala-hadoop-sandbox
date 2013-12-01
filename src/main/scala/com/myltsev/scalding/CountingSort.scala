package com.myltsev.scalding

import com.twitter.scalding._
import org.apache.hadoop.util.ToolRunner
import org.apache.hadoop.conf.Configuration
import scala.io.Source

class CountingSort(args: Args) extends Job(args) {
  val tokenizerRegex = """\W+"""

  TextLine(args("input"))
    .read
    .map('line -> 'number) { line: String ⇒ line.toInt }
    .groupBy('number) { identity }
    .write(Tsv(args("output"), fields = { 'number }))
}

/**
 * Run this example, using default arguments if none are specified.
 */
object CountingSort {
  val name = "com.myltsev.scalding.CountingSort"
  val message = "Sort [-1, 0, 1] in ascending order"

  def main(args: Array[String]) {
    if (args.length != 0) {
      run(name, message, args)
    } else {
      run(name, message,
        Array("--local",
          "--input", "data/in/data_sample.txt",
          "--output", "data/out/numbers-sorted.txt"))

      printSomeOutput("data/out/numbers-sorted.txt")
    }
  }

  def run(name: String, message: String, args: Array[String]) = {
    println(s"\n==== $name " + ("===" * 20))
    println(message)
    val argsWithName = name +: args
    println(s"Running: ${argsWithName.mkString(" ")}")
    ToolRunner.run(new Configuration, new Tool, argsWithName)
  }

  def printSomeOutput(outputFileName: String, message: String = "") = {
    if (message.length > 0) println(message)
    println("Output in $outputFileName:")
    Source.fromFile(outputFileName).getLines.take(10) foreach println
    println("...\n")
  }
}
