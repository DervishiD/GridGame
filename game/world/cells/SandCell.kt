package game.world.cells

import game.fighters.AbstractFighter
import game.world.Position

class SandCell(position : Position) : AbstractFighterCell(position) {

    companion object : CellCompanion<SandCell>{

        override fun cellName(): String = "SAND"

        override fun generateFromData(data : List<String>, line : Int, column : Int) : SandCell = SandCell(Position(line, column))

        override fun encodeData(cell: SandCell, dataSeparator: String): String = cellName()

    }

    override fun actOnFighterStep(fighter: AbstractFighter) {}

    override fun cellType(): CellType = CellType.NORMAL

    override fun cellName(): String = SandCell.cellName()

}