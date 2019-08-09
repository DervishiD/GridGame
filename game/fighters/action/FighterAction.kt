package game.fighters.action

import game.fighters.AbstractFighter
import llayout.utilities.GraphicAction
import llayout.utilities.StringDisplay

interface FighterAction {

    fun name() : CharSequence

    fun description() : Collection<StringDisplay>

    fun image() : GraphicAction

    fun act(actor : AbstractFighter, target : AbstractFighter)

    fun isAvailable(actor : AbstractFighter, target : AbstractFighter) : Boolean

    fun type() : InteractionEffectType

}