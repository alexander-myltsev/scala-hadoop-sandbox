package com.myltsev.raw

import org.apache.hadoop.util.ToolRunner
import org.apache.hadoop.conf.Configuration

object Driver {
  def main(args: Array[String]): Unit = {
    ToolRunner.run(new Configuration, CountingSort, args)
  }
}
