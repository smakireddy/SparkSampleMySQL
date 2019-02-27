package com.sparkbasics

import org.apache.spark.sql.SparkSession

object CreateSparkApp {
  def main(args: Array[String]): Unit = {

    println("---------Start-------------")
    val spark = SparkSession
      .builder()
      .appName("My Sample Spark Project")
            .master("local[*]")
//      .master("yarn")
      .getOrCreate()

    //    val myRange = spark.range(1000).toDF("numbers")
    //
    //
    //    println("Num of elements in RDD: "+ myRange.count())
    //
    //    myRange.collect().foreach(println)


    val mysqlDF = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://10.20.30.98:3306/retail_db")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("dbtable", "customers")
      .option("user", "som")
      .option("password", "mapr")
      .load()

    mysqlDF.coalesce(1).write.mode("overwrite").parquet("/user/som/spark/SamplSparkApp/output")

    println("---------End-------------")

  }
}
