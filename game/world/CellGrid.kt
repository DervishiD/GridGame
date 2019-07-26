package game.world

import game.world.cells.AbstractCell
import game.world.defaults.EmptyCell

class CellGrid {

    private val numberOfLines : Int

    private val numberOfColumns : Int

    private val grid: Array<Array<AbstractCell>>

    private constructor(grid : Array<Array<AbstractCell>>){
        numberOfLines = grid.size
        numberOfColumns = grid[0].size
        this.grid = grid
    }

    constructor(lines : Int, columns : Int) : this(Array(lines) { Array<AbstractCell>(columns) { EmptyCell } })

    fun numberOfLines() : Int = numberOfLines

    fun numberOfColumns() : Int = numberOfColumns

}