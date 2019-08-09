package game.fighters.action

enum class InteractionEffectType(private val effectName : String) {
    TEST_A("Test A"),
    TEST_B("Test B");

    fun effectName() : String = effectName

}