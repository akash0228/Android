package com.example.enumvssealed

interface ColorCode{
    fun getColorCode(): String
}

enum class Color(val colorShade: String):ColorCode{
    //These Variables are assigned an index value or ordinal and name too or we can also give property like here colorShade is given
   //we can also implement interface in enum class
    RED("light red"){ //0 name="RED"  constant object of Color class
        override fun getColorCode(): String {
            return "#456"
        }

    },
    GREEN("light green") { //1 name="GREEn"  constant object of Color class
        override fun getColorCode(): String {
            return "#123"
        }
    },
//    BLUE(val myBlueColorShade:String) not allowed
}

fun main(){
    val tshirtColor:Color=Color.RED

    println(Color.RED.ordinal)
    println(Color.RED.colorShade)
    println(Color.RED.name)
    println(Color.RED) // this too gives name because internally it executes toString


    //enum object has two methods: values() and valueOf()
    val myConstants=Color.values() //gives array of all objects
    myConstants.forEach { println(it) }

    when(tshirtColor){
        Color.RED -> println("Tshirt Color is Red")
        Color.GREEN -> println("Tshirt Color is Green")
    }

    println(Color.RED.getColorCode())

}




























sealed class Shape{
    class Circle(var radius:Float):Shape()
    class Square(var side:Int):Shape()
    class Rectangle(var length:Int, var breadth:Int):Shape()

    object noShape:Shape()  //singleton object
}

//OR    Both Allowed but for that Shape. prefic will not be needed to access outside declared class

//sealed class Shape{

//}
//
//class Circle(var radius:Float):Shape()
//class Square(var side:Int):Shape()
//class Rectangle(var length:Int, var breadth:Int):Shape()

//Not allowed to create sub class outside this file
//we can also have data class inside
//can also have interface or sealed class inside


//fun main(){
//    var circle=Shape.Circle(3.5f)
//    var square=Shape.Square(8)
//    var rectangle=Shape.Rectangle(20,10)
//    val noShape=Shape.noShape
//
//    checkShape(circle)
//    checkShape(square)
//    checkShape(rectangle)
//    checkShape(noShape)
//}

fun checkShape(shape:Shape){
    when(shape){
        is Shape.Circle -> println("Cicle area is ${3.14*shape.radius*shape.radius}")
        is Shape.Square -> println("Square area is ${shape.side*shape.side}")
        is Shape.Rectangle -> println("Cicle area is ${shape.length*shape.breadth}")
        Shape.noShape -> println("No Shape Found")
//        else -> println("It is not a circle.")
    }
}