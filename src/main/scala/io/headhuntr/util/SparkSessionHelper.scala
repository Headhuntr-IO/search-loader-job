package io.headhuntr.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSessionHelper {

  def createSession(sysArgs: Array[String]): SparkSession = {
    val conf = new SparkConf()
    conf.set("spark.cores.max", "1")

    SparkSession.builder.config(conf).getOrCreate()
  }
}
