import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
case class reply(replyBytes: Array[Byte], desc: String){
  def getdesc: String={
    desc
  }
}
case class message__(message: Array[Byte], desc: String)

class replyer extends Actor {
  def receive = {
    case "hello" => println("hello back at you")
    case  message: message__      =>val reply= new reply(replyBytes = Array[Byte](0x55.toByte, 0x55.toByte, 0xaa.toByte, 0xaa.toByte), desc = "replyed")
      sender ! reply
    case _       => println("huh?")
  }
}

class requester extends Actor{
  def receive ={
    case reply: reply => println(reply.getdesc)
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  val responceActor = system.actorOf(Props[replyer], name = "helloactor")
  val requesterActor = system.actorOf(Props[requester], name = "requester")
  requesterActor.tell("hello", responceActor)
  val msg = new message__(message = Array[Byte](0x22.toByte), desc="input")
  responceActor.tell(msg, requesterActor)
}
