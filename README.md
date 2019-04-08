# How to use

Put `spark-custom-functions_2.11-0.1.jar` and `sample-data.csv` (unzip it first) from `sample` folder to `/tmp` folder on hdfs:
```$xslt
hadoop fs -put sample/sample-data.csv /tmp/
hadoop fs -put sample/spark-custom-functions_2.11-0.1.jar /tmp/
```

Start a pyspark shell:
```$xslt
pyspark --jars /tmp/spark-custom-functions_2.11-0.1.jar
```

In the pyspark shell run the following:
```$xslt
from pyspark.sql.types import DoubleType
from pyspark.sql.functions import col,expr

spark.udf.registerJavaFunction("haversine", "com.cloudera.sre.spark.customfunctions.Haversine", DoubleType())

d = spark.read.format("csv").option("header","true").load("/tmp/sample-data.csv").select(col("Date/Time"),col("Lat").cast("double"), col("Lon").cast("double"),col("Base"))
data = d.select(col("Base"),col("Lat").alias("Lat1"),col("Lon").alias("Lon1")).crossJoin(d.limit(1000).select(col("Lat").alias("Lat2"),col("Lon").alias("Lon2")))
data.count()
```

With sql syntax:
```$xslt
data.createTempView("data")
spark.sql("select *, haversine(Lat1,Lon1,Lat2,Lon2) from data limit 10").show()
spark.sql("select max(haversine(Lat1,Lon1,Lat2,Lon2)) from data").show()
```

With df syntax:
```$xslt
data1 = data.withColumn("dist",expr('haversine(Lat1,Lon1,Lat2,Lon2)'))
data1.show(10)
data1.groupBy().max("dist").show()

```