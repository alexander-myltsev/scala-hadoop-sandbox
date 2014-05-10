name := "scala-hadoop-sandbox"

organization := "com.myltsev"

version := "0.1"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "MVN Repo" at "http://mvnrepository.com/artifact/",
  "conjars" at "http://conjars.org/repo",
  "twitter" at "http://maven.twttr.com/",
  "clojars" at "http://clojars.org/repo"
)

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-core"     % "1.2.0",
  "org.apache.mrunit" % "mrunit"          % "1.0.0"   % "test" classifier "hadoop1",
  "org.specs2"        %% "specs2"         % "1.14"    % "test",
  // scalding
  "com.twitter"    %% "scalding-commons"  % "0.9.1",
  "com.twitter"    %% "scalding-args"     % "0.9.1",
  "com.twitter"    %% "scalding-core"     % "0.9.1",
  "com.twitter"    %% "scalding-date"     % "0.9.1"
)

//mainClass in (Compile,run) := Some("com.myltsev.raw.Driver")
