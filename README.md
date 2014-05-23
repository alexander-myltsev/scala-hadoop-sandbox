About
=====

Supporting samples for [a talk at MIPT](https://github.com/alexander-myltsev/mipt-hadoop-talk).

Counting Sort Sample
====================

Task
----

The task is to sort (ascending order) an array of numbers from range [-1, 0, 1] using Hadoop MapReduce.

Solution (raw Hadoop)
---------------------

Test it: `sbt test`

Run it locally: `sbt "run data/in/data_sample.txt data/out"`

Args are optional. Default values are `data/in/data_sample.txt` and `data/out`. Output directory is postfixed with current time formatted as `"yyyy.MM.dd_HH.mm.ss_zzz"`

Produce jar: `sbt assembly`

Then run it on a Hadoop: `hadoop jar target/scala-hadoop-sandbox-0.1.jar com.myltsev.raw.Driver /tmp/in/data.txt /tmp/out`

Solution ([Twitter Scalding](https://github.com/twitter/scalding))
------------------------------------------------------------------

Run it locally: `sbt "run-main com.myltsev.scalding.CountingSort"`

Result should be in "data/out/numbers-sorted.txt"


Have fun!
