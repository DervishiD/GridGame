package game.world.cells

import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import llayout6.utilities.GraphicAction

abstract class AbstractCell(private val position : Position) {

    abstract val component : CellComponent

    abstract fun actOnFighterStep(fighter : AbstractFighter)

    abstract fun actOnPlayerStep(player : Player)

    abstract fun image() : GraphicAction

}