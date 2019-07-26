package game.interactions

import game.fighters.AbstractFighter
import game.gameobjects.HealingObject

class StandardHealing(healer : AbstractFighter, target : AbstractFighter, healing : HealingObject)
    : FighterInteraction<AbstractFighter, AbstractFighter, HealingObject>(healer, target, healing){

    override fun act() = target().heal(gameObject().healing(source(), target()))

}