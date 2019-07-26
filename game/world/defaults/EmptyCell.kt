package game.world.defaults

import game.fighters.AbstractFighter
import game.world.cells.AbstractFighterCell
import game.world.cells.CellComponent
import game.world.Position
import llayout6.utilities.GraphicAction
import java.awt.Graphics

object EmptyCell : AbstractFighterCell(Position(0, 0)) {
    override val component: CellComponent = NoComponent
    override fun actOnFighterStep(fighter: AbstractFighter){}
    override fun image(): GraphicAction = { _ : Graphics, _ : Int, _ : Int ->  }
}