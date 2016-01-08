package next_steps

import support.HandsOnSuite


/**
  *
  * Maps
  *
  * Like in most languages, Maps in Scala associate keys of a given type K to values
  * of a given type V and are denoted Map[K, V]
  *
  * Maps are isomophic to a list of pairs, but with enhanced operations, like searching
  * for a key, handling duplicate keys, and so on.
  *
  *   see : http://www.scala-lang.org/api/current/index.html#scala.collection.concurrent.Map
  */

class e7_maps extends HandsOnSuite {

  exercise("Creating maps is easy") {
    val myMap = Map("PA" -> "Paris", "BE" -> "Besançon", "BL" -> "Belfort")
    myMap.size should be(3)

    myMap.head should be(("PA", "Paris"))

    // Maps retain the order of insertion, but it is irrelevant when it comes to map equality
    val myMapBis = Map("BE" -> "Besançon", "BL" -> "Belfort", "PA" -> "Paris")
    myMap.equals(myMapBis) should be(true)

    // Of Maps do not retain duplicate keys
    val myOtherMap = Map("PA" -> "Paris", "BE" -> "Besançon", "PA" -> "Palo Alto")
    myOtherMap.size should be(2)
    myOtherMap("PA") should be("Palo Alto")
  }

  exercise("We can add entries to a Map using +") {
    val myMap = Map("PA" -> "Paris", "BE" -> "Besançon", "NA" -> "Nantes")
    val aNewMap = myMap + ("BL" -> "Belfort")

    myMap.contains("BL") should be (false) // As always, immutablity is the default
    aNewMap.contains("BL") should be(true)
  }


  exercise("On peut accéder/supprimer les élément d’une map") {
    val myMap = Map("PA" -> "Paris", "BE" -> "Besançon", "NA" -> "Nantes", "BL" -> "Belfort")

    // We remove an element by its key, using -
    val aNewMap = myMap - "NA"
    aNewMap.contains("NA") should be(false)

    // We can remove a list of keys with --
    val aNewOtherMap = myMap -- List("BE", "BL")
    aNewOtherMap.contains("BE") should be(false)
    aNewOtherMap.contains("BL") should be(false)

    // An exception is thrown if we try to access a key that does not exists
    intercept[NoSuchElementException] {
      aNewOtherMap("BL") should be("") // this uses Map's apply method
    }

    // Using get is safer, since it returns an Option
    aNewOtherMap.get("BL") should be (None)

  }

}
