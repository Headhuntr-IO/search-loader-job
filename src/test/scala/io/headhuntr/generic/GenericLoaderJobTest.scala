package io.headhuntr.generic

import com.holdenkarau.spark.testing.SharedSparkContext
import io.headhuntr.candidate.CandidateLoaderJob
import io.headhuntr.test.toCollectionSupport
import io.headhuntr.util.Constants
import io.headhuntr.util.SparkSessionHelper.createSession
import org.scalatest.FlatSpec

class GenericLoaderJobTest extends FlatSpec with SharedSparkContext {

  "GenericLoaderJob" should "load t" in {
    val spark = createSession(null)

    val company = spark.loadTestFile("company")

    GenericLoaderJob(company, Constants.ElasticSearchHost, "hh2_companies").executeJob()
  }

}
