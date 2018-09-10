import scala.collection.mutable.ListBuffer

//#main-class
object AkkaQuickstart extends App {
//  println("Hello, World")
 def receive = {
    
    val bufferedSource = io.Source.fromFile("./src/main/resources/agnc-dividends.csv")
    var dividendList = new ListBuffer[Double]()
    // var dividendList = List(1.0, 2.0)
    for (line <- bufferedSource.getLines) {
        val cols = line.split(",").map(_.trim)
        dividendList += cols(2).toDouble
        // do whatever you want with the columns here
        // println(s"${cols(2)}")
    }
    val foo = 
    println("Total distributions for 1 share:  "+dividendList.foldLeft(0.0) (_ + _))
    bufferedSource.close
    
  }
  //#main-send-messages
  receive
}
//#main-class
//#full-example
