object Futures extends App {
  import scala.concurrent.{Future, Await}
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.{Future, Await}
  import scala.concurrent.duration._
  import scala.concurrent.ExecutionContext.Implicits.global

  val sumF = Future {
    (1L to 100000L).sum
  }

  sumF onComplete  {
    println(_)
  }

  // или лучше так: val doubledSumF = sumF.map(_ * 2)
  val doubledSumF = sumF.flatMap {
    case s => Future { s * 2 }
  }

  val tripledSumF = sumF.flatMap {
    case s => Future { s * 3 }
  }

  val resultF = for {
    s1 <- doubledSumF
    s2 <- tripledSumF
  } yield s1 + s2

  val result = Await.result(resultF, 5.seconds)
  println(s"result = $result")
  Thread.sleep(5000)
}
