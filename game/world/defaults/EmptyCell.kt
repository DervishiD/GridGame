package game.world.defaults

import game.fighters.AbstractFighter
import game.world.Position
import game.world.cells.AbstractFighterCell
import game.world.cells.CellType

object EmptyCell : AbstractFighterCell(Position(0, 0)) {
    override fun actOnFighterStep(fighter: AbstractFighter){}
    override fun cellType(): CellType = CellType.NORMAL
    override fun cellName() : String = "EMPTY_CELL"
}