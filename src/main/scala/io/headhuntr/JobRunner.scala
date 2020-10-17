package io.headhuntr

import io.headhuntr.candidate.CandidateLoader
import io.headhuntr.util.SparkSessionHelper.createSession

object JobRunner {

  def main(sysArgs: Array[String]): Unit = {
    val spark = createSession(sysArgs)
    val config = JobConfig(sysArgs)

    val jobType = config("job")

    jobType match {
      case "candidate" => CandidateLoader.delegate(spark, config)
    }
  }
}
