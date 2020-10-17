package io.headhuntr.candidate

import io.headhuntr.JobConfig
import org.apache.spark.sql.SparkSession

object CandidateLoader {

  def delegate(spark: SparkSession, config: JobConfig) {
    val esHost = config("esHost")
    val dmzDir = config("dmzDir")
    val workingDir = config("workingDir")

    val buildFirst = true
    val batchSize = 1000000
    val idStart = 0
    val idEnd = Long.MaxValue
    val esIndex = "candidates"

    val candidateProfileList = spark.read.parquet(s"$dmzDir/candidates")
    val jobHistory = spark.read.parquet(s"$dmzDir/candidates_job_history")

    CandidateLoaderJob(
      candidateProfileList,
      jobHistory,
      esHost,
      esIndex,
      batchSize,
      workingDir,
      idStart,
      idEnd,
      buildFirst
    ).executeJob()
  }

}
