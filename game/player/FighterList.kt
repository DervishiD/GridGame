package game.player

import game.fighters.AbstractFighter

class FighterList constructor(private val list : MutableList<AbstractFighter>){

    constructor(fighter : AbstractFighter) : this(mutableListOf(fighter))

    operator fun contains(fighter : AbstractFighter) : Boolean = fighter in list

    fun size() : Int = list.size

}