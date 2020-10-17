package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

case class DateFormatSupport(df: DataFrame) {

  /**
   * I know this feels weird,
   * parsing the date string into a timestamp,
   * then back to string.
   *
   * This is currently the only way I know to ensure that we are sending
   * the right format to ES
   *
   * @param column the column name of the data field
   * @param format the data format that we want to enforce
   * @return
   */
  def invalidAsNull(column: String, format: String): DataFrame = {
    df.withColumn(column, date_format(to_date(df(column), format), format))
  }
}
