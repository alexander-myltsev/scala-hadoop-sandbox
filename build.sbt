name := "scala-hadoop-sandbox"

organization := "com.myltsev"

version := "0.1"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "MVN Repo" at "http://mvnrepository.com/artifact"
)

libraryDependencies ++= Seq(
  "org.scala-lang"    % "scala-compiler"  % "2.10.2",
  "org.apache.hadoop" % "hadoop-core"     % "1.1.2",
  "org.apache.mrunit" % "mrunit"          % "1.0.0"    % "test" classifier "hadoop1",
  "org.specs2"        %% "specs2"         % "1.14"    % "test"
)