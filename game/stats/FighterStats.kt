package game.stats

import game.fighters.action.AOEAction
import game.fighters.action.AOEActionSet
import game.fighters.action.FighterAction
import game.fighters.action.FighterActionSet
import game.fighters.action.InteractionEffectType

class FighterStats(private var level : Int,
                   private var maximalHealth : Float,
                   private var currentHealth : Float,
                   private var movingDistance : Float,
                   private val nextLevelRequirementFunction : (Int) -> Int,
                   private val maximalHealthLevelFunction : (Int) -> Float,
                   private val movingDistanceLevelFunction : (Int) -> Float,
                   private var fighterActions : FighterActionSet,
                   private var aoeActions : AOEActionSet,
                   private var activeModifiers : FighterModifier,
                   private var passiveModifiers : FighterModifier) {

    fun level() : Int = level

    fun maximalHealth() : Float = maximalHealth

    fun currentHealth() : Float = currentHealth

    fun movingDistance() : Float = movingDistance

    fun nextLevelRequirement() : Int = nextLevelRequirementFunction(level())

    fun heal(healing : Float){
        currentHealth += healing
        if(currentHealth > maximalHealth) currentHealth = maximalHealth
    }

    fun damage(damage : Float){
        currentHealth -= damage
    }

    fun isDead() : Boolean = currentHealth() <= 0

    fun isAlive() : Boolean = currentHealth > 0

    fun levelUp(){
        level++
        upgradeHealth()
        upgradeMovingDistance()
    }

    fun fighterActions() : MutableList<FighterAction> = fighterActions.actions()

    fun aoeActions() : MutableList<AOEAction> = aoeActions.actions()

    fun addActiveMultiplicativeModifier(effectType : InteractionEffectType, modifier : Float) = activeModifiers.addMultiplicativeModifier(effectType, modifier)

    fun addActiveAdditiveModifier(effectType : InteractionEffectType, modifier : Float) = activeModifiers.addAdditiveModifier(effectType, modifier)

    fun addPassiveMultiplicativeModifier(effectType : InteractionEffectType, modifier : Float) = passiveModifiers.addMultiplicativeModifier(effectType, modifier)

    fun addPassiveAdditiveModifier(effectType : InteractionEffectType, modifier : Float) = passiveModifiers.addAdditiveModifier(effectType, modifier)

    fun activeModify(effectType : InteractionEffectType, value : Float) : Float = activeModifiers.modify(effectType, value)

    fun passiveModify(effectType : InteractionEffectType, value : Float) : Float = passiveModifiers.modify(effectType, value)

    private fun upgradeHealth(){
        val healthVariation : Float = maximalHealthLevelFunction(level())
        maximalHealth += healthVariation
        currentHealth += healthVariation
    }

    private fun upgradeMovingDistance(){
        movingDistance += movingDistanceLevelFunction(level)
    }

}