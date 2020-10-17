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
                               batchSize: Int,
                               workingDir: String,
                               start: Long = 0,
                               end: Long = Long.MaxValue,
                               buildFirst: Boolean = false) {

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