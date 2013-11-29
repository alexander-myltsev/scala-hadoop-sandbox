Counting Sort Sample
====================

Task
----

The task is to sort (ascending order) an array of numbers from range [-1, 0, 1] using Hadoop MapReduce.

Solution
--------

Test it: `sbt test`

Run it locally: `sbt "run data/in/data_sample.txt data/out"`

Args are optional. Default values are `data/in/data_sample.txt` and `data/out`. Output directory is postfixed with current time formatted as `"yyyy.MM.dd_HH.mm.ss_zzz"`

Produce jar: `sbt assembly`

Then run it on a Hadoop `hadoop jar target/scala-hadoop-sandbox-0.1.jar /tmp/in/data.txt /tmp/out`

Have fun!