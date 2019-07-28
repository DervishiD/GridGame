package game.world.cells

import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import llayout6.utilities.GraphicAction

abstract class AbstractCell(private val position : Position) {

    companion object{

        protected const val DATA_SEPARATOR : String = "|"

        fun generateFromString(data : String, line : Int, column : Int) : AbstractCell{
            val dataTable : List<String> = data.split(DATA_SEPARATOR)
            return callCorrectCell(dataTable[0], dataTable, line, column)
        }

        private fun callCorrectCell(cellName : String, data : List<String>, line : Int, column : Int) : AbstractCell{
            return when(cellName){
                DefaultCell.CELL_NAME -> DefaultCell.generateFromData(data, line, column)
                else -> TODO("Not implemented.")
            }
        }

    }

    abstract val component : CellComponent

    abstract fun actOnFighterStep(fighter : AbstractFighter)

    abstract fun actOnPlayerStep(player : Player)

    abstract fun image() : GraphicAction

    abstract fun cellType() : CellType

}