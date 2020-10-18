package io.headhuntr.generic

import io.headhuntr.util.Constants
import io.headhuntr.util.transform._
import org.apache.spark.sql.DataFrame
import org.elasticsearch.spark.sql._

case class GenericLoaderJob(data: DataFrame, esHost: String, esIndex: String) {

  def executeJob(): Unit = {
    val dataForES = data
      .toStringType("id")
      .camelCase()

    dataForES.printSchema()

    storeToES(dataForES)
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
