package io.headhuntr

case class JobConfig(sparkSubmitOptions: Array[String]) {

  def apply(key: String): String = {
    val indexKey = sparkSubmitOptions.indexWhere(p => p == "--"+key)

    if (indexKey >= 0 && sparkSubmitOptions.length >= 2) {
      return sparkSubmitOptions(indexKey+1)
    }
    null
  }

  def apply(key: String, defaultValue: String): String = {
    val value = apply(key)

    if (value != null) value
    else defaultValue
  }

  def is(key: String): Boolean = apply(key, "false").toLowerCase() == "true"
}
