package com.sparkbasics

import org.apache.spark.sql.SparkSession

object WordCountExample {

  def main(args: Array[String]): Unit = {

    println("=========Start===========")
    val spark = SparkSession
      .builder()
      .appName("World Count Example")
      .master("local[*]")
      .getOrCreate()

    val input_file = "/user/mapr/spark/word_count_input.txt"

    val textFile = spark.read.textFile(input_file)

    import spark.implicits._

    val counts = textFile.flatMap(line => line.split("\\s"))
      .map(_.replace("[,:,!;?]", "").trim.toLowerCase)
      .filter(!_.isEmpty)
      .map(word => (word,1)).rdd.reduceByKey(_ + _).sortByKey()

//    counts.collect()

    counts.saveAsTextFile("/user/mapr/spark/word_count_output")

  }


}
