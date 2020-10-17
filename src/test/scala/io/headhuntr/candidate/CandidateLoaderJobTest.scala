package io.headhuntr.candidate

import com.holdenkarau.spark.testing.SharedSparkContext
import io.headhuntr.util.SparkSessionHelper.createSession
import org.scalatest.FlatSpec
import io.headhuntr.test._
import io.headhuntr.util.Constants

class CandidateLoaderJobTest extends FlatSpec with SharedSparkContext {

  "CandidateLoaderJob" should "combine all the complex data into a single index" in {
    val spark = createSession(null)

    val candidateProfileList = spark.loadTestFile("candidates")
    val jobHistory = spark.loadTestFile("candidates_job_history")

    CandidateLoaderJob(
      candidateProfileList,
      jobHistory,
      Constants.ElasticSearchHost,
      "hh2_candidates",
      300,
      "src/test/resources/parquet/temp/workingDir",
      start = 1,
      end = 999999999,
      buildFirst = true
    ).executeJob()
  }

}
