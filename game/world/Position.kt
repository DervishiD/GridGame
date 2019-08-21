package game.world

import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class Position(private val line : Int, private val column : Int) {

    companion object{

        private const val DATA_SEPARATOR : String = ","

        fun encode(position : Position) : String = "${position.line}$DATA_SEPARATOR${position.column}"

        fun decode(positionCode : String) : Position{
            val data : List<String> = positionCode.split(DATA_SEPARATOR)
            return Position(data[0].toInt(), data[1].toInt())
        }

    }

    fun line() : Int = line

    fun column() : Int = column

    fun up() : Position = Position(line - 1, column)

    fun down() : Position = Position(line + 1, column)

    fun left() : Position = Position(line, column - 1)

    fun right() : Position = Position(line, column + 1)

    fun distance(other : Position) : Float = hypot(line - other.line + 0.0, column - other.column + 0.0).toFloat()

    fun isPositive() : Boolean = line >= 0 && column >= 0

    fun isStrictlyPositive() : Boolean = line > 0 && column > 0

    operator fun plus(other : Position) : Position = Position(line + other.line, column + other.column)

    operator fun minus(other : Position) : Position = Position(line - other.line, column - other.column)

    operator fun get(line : Int, column : Int) : Position = Position(this.line + line, this.column + column)

    operator fun rangeTo(other : Position) : Collection<Position>{
        val result : MutableCollection<Position> = mutableSetOf()
        for(i : Int in min(line, other.line)..max(line, other.line)){
            for(j : Int in min(column, other.column)..max(column, other.column)){
                result.add(Position(i, j))
            }
        }
        return result
    }

    override operator fun equals(other : Any?) : Boolean{
        return if(other is Position){
            other.line() == line() && other.column() == column()
        }else super.equals(other)
    }

    override fun hashCode(): Int = Integer.hashCode(line()) + Integer.hashCode(column())

}