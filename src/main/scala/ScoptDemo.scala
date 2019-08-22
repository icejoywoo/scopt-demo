
object ScoptDemo {

  private val parser = new scopt.OptionParser[Config]("scopt") {
    head("scopt", "3.x")

    opt[Int]('c', "count")
      .action((x, c) => c.copy(count = x))
      .validate(x =>
        if (x > 0) success
        else failure("Option --count must be >0")
      )
      .text("count is an integer property")

    opt[String]('m', "mode")
      .action((x, c) => c.copy(mode = x))
      .text("mode is an string property")

    opt[Seq[String]]('l', "list")
      .action((x, c) => c.copy(list = x))
      .text("list is an Seq[String] property")

    opt[Map[String, String]]("map")
      .action((x, c) => c.copy(map = x))
      .text("map is an map property")

    opt[Boolean]("switch")
      .action( (x, c) => c.copy(switch = x) )
      .text("switch is an boolean property")

    opt[Unit]("turn-off-switch")
      .abbr("ns")
      .action( (_, c) => c.copy(switch = false) )
      .text("set switch to false")

    opt[Unit]("turn-on-switch")
      .abbr("s")
      .action( (_, c) => c.copy(switch = true) )
      .text("set switch to true")

    opt[Unit]("debug")
      .hidden()
      .action( (_, c) => c.copy(debug = true) )
      .text("this option is hidden in the usage text")

    checkConfig(c =>
      if (c.switch && c.count > 10) failure("count must be greater than 10 when switch is on")
      else success)
  }

  def main(args: Array[String]): Unit = {
    parser.parse(args, Config()) match {
      case Some(config) =>
        println(Utility.prettyToString(config))

      case None =>
        println("error")
    }
  }
}
