package game.world.cells

import display.images.ImageLoader
import game.fighters.AbstractFighter
import game.world.Position
import llayout.utilities.GraphicAction

class SandCell(position : Position) : AbstractFighterCell(position) {

    companion object{

        const val CELL_NAME : String = "SAND"

        fun generateFromData(data : List<String>, line : Int, column : Int) : SandCell = SandCell(Position(line, column))

    }

    override fun actOnFighterStep(fighter: AbstractFighter) {}

    override fun cellType(): CellType = CellType.NORMAL

    override fun image(): GraphicAction = ImageLoader.loadSandImage()

}