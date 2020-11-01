package io.headhuntr.generic

import io.headhuntr.JobConfig
import org.apache.spark.sql.SparkSession

case object GenericLoader {

  def delegate(spark: SparkSession, config: JobConfig) {
    val esHost = config("esHost")
    val dmzDir = config("dmzDir")
    val fileLocation = config("file")
    val esIndex = config("index")

    val data = spark.read.parquet(s"$dmzDir/$fileLocation")

    GenericLoaderJob(data,
      esHost,
      esIndex
    ).executeJob()
  }

}
