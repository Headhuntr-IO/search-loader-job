package io.headhuntr.util

import org.apache.spark.sql.DataFrame

package object transform {

  implicit def toCollectionSupport(df: DataFrame): CollectionSupport = new CollectionSupport(df)

  implicit def toColumnSupport(df: DataFrame): ColumnNameSupport = new ColumnNameSupport(df)

  implicit def toCombinationSupport(df: DataFrame): CombinationSupport = new CombinationSupport(df)

  implicit def toNestedSupport(df: DataFrame): NestedSupport = NestedSupport(df)

  implicit def toDataTypeSupport(df: DataFrame): DataTypeSupport = DataTypeSupport(df)

  implicit def toDateFormatSupport(df: DataFrame): DateFormatSupport = DateFormatSupport(df)

  implicit def toStringSupport(df: DataFrame): StringSupport = StringSupport(df)

  implicit def toFilterSupport(df: DataFrame): FilterSupport = FilterSupport(df)
}
