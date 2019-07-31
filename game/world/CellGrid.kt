package game.world

import game.world.cells.AbstractCell
import llayout.utilities.GraphicAction

class CellGrid(private val grid: Array<Array<AbstractCell>>) {

    private val numberOfLines : Int = grid.size

    private val numberOfColumns : Int = grid[0].size

    fun numberOfLines() : Int = numberOfLines

    fun numberOfColumns() : Int = numberOfColumns

    fun imageAt(line : Int, column : Int) : GraphicAction = grid[line][column].image()

}