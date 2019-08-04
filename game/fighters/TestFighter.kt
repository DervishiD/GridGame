package game.fighters

import game.fighters.action.AOEActionSet
import game.fighters.action.FighterActionSet
import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellType
import game.world.defaults.EmptyCell
import llayout.utilities.GraphicAction
import java.awt.Graphics

class TestFighter(name : String = "NO_NAME") : AbstractFighter(name) {
    
    private companion object{
        private const val DEFAULT_MAX_HEALTH : Float = 20.0f
        private const val DEFAULT_MOVING_DISTANCE : Float = 2.0f
        private val NEXT_LEVEL_REQUIREMENT_FUNCTION : (Int) -> Int = { i -> i }
        private val MAX_HEALTH_LEVEL_FUNCTION : (Int) -> Float = { _ -> 1f }
        private val MOVING_DISTANCE_LEVEL_FUNCTION : (Int) -> Float = { _ -> 0.1f }
        private val FIGHTER_ACTIONS : FighterActionSet = FighterActionSet()
        private val AOE_ACTIONS : AOEActionSet = AOEActionSet()
    }

    override var stats : FighterStats = FighterStats(0, DEFAULT_MAX_HEALTH, DEFAULT_MAX_HEALTH, DEFAULT_MOVING_DISTANCE,
        NEXT_LEVEL_REQUIREMENT_FUNCTION, MAX_HEALTH_LEVEL_FUNCTION, MOVING_DISTANCE_LEVEL_FUNCTION, FIGHTER_ACTIONS, AOE_ACTIONS)

    private var currentCell : AbstractCell = EmptyCell

    override fun takeDamage(damage: Float) {
        stats.damage(damage)
        deathCheck()
    }

    private fun deathCheck(){
        if(isDead()) die()
    }

    override fun die() {
        println("The test fighter is dead")
    }

    override fun isImmuneToDamage(): Boolean {
        return isDead()
    }

    override fun canBeHealed(): Boolean {
        return true
    }

    override fun heal(health: Float) = stats.heal(health)

    override fun currentCell(): AbstractCell = currentCell

    override fun canStepOn(cell: AbstractCell): Boolean {
        return cell.cellType() != CellType.TREE_TEST
    }

    override fun image(): GraphicAction {
        return { g : Graphics, w : Int, h : Int ->
            g.color = java.awt.Color.BLUE
            g.drawRect(0, 0, w-1, h-1)
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h, h, 0), 3)
        }
    }

    override fun type(): CharSequence = "testFighter"

}