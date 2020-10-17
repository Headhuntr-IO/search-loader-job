package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame

class ColumnNameSupport(df: DataFrame) {

  def rename(map: Map[String, String]): DataFrame = {
    map.foldLeft(df) ( (current, col) => current.withColumnRenamed(col._1, col._2))
  }

  def camelCase(): DataFrame = {
    def sc2cc(in: String): String = "_([a-z\\d])".r.replaceAllIn(in, _.group(1).toUpperCase)

    val renamed = df.columns.map(s => (s,sc2cc(s))).toMap
    rename(renamed)
  }
}
