package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

class CollectionSupport(df: DataFrame) {

  /**
   * groups the rows using a column and creates a new list to hold the remaining fields
   *
   * @param groupedByColumn
   * @param fieldName
   * @return
   */
  def toList(groupedByColumn: String, fieldName: String): DataFrame = {
    val remainingColumns = df.columns.filter(!_.contains(groupedByColumn)).map(df(_))
    df.groupBy(df(groupedByColumn)).agg(collect_list(struct(remainingColumns:_*)).as(fieldName))
  }

  /**
   * targets a single field and creates a new list aggregated by the groupedByColumn
   * @param groupedByColumn the aggregation field
   * @param fieldName the name of the field containing the list
   * @param singleField the specified field that will be aggregated into the list
   * @return
   */
  def toList(groupedByColumn: String, fieldName: String, singleField: String): DataFrame = {
    df.groupBy(df(groupedByColumn)).agg(collect_list(df(singleField)).as(fieldName))
  }
}
