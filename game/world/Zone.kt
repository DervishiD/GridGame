package game.world

import game.fighters.AbstractFighter
import game.fights.FightDetector
import game.fights.FightGenerator
import game.player.Player
import game.world.cells.AbstractCell
import game.world.cells.CellComponent
import game.world.defaults.EmptyCell
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.math.ceil
import kotlin.math.floor

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

    fun generateFight(player : Player) : Pair<Iterable<Position>, Map<Position, AbstractFighter>>{
        return FightGenerator.generateFightFor(this, player, fightGeneration)
    }

    fun cellAt(line : Int, column : Int) : AbstractCell = cells.cellAt(line, column)

    fun cellAt(position : Position) : AbstractCell = cells.cellAt(position)

    fun componentAt(position : Position) : CellComponent = cellAt(position).component()

    fun moveCellComponent(from : Position, to : Position) = cells.moveComponent(from, to)

    operator fun contains(position : Position) : Boolean{
        return position.isPositive() && position.line() < numberOfLines() && position.column() < numberOfColumns()
    }

    fun availablePositions(fighter : AbstractFighter) : Collection<Position>{
        val result : MutableCollection<Position> = mutableSetOf()
        val fighterPosition : Position = fighter.currentCell().position()
        val upperLeftCorner = Position(floor(fighterPosition.line() - fighter.movingDistance()).toInt(),
                                       floor(fighterPosition.column() - fighter.movingDistance()).toInt())
        val lowerRightCorner = Position(ceil(fighterPosition.line() + fighter.movingDistance()).toInt(),
            ceil(fighterPosition.column() + fighter.movingDistance()).toInt())
        for(position : Position in upperLeftCorner..lowerRightCorner){
            if(position in this && !cellAt(position).containsObject() && fighter.canStepOn(cellAt(position))){
                result.add(position)
            }
        }
        return result
    }
    
    fun availableCells(fighter : AbstractFighter) : Collection<AbstractCell>{
        val result : MutableCollection<AbstractCell> = mutableSetOf()
        for(position : Position in availablePositions(fighter)) result.add(cellAt(position))
        return result
    }

}