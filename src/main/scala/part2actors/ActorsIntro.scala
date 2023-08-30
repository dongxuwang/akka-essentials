package part2actors

import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object ActorsIntro {

  // part 1: behavior
  val simpleActorBehavior: Behavior[String] = Behaviors.receiveMessage { (message: String) =>
    // do something with the message
    println(s"[simple actor] I have received: $message")

    // new behavior for the NEXT message
    Behaviors.same
  }

  def demoSimpleActor(): Unit = {
    // part 2: instantiate
    val actorSystem = ActorSystem(SimpleActor_V2(), "FirstActorSystem")

    // part 3: communicate!
    actorSystem ! "I am learning Pekko" // asynchronously send a message
    // actorSystem ! 42
    // ! = the "tell" method

    // part 4: gracefully shutdown
    Thread.sleep(1000)
    actorSystem.terminate()
  }

  // "refactor"
  object SimpleActor {
    def apply(): Behavior[String] = Behaviors.receiveMessage { (message: String) =>
      // do something with the message
      println(s"[simple actor] I have received: $message")

      // new behavior for the NEXT message
      Behaviors.same
    }
  }

  object SimpleActor_V2 {
    def apply(): Behavior[String] = Behaviors.receive { (context, message) =>
      // context is a data structure (ActorContext) with access to a variety of APIs
      // simple example: logging
      context.log.info(s"[simple actor] I have received: $message")

      Behaviors.same
    }
  }

  object SimplaActor_V3 {
    def apply(): Behavior[String] = Behaviors.setup { context =>


      Behaviors.receiveMessage { message =>
        context.log.info(s"[simple actor] I have received: $message")
        Behaviors.same
      }
    }
  }

  def main(args: Array[String]): Unit = {
    demoSimpleActor()
  }

}
