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

  // This is screaming for a generic function param of type "(List[String]) => A" where A is the generic type
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

  val ticker = if (args.length > 0)  args(0) else "agnc"

  
  
  val divList = loadDividends("./src/main/resources/"+ticker+"-dividends.csv")
  val priceList = loadQuotes("./src/main/resources/"+ticker+"-prices.csv")
  println(divList.reverse.head.exDate + " " + divList.reverse.head.amount)
  println(priceList.reverse.head.date + " " + priceList.reverse.head.close)
  val startingInvestment = priceList.reverse.head.close
  val (_, withQuotes) = divList.partition(_.exDate.compareTo( priceList.reverse.head.date) < 0 )
  val totalDividends = withQuotes.foldLeft(0.0) ((left, right) => left + right.amount)
  println("starting investment: "+ priceList.reverse.head.close +" dividends: "+ totalDividends+" endValue: "+(totalDividends+priceList.head.close))
  println("return: "+(priceList.reverse.head.close)/(totalDividends+priceList.head.close-priceList.reverse.head.close)*100+"%")
}
