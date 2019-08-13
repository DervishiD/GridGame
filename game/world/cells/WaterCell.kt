package game.world.cells

import display.images.ImageLoader
import game.fighters.AbstractFighter
import game.world.Position
import llayout.utilities.GraphicAction

class WaterCell(position : Position) : AbstractFighterCell(position) {

    companion object{

        const val CELL_NAME : String = "WATER"

        fun generateFromData(data : List<String>, line : Int, column : Int) : WaterCell = WaterCell(Position(line, column))

    }

    override fun actOnFighterStep(fighter: AbstractFighter) {}

    override fun cellType(): CellType = CellType.WATER

    override fun image(): GraphicAction = ImageLoader.loadWaterImage()

}