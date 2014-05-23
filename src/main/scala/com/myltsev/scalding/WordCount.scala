package com.myltsev.scalding

import com.twitter.scalding._

class WordCount(args: Args) extends Job(args) {
  TextLine(args("input"))
    .flatMap('line -> 'word) { line: String ⇒ tokenize(line) }
    .groupBy('word) { _.size }
    .write(Tsv(args("output")))

  // Split a piece of text into individual words.
  def tokenize(text: String): Array[String] = {
    // Lowercase each word and remove punctuation.
    text.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+")
  }
}

/**
 * Run this example, using default arguments if none are specified.
 */
object WordCount {
  import org.apache.hadoop.util.ToolRunner
  import org.apache.hadoop.conf.Configuration
  import scala.io.Source

  val name = "com.myltsev.scalding.WordCount"
  val message = "Counts words"

  def main(args: Array[String]) {
    if (args.length != 0) {
      run(name, message, args)
    } else {
      run(name, message,
        Array("--local",
          "--input", "data/in/words_sample.txt",
          "--output", "data/out/words-counted.txt"))

      printSomeOutput("data/out/words-counted.txt")
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
