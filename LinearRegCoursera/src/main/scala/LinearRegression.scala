import org.apache.spark.{SparkContext, SparkConf}
import breeze.linalg._

object LinearRegression {
  def main(args: Array[String]): Unit = {
    
    case class DataPoints(x: Double, y: Double)
    
    val conf = new SparkConf().setAppName("LinearRegressionExample").setMaster("local")
    val sc  = new SparkContext(conf)
    
    val resource = this.getClass.getClassLoader.getResource("data.txt")
    val filePath = (resource.toURI).getPath
     
    val raw = sc.textFile(filePath).map{ line =>
       val parts = line.split(',')
       (DenseVector(1.0,parts(0).toDouble), parts(1).toDouble)
    }
       
    // Number of training examples
    val m = raw.count
    
    //Learning Rate
    val alpha = 0.01
    
    //Number of iterations
    val iterations = 1500
    
    var theta = DenseVector[Double](0,0)
    
    for (i <- 1 to iterations)
    {
      val gradient_sum = raw.map{ line =>
          val prediction =   line._1.t * theta // h(x)
          val difference = prediction - line._2 //h(x) - y
          val gradient = line._1 * difference  // (h(x) - y).x
          (gradient.valueAt(0), gradient.valueAt(1))
       }.fold((0,0))((a,b) => (a._1 + b._1, a._2 + b._2))
       
      //Sum((h(x) - y).x) /m - Gradient 
      val gradVector = DenseVector[Double](gradient_sum._1, gradient_sum._2) :/ DenseVector[Double](Array.fill(2)(m.toDouble))
      
      //Updated values of theta_0 and theta_1
      theta = theta - (alpha * gradVector)   
    }
    
    println("Final Theta Values" + theta)
    println("Predicting values for population sizes of 35000")
    val result = DenseVector[Double](1,3.5).t * theta
    println("For poupulation of 35000, value is:" +  result * 100000)
    
    sc.stop
  }
}
  
    

