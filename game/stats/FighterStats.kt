package game.stats

class FighterStats(private var level : Int,
                   private var maximalHealth : Float,
                   private var currentHealth : Float,
                   private var movingDistance : Float,
                   private var nextLevelRequirementFunction : (Int) -> Int,
                   private var maximalHealthLevelFunction : (Int) -> Float,
                   private var movingDistanceLevelFunction : (Int) -> Float) {

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

    private fun upgradeHealth(){
        val healthVariation : Float = maximalHealthLevelFunction(level())
        maximalHealth += healthVariation
        currentHealth += healthVariation
    }

    private fun upgradeMovingDistance(){
        movingDistance += movingDistanceLevelFunction(level)
    }

}