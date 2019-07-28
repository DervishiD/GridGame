package game.fighters

import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellComponent

abstract class AbstractFighter : CellComponent{

    protected abstract var stats : FighterStats

    protected fun isDead() : Boolean = stats.isDead()

    protected fun isAlive() : Boolean = stats.isAlive()

    protected fun isNotImmuneToDamage() : Boolean = !isImmuneToDamage()

    protected fun canNotBeHealed() : Boolean = !canBeHealed()

    protected fun maxHealth() : Int = stats.maximalHealth().toInt()

    protected fun currentHealth() : Int = stats.currentHealth().toInt()

    protected fun movingDistance() : Float = stats.movingDistance()

    abstract fun takeDamage(damage : Float)

    protected abstract fun die()

    protected abstract fun isImmuneToDamage() : Boolean

    protected abstract fun canBeHealed() : Boolean

    abstract fun heal(health : Float)

    protected abstract fun currentCell() : AbstractCell

    protected abstract fun canStepOn(cell : AbstractCell) : Boolean

}