package game.stats

import game.fighters.action.AOEAction
import game.fighters.action.AOEActionSet
import game.fighters.action.FighterAction
import game.fighters.action.FighterActionSet

class FighterStats(private var level : Int,
                   private var maximalHealth : Float,
                   private var currentHealth : Float,
                   private var movingDistance : Float,
                   private var nextLevelRequirementFunction : (Int) -> Int,
                   private var maximalHealthLevelFunction : (Int) -> Float,
                   private var movingDistanceLevelFunction : (Int) -> Float,
                   private var fighterActions : FighterActionSet,
                   private var aoeActions : AOEActionSet) {

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

    private fun upgradeHealth(){
        val healthVariation : Float = maximalHealthLevelFunction(level())
        maximalHealth += healthVariation
        currentHealth += healthVariation
    }

    private fun upgradeMovingDistance(){
        movingDistance += movingDistanceLevelFunction(level)
    }

}