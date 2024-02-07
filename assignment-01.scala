import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.types._

object Assignment01 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("Assignment01").getOrCreate()


    val csvFilePath = "Divvy_Trips_2015-Q1.csv"


    val df1 = spark.read.option("header", "true").csv(csvFilePath)
    println("DataFrame 1:")
    df1.printSchema()
    println("Record Count: " + df1.count())


    val schema = new StructType()
      .add(StructField("trip_id", IntegerType, true))
      .add(StructField("bike_id", IntegerType, true))
      .add(StructField("tripduration", StringType, true))
      .add(StructField("gender", StringType, true))
      .add(StructField("from_station", IntegerType, true))
      .add(StructField("from_station_name", IntegerType, true))
      .add(StructField("to_station", StringType, true))
      .add(StructField("to_station_name", StringType, true))
      .add(StructField("usertype", StringType, true))
      .add(StructField("birthyear", IntegerType, true))


    val df2 = spark.read.schema(schema).option("header", "true").csv(csvFilePath)
    println("\nDataFrame 2:")
    df2.printSchema()
    println("Record Count: " + df2.count())


    val ddlSchema = "trip_id INT, bike_id INT, tripduration STRING, gender STRING, from_station STRING, gender STRING,  from_station STRING, from_station_name STRING, to_station STRING, to_station_name STRING,>

    val df3 = spark.read.option("header", "true").option("inferSchema", "false").schema(ddlSchema).csv(csvFilePath)
    println("\nDataFrame 3:")
    df3.printSchema()
    println("Record Count: " + df3.count())


    val dfFiltered = df3.select("gender").filter((df3("start_station_name").between("A", "K") && df3("gender") === "female") ||
                                                (df3("start_station_name").between("L", "Z") && df3("gender") === "male"))
    val groupedDF = dfFiltered.groupBy("start_station_name").count()
    groupedDF.show(10)


    spark.stop()
  }
}
