package we_need_to_go_deeper

import support.HandsOnSuite


/**
  * Now what if we want to allow our Bag to be empty ?
  */
class e2_algebraic_bag extends HandsOnSuite {

 /**
   * We define Bag as what is called : an Algebraic Data Type (ADT for short).
   * 
   * It consist of a trait (remember, a trait is like an Java interface with default methods)
   * and some implementations, usualy case classes or case objects.
   * 
   * The sealed keyword signify that no class can extend this trait outside this file
   * (this allows the compiler to know all implementations of the trait, and check 
   * that pattern matching are exhaustive)
   * 
   */
  sealed trait Bag {

    def map(function:Int => Int):Bag

    def flatMap(function:Int => Bag):Bag

    def filter(function:Int => Boolean):Bag

    def contentOrElse(replacement:Int):Int

  }

  /**
    * We can create an object that has the same name as a trait (or class), this makes
    * it the "companion object" of the said trait (or class)
    */
  object Bag {
    def apply(contenu:Int):Bag = FullBag(contenu)
  }

  case object EmptyBag extends Bag {

    override def map(function:Int => Int):Bag = EmptyBag

    override def flatMap(function:Int => Bag):Bag = EmptyBag

    override def filter(function:Int => Boolean):Bag = EmptyBag

    override def contentOrElse(replacement:Int):Int = replacement
  }

  case class FullBag(contenu:Int) extends Bag {

    override def map(function:Int => Int):Bag = new FullBag(function(contenu))

    override def flatMap(function:Int => Bag):Bag = function(contenu)

    override def filter(function:Int => Boolean):Bag =
        if (function(contenu)) new FullBag(contenu) else EmptyBag 

    override def contentOrElse(replacement:Int):Int = contenu
  }


  exercise("Bag creation")  {
    val s0 = Bag(0)    // here we call the companion object's apply method, it acts like a "smart constuctor"

  }

  exercise("I can lift a function into a bag") {

    val bagOfZero = Bag(0)


    // Thanks to the sealed trait, the compiler will warn us if our match is not exhaustive
    bagOfZero.map(x => x +1) match {
      case FullBag(content) => content should be(1)

      case EmptyBag => fail("That bag shouldn't be empty")
    }

    val bagVide=EmptyBag

    bagVide.map(x=>x+1) match {
      case FullBag(_) => fail("This bag shouldn't be full")
      case _ => Unit
    }
  }

  exercise("I can combine bags") {
    val bagOfTwo = Bag(2)

    val bagOfAHundred  = Bag(100)

    val combinaison = for (two <- bagOfTwo; aHundred <- bagOfAHundred) yield( two * aHundred )

    combinaison match {
      case FullBag(content) => {
        content should be (200)
      }

      case _ => fail("shouldn't be empty")
    }
    val emtpyCombination = for (two <- EmptyBag; aHundred <- bagOfAHundred) yield( two * aHundred )
    emtpyCombination match {
      case FullBag(_) => fail("Shouldn't be full")
      case _ => Unit
    }

  }

  exercise("I can filter the content of a Bag") {

    val bagOfTwo = Bag(2)

    val bag = bagOfTwo.filter(x => x > 10)

    bag match {
      case FullBag(_) => fail("this should be empty")
      case _  => Unit
    }

    bagOfTwo.filter(x => x > 1) match {
      case EmptyBag => fail("This should be full")
      case _ => Unit
    }

  }

}
