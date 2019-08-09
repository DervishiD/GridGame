package game.fighters.action

import game.fighters.AbstractFighter
import game.world.cells.AbstractCell
import llayout.utilities.GraphicAction
import llayout.utilities.StringDisplay

interface AOEAction {

    fun name() : CharSequence

    fun description() : Collection<StringDisplay>

    fun image() : GraphicAction

    fun range() : Collection<AbstractCell>

    fun act(actor : AbstractFighter)

    fun isAvailable(actor : AbstractFighter)

    fun type() : InteractionEffectType

}