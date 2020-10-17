package io.headhuntr

import org.scalatest.{FlatSpec, Matchers}

class JobConfigSpec extends FlatSpec with Matchers {

  "JobConfig" should "get the parameter when it is present" in {
    val params = Array("--arg1", "val1", "--arg3", "val3", "--arg2", "val2")
    val config = JobConfig(params)

    config("arg3") should equal("val3")
    config("arg1") should equal("val1")
    config("arg2") should equal("val2")
  }

  it should "get the boolean param" in {
    val params = Array("--arg1", "true", "--arg3", "false", "--arg2", "something_else_not_bool")
    val config = JobConfig(params)

    config.is("arg1")
    !config.is("arg2")
    !config.is("arg3")
    !config.is("doest_not_exist")
  }

  it should "allow an optional parameter" in {
    val params = Array("--arg1", "true", "--arg3", "false", "--arg2", "something_else_not_bool")
    val config = JobConfig(params)

    val result = config("doesNotExist", "default")

    result == "default"
  }
}
