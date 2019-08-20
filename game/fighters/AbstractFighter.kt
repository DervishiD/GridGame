package game.fighters

import game.fighters.action.AOEAction
import game.fighters.action.FighterAction
import game.fighters.action.InteractionEffectType
import game.skilltree.GraphicalSkillTree
import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellComponent
import game.world.defaults.EmptyCell

abstract class AbstractFighter(private var name : String) : CellComponent{

    companion object{

        fun componentID() : String = "FIGHTER"

    }

    private var cell : AbstractCell = EmptyCell

    protected abstract var stats : FighterStats

    protected abstract var graphicalSkillTree : GraphicalSkillTree

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

    fun levelUp() = stats.levelUp()

    fun fighterActions() : MutableList<FighterAction> = stats.fighterActions()

    fun aoeActions() : MutableList<AOEAction> = stats.aoeActions()

    fun nextLevelRequirements() : Int = stats.nextLevelRequirement()

    fun stats() : FighterStats = stats

    fun skillTree() : GraphicalSkillTree = graphicalSkillTree

    fun addActiveMultiplicativeModifier(effectType : InteractionEffectType, modifier : Float) = stats.addActiveMultiplicativeModifier(effectType, modifier)

    fun addActiveAdditiveModifier(effectType : InteractionEffectType, modifier : Float) = stats.addActiveAdditiveModifier(effectType, modifier)

    fun addPassiveMultiplicativeModifier(effectType : InteractionEffectType, modifier : Float) = stats.addPassiveMultiplicativeModifier(effectType, modifier)

    fun addPassiveAdditiveModifier(effectType : InteractionEffectType, modifier : Float) = stats.addPassiveAdditiveModifier(effectType, modifier)

    fun activeModify(effectType : InteractionEffectType, value : Float) : Float = stats.activeModify(effectType, value)

    fun passiveModify(effectType : InteractionEffectType, value : Float) : Float = stats.passiveModify(effectType, value)

    fun currentCell() : AbstractCell = cell

    fun setCell(cell : AbstractCell){
        this.cell = cell
        cell.actOnFighterStep(this)
    }

    abstract fun takeDamage(damage : Float)

    abstract fun isImmuneToDamage() : Boolean

    abstract fun canBeHealed() : Boolean

    abstract fun heal(health : Float)

    abstract fun type() : CharSequence

    abstract fun canStepOn(cell : AbstractCell) : Boolean

    override fun componentID(): String = AbstractFighter.componentID()

    override fun reactToPlayerInteraction() {}

}