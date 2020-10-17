package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame

/**
 * includes/excludes a subset of the data using the
 *
 * @param df the superset
 */
case class FilterSupport(df: DataFrame) {

  def include(subset: DataFrame, col: String): DataFrame = {
    df.join(subset, col)
  }

  def exclude(subset: DataFrame, col: String): DataFrame = {
    df.join(subset, Seq(col), "left_anti")
  }
}
