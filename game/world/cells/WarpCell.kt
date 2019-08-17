package game.world.cells

import game.player.Player
import game.world.Position

class WarpCell(position : Position,
               private val cellType : CellType,
               private val warpZoneName : String,
               private val warpPosition: Position) : AbstractPlayerCell(position) {

    companion object : CellCompanion<WarpCell>{

        override fun cellName(): String = "WARP"

        override fun generateFromData(data : List<String>, line : Int, column : Int) : WarpCell{
            TODO("Not implemented.")
        }

    }

    override fun actOnPlayerStep(player: Player) {
        TODO("Warp")
    }

    override fun cellType(): CellType = cellType

    override fun cellName(): String = WarpCell.cellName()

}