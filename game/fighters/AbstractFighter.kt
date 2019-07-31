package game.fighters

import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellComponent

abstract class AbstractFighter(private var name : String) : CellComponent{

    protected abstract var stats : FighterStats

    fun isDead() : Boolean = stats.isDead()

    fun isAlive() : Boolean = stats.isAlive()

    fun isNotImmuneToDamage() : Boolean = !isImmuneToDamage()

    fun canNotBeHealed() : Boolean = !canBeHealed()

    fun maxHealth() : Int = stats.maximalHealth().toInt()

    fun currentHealth() : Int = stats.currentHealth().toInt()

    fun movingDistance() : Float = stats.movingDistance()

    fun name() : String = name

    fun setName(name : String){
        this.name = name
    }

    fun level() : Int = stats.level()

    abstract fun takeDamage(damage : Float)

    protected abstract fun die()

    abstract fun isImmuneToDamage() : Boolean

    abstract fun canBeHealed() : Boolean

    abstract fun heal(health : Float)

    abstract fun type() : CharSequence

    abstract fun currentCell() : AbstractCell

    abstract fun canStepOn(cell : AbstractCell) : Boolean

}