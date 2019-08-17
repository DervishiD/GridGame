package game.world

import display.images.ImageLoader
import game.world.cells.AbstractCell
import llayout.utilities.GraphicAction

class CellGrid(private val grid: Array<Array<AbstractCell>>) {

    private val numberOfLines : Int = grid.size

    private val numberOfColumns : Int = grid[0].size

    fun numberOfLines() : Int = numberOfLines

    fun numberOfColumns() : Int = numberOfColumns

    fun imageAt(line : Int, column : Int) : GraphicAction = ImageLoader.imageOf(cellAt(line, column))

    fun cellAt(line : Int, column : Int) : AbstractCell = grid[line][column]

    fun cellAt(position : Position) : AbstractCell = cellAt(position.line(), position.column())

    fun moveComponent(from : Position, to : Position){
        cellAt(to).setCellComponent(cellAt(from).component())
        cellAt(from).empty()
    }

}