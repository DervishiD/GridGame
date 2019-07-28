package game.fighters

import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellType
import game.world.defaults.EmptyCell
import llayout6.utilities.GraphicAction
import llayout6.utilities.LObservable
import java.awt.Graphics

class TestFighter : AbstractFighter() {
    
    private companion object{
        private const val DEFAULT_MAX_HEALTH : Float = 20.0f
        private const val DEFAULT_MOVING_DISTANCE : Float = 2.0f
        private val NEXT_LEVEL_REQUIREMENT_FUNCTION : (Int) -> Int = { i -> i }
        private val MAX_HEALTH_LEVEL_FUNCTION : (Int) -> Float = { _ -> 1f }
        private val MOVING_DISTANCE_LEVEL_FUNCTION : (Int) -> Float = { _ -> 0.1f }
    }

    override var stats : FighterStats = FighterStats(0, DEFAULT_MAX_HEALTH, DEFAULT_MAX_HEALTH, DEFAULT_MOVING_DISTANCE,
        NEXT_LEVEL_REQUIREMENT_FUNCTION, MAX_HEALTH_LEVEL_FUNCTION, MOVING_DISTANCE_LEVEL_FUNCTION)

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
        return { _ : Graphics, _ : Int, _ : Int ->  }
    }

}