package game.player

import game.fighters.AbstractFighter

class FighterList constructor(private val list : MutableList<AbstractFighter>){

    constructor(fighter : AbstractFighter) : this(mutableListOf(fighter))

}