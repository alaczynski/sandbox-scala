import org.scalatest.FunSuite

class ScalaLearning extends FunSuite {

  // common
  // ===================================================================================================================
  test("common - syntax") {
    // ++ operator is not supported
    var i = 0
    i += 1

    // parentheses needed in if and while boolean expression
    if (true) 1 else 2 // if true 1 else 2 is invalid

    // -> method could be invoked on any object (implicit conversion)
    val t = (1).->("a")
    assert(t == Tuple2(1, "a"))
    assert(t ==(1, "a"))

    // semicolon
    // - optional unless many statements in one line
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

  /*
  imports
  - by default imported
    - java.lang
    - scala
    - members of object scala.Predef e.g. println, assert
  */

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

    // procedural function - no = sign
    def procedure() {"result will be transformed always to Unit"}
    assert(procedure() ==())
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
    assert(a1 != a2) // equals on arrays check reference equality
  }

  // list
  // - are immutable
  // ===================================================================================================================
  test("list") {
    val one = List(1, 2)
    assert(one(0) == 1)

    // :: operator is invoked on right operand
    // Scala rule - when method name ends in : then it is invoked on right
    val two = 1 :: one

    // Nil is empty list (extends List)
    val tree = 1 :: 2 :: 3 :: Nil

    // concatenates 2 lists
    one ::: tree
  }

  // map
  // ===================================================================================================================
  test("map") {
    // supported by syntaxt, default is immutable, mutable supported in the same way as for Set
    var capital = Map("US" -> "Washington", "France" -> "Paris")
    capital += ("Japan" -> "Tokyo")
    assert(capital("US") == "Washington")

    // create empty map, explicit types are required for empty maps (not like in Java)
    var emptyMap = Map[Int, String]()
    emptyMap += (1 -> "a")
    // 1 -> "a" means (1).->("a"): Tuple2[Int, String] which is passed to method + of map

    val mutable = scala.collection.mutable.Map(1 -> "a")
    // def += (kv: (A, B)): this.type
    mutable += (2 -> "b")
    mutable += 3 -> "c"
    assert(mutable == Map(1 -> "a", 2 -> "b", 3 -> "c"))
  }

  // tuple
  // - immutable, can contain diff types of objects
  // - supports tuple with max 22 fields
  // ===================================================================================================================
  test("tuple") {
    // _1, _2 are fields names
    val pair = (1, "a")
    assert(pair._1 == 1)
    assert(pair._2 == "a")
    assert(pair.isInstanceOf[Tuple2[Int, String]]) // pair.isInstanceOf[Tuple2] does not compile
    assert(pair == Tuple2(1, "a"))
  }

  // set
  // ===================================================================================================================
  test("set") {
    // Set(1, 2) means invoke apply method on object scala.collection.immutable.Set available as val scala.Predef.Set
    val set = Set(1, 2)
    // add element to immutable set, creates new set
    assert((set + 3) != set)

    // mutable set
    val mutableSet = scala.collection.mutable.Set(1, 2)
    // += is a method, not available in immutable set
    mutableSet += 3
    assert(mutableSet == Set(1, 2, 3))
  }

  /*
  class
  - public is default access modifier (not like package in Java)
  */
  // ===================================================================================================================
  test("class - syntax") {
    class SomeClass {}
    val objectOfSomeClass = new SomeClass
    assert(objectOfSomeClass.isInstanceOf[SomeClass])
  }

  /*
  object
  - scala does not have static, rather have singleton object
   */
  // ===================================================================================================================
  test("object") {
    object SomeObject {
      var field = "a"
    }
    assert(SomeObject.field == "a")
    val someObject = SomeObject
    assert(SomeObject == someObject)
  }

  // if class and object share the same object then are called companion, must be defined in the same source file
  class SomeClass {
    private val a = "class"

    def method(): String = {
      SomeClass.a
    }
  }

  object SomeClass {
    private val a = "object"

    def method(): String = {
      val someClass = new SomeClass
      someClass.a
    }
  }

  // object with companion class is called standalone object
  test("companion class and object") {
    assert((new SomeClass).method() == "object")
    assert(SomeClass.method() == "class")

    // singleton object is implemented as instance of a synthetic class - class generated by compiler rather then written by programmer
    // Then name is <object name>$
    assert(!SomeClass.isInstanceOf[SomeClass])
    assert(SomeClass.getClass.getSimpleName == "SomeClass$")
    assert((new SomeClass).isInstanceOf[SomeClass])
  }

  // application trait
  class SomeApp extends App {
    for (arg <- args) println(arg)
  }
}
