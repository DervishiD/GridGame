package game.stats

class EffectModifier(private var multiplicativeModifier : Float = 1.0f, private var additiveModifier : Float = 0.0f) {

    companion object{

        private const val DATA_SEPARATOR : String = "|"

        private fun decode(encodedEffectModifier : String) : EffectModifier = decode(encodedEffectModifier.split(DATA_SEPARATOR))

        private fun decode(data : List<String>) : EffectModifier = EffectModifier(data[0].toFloat(), data[1].toFloat())

    }

    fun multiplyBy(modifier : Float){
        multiplicativeModifier *= modifier
    }

    fun add(modifier : Float){
        additiveModifier += modifier
    }

    fun modify(value : Float) : Float = multiplicativeModifier * value + additiveModifier

    override fun toString(): String = "$multiplicativeModifier$DATA_SEPARATOR$additiveModifier"

}