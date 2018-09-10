import scala.collection.mutable.ListBuffer
import java.time.LocalDate
import java.time.format._


case class Dividend( exDate: LocalDate, amount: Double)
case class Quote( date: LocalDate, open: Double, high: Double, low: Double, close: Double, volume: Int)

object AkkaQuickstart extends App {
//  println("Hello, World")

val dtf = DateTimeFormatter.ofPattern("M/d/yyyy")
  def loadDividends(filename: String) : List[Dividend] = {
    val bufferedSource = io.Source.fromFile(filename)
    var dividendList = new ListBuffer[Dividend]()
    for (line <- bufferedSource.getLines) {
        val cols = line.split(",").map(_.trim)
        val entry = Dividend(LocalDate.parse(cols(0), dtf), cols(2).toDouble )
        dividendList += entry
    }
    bufferedSource.close
    return dividendList.toList
  }

    def loadQuotes(filename: String) : List[Quote] = {
    val bufferedSource = io.Source.fromFile(filename)
    var dividendList = new ListBuffer[Quote]()
    for (line <- bufferedSource.getLines) {
        val cols = line.split(",").map(_.trim)
        val entry = Quote(LocalDate.parse(cols(0), dtf), cols(1).toDouble, cols(2).toDouble, cols(3).toDouble, cols(3).toDouble, cols(5).toInt )
        dividendList += entry
    }
    bufferedSource.close
    return dividendList.toList
  }
  
  val divList = loadDividends("./src/main/resources/agnc-dividends.csv")
  val priceList = loadQuotes("./src/main/resources/agnc-prices.csv")
  println(divList.reverse.head.exDate + " " + divList.reverse.head.amount)
  println(priceList.reverse.head.date + " " + priceList.reverse.head.close)
}
