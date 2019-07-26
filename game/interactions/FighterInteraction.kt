package game.interactions

import game.fighters.AbstractFighter
import game.gameobjects.GameObject

abstract class FighterInteraction<S : AbstractFighter, T : AbstractFighter, O : GameObject>(source : S, private val target : T, gameObject : O)
    : AbstractInteraction<S, O>(source, gameObject) {

    protected fun target() : T = target

}