package next_steps

import support.HandsOnSuite

/**
*   Set, like well... Set.
*
*   some useful links :
*     - http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.Set
*     - http://docs.scala-lang.org/overviews/collections/sets.html
*/

class e8_sets extends HandsOnSuite {

  exercise("Creating Set") {
    val mySet = Set("South", "East", "West", "North")
    mySet.size should be(4)

    val myOtherSet = Set("South", "East", "South", "North")
    myOtherSet.size should be(3)
  }

  exercise("Operations on Sets") {
    // addition
    val mySet = Set("South", "East", "South")
    val aNewSet = mySet + "North"

    aNewSet.contains("North") should be(true)

    // removal
    val mySetBis = Set("South", "East", "Oueast", "North")
    val aNewSetBis = mySetBis - "North"
    
    // testing if an element is present
    aNewSetBis.contains("North") should be(false)

    // multiple removals
    val myOtherSet = Set("South", "East", "West", "North")
    val aNewOtherSet = myOtherSet -- List("West", "North")

    aNewOtherSet.contains("North") should be(false)
    aNewOtherSet.contains("West") should be(false)
  }
}
