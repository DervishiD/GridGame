package game.world

class Zone(numberOfLines : Int, numberOfColumns : Int) {

    private val cells : CellGrid = CellGrid(numberOfLines, numberOfColumns)

    private fun numberOfLines() : Int = cells.numberOfLines()

    private fun numberOfColumns() : Int = cells.numberOfColumns()

}