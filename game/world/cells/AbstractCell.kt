package game.world.cells

import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import game.world.defaults.NoComponent
import llayout.utilities.Action
import llayout.utilities.GraphicAction
import llayout.utilities.LObservable

abstract class AbstractCell(private val position : Position) {

    companion object{

        protected const val DATA_SEPARATOR : String = "|"

        fun generateFromString(data : String, line : Int, column : Int) : AbstractCell{
            val dataTable : List<String> = data.split(DATA_SEPARATOR)
            return callCorrectCell(dataTable[0], dataTable, line, column)
        }

        private fun callCorrectCell(cellName : String, data : List<String>, line : Int, column : Int) : AbstractCell{
            return when(cellName){
                WaterCell.cellName() -> WaterCell.generateFromData(data, line, column)
                SandCell.cellName() -> SandCell.generateFromData(data, line, column)
                else -> TODO("Not implemented.")
            }
        }

    }

    private var component : LObservable<CellComponent> = LObservable(NoComponent)

    fun containsObject() : Boolean = component() != NoComponent

    fun setCellComponent(component : CellComponent){
        this.component.value = component
    }

    fun addComponentListener(action : Action){
        component.addListener(action)
    }

    fun empty() = setCellComponent(NoComponent)

    fun position() : Position = position

    fun component() : CellComponent = component.value

    abstract fun actOnFighterStep(fighter : AbstractFighter)

    abstract fun actOnPlayerStep(player : Player)

    abstract fun cellType() : CellType

    abstract fun cellName() : String

}