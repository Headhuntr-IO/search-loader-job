package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

case class StringSupport(df: DataFrame) {

  /**
   * pretty obvious, I'm just documenting for fun
   *
   * @param column the column to uppercase
   * @return
   */
  def toUppercase(column: String): DataFrame = {
    df.withColumn(column, upper(df(column)))
  }

  /**
   * converts a CSV column to an array
   *
   * @param column the CSV column
   * @return
   */
  def splitToArray(column: String): DataFrame = {
    df.withColumn(column, split(df(column), ","))
  }

  /**
   * filters a list-type column to only allow certain values in the final list
   *
   * @param column the list-type column to clean up
   * @return a new dataframe with the sanitized list
   */
  def restrictValues(column: String, allowedValues: Seq[String]): DataFrame = {
    val restrictValues: Seq[String] => Seq[String] = _.filter(allowedValues.contains(_))
    val restrictUDF = udf(restrictValues)

    df.withColumn(column, restrictUDF(df(column)))
  }
}
