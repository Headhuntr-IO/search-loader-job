package io.headhuntr.util.transform

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType}

case class DataTypeSupport(df: DataFrame) {

  //TODO: this is shit code, rewrite implementation
  def toDouble(col: String, otherCols: String*): DataFrame = {
    var x = df.withColumn(col, df(col).cast(DoubleType))

    if (otherCols != null) {
      x = otherCols.foldLeft(x) ((a, c) => a.withColumn(c, a(c).cast(DoubleType)))
    }

    x
  }

  def toStringType(col: String, otherCols: String*): DataFrame = {
    var x = df.withColumn(col, df(col).cast(StringType))

    if (otherCols != null) {
      x = otherCols.foldLeft(x) ((a, c) => a.withColumn(c, a(c).cast(StringType)))
    }

    x
  }

  def toIntegerType(col: String, otherCols: String*): DataFrame = {
    var x = df.withColumn(col, df(col).cast(IntegerType))

    if (otherCols != null) {
      x = otherCols.foldLeft(x) ((a, c) => a.withColumn(c, a(c).cast(IntegerType)))
    }

    x
  }
}
