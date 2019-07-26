package game.interactions

import game.fighters.AbstractFighter
import game.gameobjects.GameObject

abstract class AbstractInteraction<S : AbstractFighter, O : GameObject>(private val source : S, private val gameObject : O) {

    protected fun source() : S = source

    protected fun gameObject() : O = gameObject

    abstract fun act()

}