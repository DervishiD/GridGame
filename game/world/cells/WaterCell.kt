package game.world.cells

import game.fighters.AbstractFighter
import game.world.Position

class WaterCell(position : Position) : AbstractFighterCell(position) {

    companion object : CellCompanion<WaterCell>{

        override fun cellName() : String = "WATER"

        override fun generateFromData(data : List<String>, line : Int, column : Int) : WaterCell = WaterCell(Position(line, column))

    }

    override fun actOnFighterStep(fighter: AbstractFighter) {}

    override fun cellType(): CellType = CellType.WATER

    override fun cellName(): String = WaterCell.cellName()

}