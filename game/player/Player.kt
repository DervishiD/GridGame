package game.player

import game.fighters.AbstractFighter
import game.stats.PlayerStats

class Player constructor(private val name : String, private val stats : PlayerStats, private val fighters : FighterList, private val team : FighterList) {

    companion object{
        private const val TEAM_UPPER_BOUND : Int = 10
        private const val DEFAULT_TEAM_UPPER_BOUND : Int = 4
    }

    private var currentTeamUpperBound : Int = DEFAULT_TEAM_UPPER_BOUND

    constructor(name : String, firstFighter : AbstractFighter) : this(name, PlayerStats.starterPack(), FighterList(firstFighter), FighterList(firstFighter))

    fun team() : FighterList = team

    fun teamContains(fighter : AbstractFighter) : Boolean = fighter in team

    fun teamSize() : Int = team.size()

    fun fullTeamSize() : Int = currentTeamUpperBound

}