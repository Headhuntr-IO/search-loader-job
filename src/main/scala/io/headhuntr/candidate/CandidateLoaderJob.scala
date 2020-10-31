package io.headhuntr.candidate

import io.headhuntr.util.Constants
import io.headhuntr.util.transform._
import org.apache.spark.sql.DataFrame
import org.elasticsearch.spark.sql._

case class CandidateLoaderJob(
                               candidates: DataFrame,
                               jobHistory: DataFrame,
                               esHost: String,
                               esIndex: String,
                               workingDir: String) {

  def executeJob(): Unit = {
    val candDF = buildCandidates()
      .combine(buildJobHistory(), "candId")
      .rename(Map("candId" -> "id"))
      .toIntegerType("monthsExperience")
      .toStringType("id")

    candDF.printSchema()

    storeToES(candDF)
  }


  private def buildCandidates(): DataFrame = {
    candidates
      .camelCase()
  }

  private def buildJobHistory(): DataFrame = {
    jobHistory
      .camelCase()
      .toIntegerType("monthsExperience")
      .invalidAsNull("jobStart", "yyyy-MM-dd")
      .invalidAsNull("jobEnd", "yyyy-MM-dd")
      .rename(Map(
        "jobSequence" -> "sequence",
        "jobStart" -> "start",
        "jobEnd" -> "end",
        "workLocation" -> "location"))
      .toList("candId", "jobHistory")
  }


  private def storeToES(df: DataFrame): Unit = {
    df.saveToEs(esIndex, Map(
      "es.mapping.id"                 -> "id",
      "es.index.auto.create"          -> Constants.ElasticSearchCreateIndex,
      "es.write.operation"            -> "upsert",
      "es.nodes"                      -> esHost,
      "es.batch.write.retry.wait"     -> "60s",
      "es.nodes.wan.only"             -> "true"
    ))
  }
}
