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
  val system = ActorSystem("HelloSystem0")
  val selection = system.actorSelection("akka.tcp://HelloSystem@127.0.0.1:4555/helloactor")

  val requesterActor = system.actorOf(Props[requester], name = "requester")
  selection.tell("hello", requesterActor)
  val msg = new message__(message = Array[Byte](0x22.toByte), desc="input")
  selection.tell(msg, requesterActor)
}
