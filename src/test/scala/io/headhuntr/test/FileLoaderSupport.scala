package io.headhuntr.test

import org.apache.spark.sql.{DataFrame, SparkSession}

class FileLoaderSupport(spark: SparkSession) {

  def loadTestFile(file: String): DataFrame = spark.read.parquet(s"src/test/resources/parquet/$file")

}
