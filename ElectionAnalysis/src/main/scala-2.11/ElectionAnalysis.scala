/**
  * Created by pratyush04 on 11-03-2017.
  */
import org.apache.spark.sql.SparkSession

object ElectionAnalysis {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession
      .builder()
      .appName("Election Analysis")
      .getOrCreate()

    import spark.implicits._
    val data = spark.read.option("header", "true").option("delimiter", "\t").option("inferSchema", "true").csv("/user/pratyush04/elections/ls2014.tsv")
      .filter($"state" === "Uttar Pradesh").select("constituency", "partyname", "total")

    val groupedData = data.groupBy("constituency").max("total").withColumnRenamed("max(total)", "maximum")
      .withColumnRenamed("constituency", "const")

    val winner = data.join(groupedData, (data("constituency") === groupedData("const")) && (data("total") === groupedData("maximum")))
      .select(data("constituency"), data("partyname"))

    winner.groupBy("partyname").count().show()
  }
}