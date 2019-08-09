package game.world

import game.fights.FightDetector
import game.fights.FightGenerator
import game.world.cells.AbstractCell
import game.world.defaults.EmptyCell
import llayout.utilities.GraphicAction
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Zone{

    companion object{

        private const val FILE_EXTENSION : String = ".txt"

        private const val ZONE_FOLDER_NAME : String = "zones"

        private const val SOURCE_FOLDER_NAME : String = "src"

        private fun zoneFolderName() : String = SOURCE_FOLDER_NAME + File.separator + ZONE_FOLDER_NAME + File.separator

        private fun readName(reader : BufferedReader) : String{
            return reader.readLine()
        }

        private fun readFightDetection(reader : BufferedReader) : CharSequence{
            return reader.readLine()
        }

        private fun readFightGeneration(reader : BufferedReader) : CharSequence{
            return reader.readLine()
        }

        private fun generateCellGrid(reader : BufferedReader) : CellGrid{
            val lines : Int = reader.readLine().toInt()
            val columns : Int = reader.readLine().toInt()
            val grid : Array<Array<AbstractCell>> = Array(lines) { Array<AbstractCell>(columns) { EmptyCell } }
            for(line : Int in 0 until lines){
                for(column : Int in 0 until columns){
                    grid[line][column] = AbstractCell.generateFromString(reader.readLine(), line, column)
                }
            }
            return CellGrid(grid)
        }

        fun generateZoneByName(name : String) : Zone = Zone(name + FILE_EXTENSION)

    }

    private val cells : CellGrid

    private val fightDetection : CharSequence

    private val fightGeneration : CharSequence

    private val name : String

    private constructor(fileName : String){
        val reader = BufferedReader(FileReader(File(zoneFolderName() + fileName)))
        /*
         * ORDER MATTERS
         */
        name = readName(reader)
        fightDetection = readFightDetection(reader)
        fightGeneration = readFightGeneration(reader)
        cells = generateCellGrid(reader)
    }

    fun numberOfLines() : Int = cells.numberOfLines()

    fun numberOfColumns() : Int = cells.numberOfColumns()

    fun hasAFight() : Boolean = FightDetector.detectFightFor(fightDetection)

    fun generateFight() = FightGenerator.generateFightFor(this, fightGeneration)

    fun imageAt(line : Int, column : Int) : GraphicAction = cells.imageAt(line, column)

    fun cellAt(line : Int, column : Int) : AbstractCell = cells.cellAt(line, column)

    fun cellAt(position : Position) : AbstractCell = cells.cellAt(position)

    fun moveCellComponent(from : Position, to : Position) = cells.moveComponent(from, to)

    operator fun contains(position : Position) : Boolean{
        return position.isPositive() && position.line() < numberOfLines() && position.column() < numberOfColumns()
    }

}