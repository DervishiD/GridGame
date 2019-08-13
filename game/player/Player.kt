package game.player

import game.fighters.AbstractFighter
import game.stats.PlayerStats
import game.world.cells.AbstractCell
import game.world.cells.CellComponent
import game.world.cells.CellType
import llayout.utilities.GraphicAction
import java.awt.Graphics

class Player constructor(private val name : String,
                         private val stats : PlayerStats,
                         private val fighters : FighterList,
                         private val team : FighterList)
    : CellComponent
{

    private companion object{

        private const val TEAM_UPPER_BOUND : Int = 10

        private const val DEFAULT_TEAM_UPPER_BOUND : Int = 4

        private enum class Direction{ UP, DOWN, LEFT, RIGHT }

    }

    private var currentTeamUpperBound : Int = DEFAULT_TEAM_UPPER_BOUND

    private var direction : Direction = Companion.Direction.UP

    constructor(name : String, firstFighter : AbstractFighter) : this(name, PlayerStats.starterPack(), FighterList(firstFighter), FighterList(firstFighter))

    fun team() : FighterList = team

    fun teamContains(fighter : AbstractFighter) : Boolean = fighter in team

    fun teamSize() : Int = team.size()

    fun fullTeamSize() : Int = currentTeamUpperBound

    fun fighters() : FighterList = fighters

    fun inventory() : PlayerInventory = stats.inventory()

    fun points() : Int = stats.points()

    fun removePoints(points : Int) = stats.removePoints(points)

    fun stats() : PlayerStats = stats

    fun name() : String = name

    fun addToTeam(fighter : AbstractFighter){
        if(teamIsFull()) throw IllegalStateException("The team is already full.")
        team.add(fighter)
    }

    fun removeFromTeam(fighter : AbstractFighter){
        team.remove(fighter)
    }

    fun teamCanGrow() : Boolean = fullTeamSize() < TEAM_UPPER_BOUND

    fun addTeamSlot(){
        currentTeamUpperBound++
    }

    fun faceUp(){
        direction = Companion.Direction.UP
    }

    fun faceDown(){
        direction = Companion.Direction.DOWN
    }

    fun faceLeft(){
        direction = Companion.Direction.LEFT
    }

    fun faceRight(){
        direction = Companion.Direction.RIGHT
    }

    fun isFacingUp() : Boolean = direction == Companion.Direction.UP

    fun isFacingDown() : Boolean = direction == Companion.Direction.DOWN

    fun isFacingLeft() : Boolean = direction == Companion.Direction.LEFT

    fun isFacingRight() : Boolean = direction == Companion.Direction.RIGHT

    fun canStepOn(cell : AbstractCell) : Boolean{
        return cell.cellType() != CellType.WATER
    }

    private fun teamIsFull() : Boolean = teamSize() >= fullTeamSize()

    override fun image(): GraphicAction {
        return { g : Graphics, w : Int, h : Int ->
            g.color = java.awt.Color.BLACK
            g.fillOval(w/4, h/4, w/2, h/2)
        }
    }

}