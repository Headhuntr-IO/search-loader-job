package io.headhuntr

import org.apache.spark.sql.SparkSession

package object test {

  implicit def toCollectionSupport(spark: SparkSession): FileLoaderSupport = new FileLoaderSupport(spark)
}
