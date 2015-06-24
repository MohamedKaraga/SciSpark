import breeze.linalg.DenseMatrix
import org.dia._
import org.jblas.DoubleMatrix
import org.nd4j.linalg.factory.Nd4j

/**
 * import DSL for scala api
 */
import org.nd4j.api.linalg.DSL._

/**
 * Created by rahulsp on 6/19/15.
 */
class Main$Test extends org.scalatest.FunSuite {
  test("Multiple Slicing") {

    (1 to 100).foreach {i =>
      val m = DenseMatrix.zeros[Double](i * 1000, i * 1000)//, (1000000 to 2000000).toArray.map(p => p.toDouble), 0)
      val m2 = DenseMatrix.ones[Double](i * 1000, i * 1000)//, (1 to 1000000).toArray.map(p => p.toDouble), 0)
      val start = System.nanoTime()
      //val m3 = m * m2
      val m3 = m :* m2
      val stop = System.nanoTime()
      println(stop - start)
    }

//    val slice1 = m(1 to 3, 1 to 3)
//    assert(slice1(::, 1) === DenseVector(14, 15, 16))
//    assert(slice1(::, 1 to 2) === DenseMatrix((14, 20), (15, 21), (16, 22)))
  }


  test("BlockAvrgArrayTest") {
    val squareSize = 100
    val reductionSize = 50
    val accuracy = 1E-4
    val reducedWidth = squareSize / reductionSize
    val testMatrix = DoubleMatrix.ones(squareSize, squareSize)
    val resultMatrix = Main.jblasreduceResolution(testMatrix, reductionSize)

    for (i <- 0 to (reducedWidth - 1)) {
      for (j <- 0 to (reducedWidth - 1)) {
        val error = Math.abs(resultMatrix.get(i, j) - 1)
        if (error >= accuracy) {
          assert(error >= accuracy, "The error is not even close for indices " + i + " " + j + "with value : " + resultMatrix.get(i, j))
        }
      }
    }
  }

  /**
   * Sets the values in the first row to be NaN's
   * The average in the first element of the reduced Matrix should be
   * 49/50. If not, then NaN's were not properly accounted for.
   *
   * TODO :: This test needs to fail - not sure why it isn't failing
   */
    test("BlockAvrgArrayNanTest") {
      val squareSize = 100
      val reductionSize = 50
      val accuracy = 1E-15
      val reducedWidth = squareSize / reductionSize
      var testMatrix = DoubleMatrix.ones(squareSize, squareSize)
      for(i <- 0 to squareSize) {
        testMatrix = testMatrix.put(i, 0, Double.NaN)
        testMatrix = testMatrix.put(i, 1, Double.NaN)
        testMatrix = testMatrix.put(i, 2, Double.NaN)
      }

      val resultMatrix = Main.jblasreduceResolution(testMatrix, reductionSize)

      for(i <- 0 to (reducedWidth - 1)) {
        for (j <- 0 to (reducedWidth - 1)) {
          val error = Math.abs(resultMatrix.get(i, j) - 1)
          if (error >= accuracy) {
            assert(error >= accuracy, "The error is not even close for indices " + i + " " + j + "with value : " + resultMatrix.get(i, j))
          }
        }
      }
    assert(true)
  }

  test("ND4JOps2dTest") {
    (1 to 100).foreach{p =>
      var m1 = Nd4j.create(p*1000 * p *1000).reshape(p * 1000,p * 1000)
      var m2 = Nd4j.create(p*1000 * p *1000).reshape(p * 1000,p * 1000)
      /**
       * Vector subtraction
       */
      var start = System.nanoTime().toDouble / 1E9.toDouble
      val m3 = m1 - m2
      var stop = System.nanoTime().toDouble / 1E9.toDouble
      println(stop - start)
    }
    assert(true)
  }
//  test("breezeReduceResolutionAvrgTest") {
//    val squareSize = 100
//    val reductionSize = 50
//    val accuracy = 1E-15
//    val reducedWidth = squareSize / reductionSize
//    val testMatrix = DenseMatrix.ones[Double](squareSize, squareSize)
//
//    val resultMatrix = Main.breezereduceResolution(testMatrix, reductionSize)
//
//    for(i <- 0 to (reducedWidth - 1)){
//      for(j <- 0 to (reducedWidth - 1)) {
//        val error = Math.abs(resultMatrix(i, j) - 1)
//        if(error >= accuracy) {
//          assert(error >= accuracy, "The error is not even close for indices " + i + " " + j + "with value : " + resultMatrix(i, j))
//        }
//      }
//    }
//    assert(true)
//  }
}
