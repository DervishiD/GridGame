package game.world.cells

import game.player.Player
import game.world.Position

abstract class AbstractFighterCell(position : Position) : AbstractCell(position) {

    override fun actOnPlayerStep(player: Player) {}

}