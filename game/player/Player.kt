package game.player

import game.fighters.AbstractFighter
import game.stats.PlayerStats

class Player constructor(private val name : String, private val stats : PlayerStats, private val fighters : FighterList) {

    constructor(name : String, firstFighter : AbstractFighter) : this(name, PlayerStats(), FighterList(firstFighter))

}