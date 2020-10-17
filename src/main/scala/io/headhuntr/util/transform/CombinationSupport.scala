package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame

class CombinationSupport(df: DataFrame) {

  def combine(otherDF: DataFrame, column: String): DataFrame = {
    df.join(otherDF, Seq(column), "left")
  }
}
