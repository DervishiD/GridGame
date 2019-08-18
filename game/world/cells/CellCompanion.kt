package game.world.cells

interface CellCompanion<T : AbstractCell> {

    fun cellName() : String

    fun generateFromData(data : List<String>, line : Int, column : Int) : T

    fun encodeData(cell : T, dataSeparator : String) : String

}