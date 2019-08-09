package game.stats

import game.fighters.action.InteractionEffectType

class FighterModifier(private val modifierMap : MutableMap<InteractionEffectType, EffectModifier> = mutableMapOf()) {

    fun addAdditiveModifier(effect : InteractionEffectType, additiveModifier : Float){
        if(modifierMap.containsKey(effect)){
            modifierMap[effect]!!.add(additiveModifier)
        }else{
            modifierMap[effect] = EffectModifier(1.0f, additiveModifier)
        }
    }

    fun addMultiplicativeModifier(effect : InteractionEffectType, multiplicativeModifier : Float){
        if(modifierMap.containsKey(effect)){
            modifierMap[effect]!!.multiplyBy(multiplicativeModifier)
        }else{
            modifierMap[effect] = EffectModifier(multiplicativeModifier, 0.0f)
        }
    }

    fun modify(effectType : InteractionEffectType, value : Float) : Float{
        return if(modifierMap.containsKey(effectType)) modifierMap[effectType]!!.modify(value) else value
    }

}