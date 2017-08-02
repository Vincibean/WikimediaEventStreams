package org.vincibean.wikimedia.event.streams

import java.io.PrintStream
import java.net.ServerSocket

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.unmarshalling.sse.EventStreamUnmarshalling._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import play.api.libs.json.{JsObject, Json}

object RecentChangesBackend {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()
    import system.dispatcher

    val outStream = new PrintStream(new ServerSocket(8124).accept().getOutputStream)
    val printStreamFun = printStream(outStream) _

    Http()
      .singleRequest(Get("https://stream.wikimedia.org/v2/stream/recentchange"))
      .flatMap(httpResp => Unmarshal(httpResp).to[Source[ServerSentEvent, NotUsed]])
      .map(_.map(event => stringToJson(event.data)))
      .foreach(_.runForeach(printStreamFun))
  }

  def stringToJson(message: String): JsObject = {
    val json = Json.parse(message)
    Json.obj(
      "user" -> json \ "user",
      "pageUrl" -> s"""${(json \ "server_url").as[String]}/wiki/${(json \ "title").as[String].replaceAll(" ", "_")}""",
      "id" -> json \ "id",
      "timestamp" -> (json \ "timestamp").as[Long])
  }

  def printStream(outStream: PrintStream)(json: JsObject): Unit = {
    outStream.println(json.toString)
    outStream.flush()
  }

}