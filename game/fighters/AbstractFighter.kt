package game.fighters

import game.fighters.action.AOEAction
import game.fighters.action.FighterAction
import game.interactions.InteractionEffectType
import game.skilltree.GraphicalSkillTree
import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellComponent

abstract class AbstractFighter(private var name : String) : CellComponent{

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

    abstract fun takeDamage(damage : Float)

    abstract fun isImmuneToDamage() : Boolean

    abstract fun canBeHealed() : Boolean

    abstract fun heal(health : Float)

    abstract fun type() : CharSequence

    abstract fun currentCell() : AbstractCell

    abstract fun canStepOn(cell : AbstractCell) : Boolean

}