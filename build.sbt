name := "search-loader-job"

version := "latest"

scalaVersion := "2.11.12"

val awsSDKVersion = "1.11.728"
val hadoopVersion = "2.8.3"
val sparkVersion = "2.4.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.elasticsearch" % "elasticsearch-hadoop" % "7.4.2",

  //libraries that are probably provided at runtime
  "com.amazonaws" % "aws-java-sdk-core" % awsSDKVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.amazonaws" % "aws-java-sdk-s3" % awsSDKVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "com.amazonaws" % "aws-java-sdk-sts" % awsSDKVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.apache.hadoop" % "hadoop-aws" % hadoopVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.apache.hadoop" % "hadoop-common" % hadoopVersion % Test exclude("com.fasterxml.jackson.core", "jackson-databind"),

  //important testing libraries
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "com.holdenkarau" %% "spark-testing-base" % "2.4.2_0.12.0" % Test
)

resolvers += "Mulesoft" at "https://repository.mulesoft.org/nexus/content/repositories/public"

parallelExecution in Test := false
