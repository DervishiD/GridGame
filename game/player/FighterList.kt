package game.player

import game.fighters.AbstractFighter

class FighterList constructor(private val list : MutableList<AbstractFighter>) : Iterable<AbstractFighter>{

    constructor(fighter : AbstractFighter) : this(mutableListOf(fighter))

    operator fun contains(fighter : AbstractFighter) : Boolean = fighter in list

    fun size() : Int = list.size

    fun add(fighter : AbstractFighter) : FighterList{
        list.add(fighter)
        return this
    }

    fun remove(fighter : AbstractFighter) : FighterList{
        list.remove(fighter)
        return this
    }

    override fun iterator(): Iterator<AbstractFighter> = list.iterator()

}