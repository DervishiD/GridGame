package game.world.defaults

import game.fighters.AbstractFighter
import game.world.cells.AbstractFighterCell
import game.world.cells.CellComponent
import game.world.Position
import game.world.cells.CellType
import llayout.utilities.GraphicAction
import llayout.utilities.LObservable
import java.awt.Graphics

object EmptyCell : AbstractFighterCell(Position(0, 0)) {
    override var component: LObservable<CellComponent> = LObservable(NoComponent)
    override fun actOnFighterStep(fighter: AbstractFighter){}
    override fun image(): GraphicAction = { _ : Graphics, _ : Int, _ : Int ->  }
    override fun cellType(): CellType = CellType.NORMAL
}