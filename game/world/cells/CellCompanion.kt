package game.world.cells

interface CellCompanion<out T : AbstractCell> {

    fun cellName() : String

    fun generateFromData(data : List<String>, line : Int, column : Int) : T

}