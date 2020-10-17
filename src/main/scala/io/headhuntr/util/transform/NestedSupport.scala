package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

case class NestedSupport(df: DataFrame) {

  def nest(fieldName: String, columns: String*): DataFrame = {
    df.withColumn(fieldName, struct(columns.map(df(_)): _*))
      .drop(columns.filterNot(_.equals(fieldName)): _*)
  }

  def nestAndRename(fieldName: String, columnMappings: Map[String, String]): DataFrame = {
    df.withColumn(fieldName, struct(columnMappings.keys.map(s => df(s).as(columnMappings(s))).toSeq: _*))
      .drop(columnMappings.keys.filterNot(_.equals(fieldName)).toSeq: _*)
  }

}
