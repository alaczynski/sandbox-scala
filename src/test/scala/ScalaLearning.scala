import org.scalatest.FunSuite

class ScalaLearning extends FunSuite {

  // common
  // ===================================================================================================================
  test("common - syntax") {
    // ++ operator is not supported
    var i = 0
    i += 1

    // parentheses needed in if and while boolean
    if (true) 1 else 2 // if true 1 else 2 is invalid
  }

  test("common - operators") {
    // technically scala does not have method overloading, instead operators are methods
    assert(1 + 2 == (1).+(2))
  }

  test("common - code transformation") {
    // array(1) is transformed to array.apply(1)
    var array = Array(1, 2)
    array.apply(1)
    assert(array(1) == array.apply(1))

    class SomeClass {
      var s: String = _

      def apply(i: Int): Boolean = true

      def update(i: Int, s: String) = {
        this.s = s
      }
    }

    // apply method invocation is supported for variable of any type
    val someObject = new SomeClass
    assert(someObject(1))
    // update method invocation
    someObject(1) = "test"
    assert(someObject.s == "test")
  }

  // function
  // ===================================================================================================================
  test("function - syntax") {
    // parameter type is required, compiler does not infer them
    def max(a: Int, b: Int): Int = {
      if (a > b) a
      else b
    }

    // result type could be skipped but not for recursive functions
    // when body is one statement then curly braces could be skipped
    def max2(a: Int, b: Int) = if (a > b) a else b

    // void from Java is represented as Unit
    // functions returning Unit are designed for side effect
    def noResult() = {}
    assert(noResult().isInstanceOf[Unit])
  }

  test("function - partially applied function") {
  }

  // array
  // - are mutable, use list instead
  // ===================================================================================================================
  test("array - syntax") {
    // size is mandatory, accessing using () not [] like in Java
    val strings = new Array[String](2)
    strings(0) = "a"
    strings(1) = "b"
    assert(strings(0) == "a")

    // factory method apply
    val a1 = Array("a", "b")
    val a2 = Array.apply("a", "b")
    assert(a1 sameElements a2)
  }

  // list
  // - are immutable
  // ===================================================================================================================
  test("list") {
    val one = List(1, 2)
    assert(one(1) == 1)

    // :: operator is invoked on right operand
    // Scala rule - when method name ends in : then it is invoked on right
    val two = 1 :: one

  }

  // map
  // ===================================================================================================================
  test("map") {
    // supported by syntaxt
    var capital = Map("US" -> "Washington", "France" -> "Paris")
    capital += ("Japan" -> "Tokyo")
    assert(capital("US") == "Washington")
  }

}
