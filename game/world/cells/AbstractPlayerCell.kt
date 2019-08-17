package game.world.cells

import game.fighters.AbstractFighter
import game.world.Position

abstract class AbstractPlayerCell(position : Position) : AbstractCell(position) {

    override fun actOnFighterStep(fighter: AbstractFighter) {}

}