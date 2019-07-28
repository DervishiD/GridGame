package game.world

import game.world.cells.AbstractCell
import game.world.defaults.EmptyCell

class CellGrid(private val grid: Array<Array<AbstractCell>>) {

    private val numberOfLines : Int = grid.size

    private val numberOfColumns : Int = grid[0].size

    fun numberOfLines() : Int = numberOfLines

    fun numberOfColumns() : Int = numberOfColumns

}