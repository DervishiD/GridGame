package game.world.cells

import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import game.world.defaults.NoComponent
import llayout6.utilities.GraphicAction
import java.awt.Color
import java.awt.Graphics

class DefaultCell private constructor(position: Position) : AbstractCell(position) {

    companion object{

        const val CELL_NAME : String = "DEFAULT"

        fun generateFromData(data : List<String>, line : Int, column : Int) : DefaultCell = DefaultCell(Position(line, column))

    }

    override val component: CellComponent = NoComponent

    override fun actOnFighterStep(fighter: AbstractFighter) {}

    override fun actOnPlayerStep(player: Player) {}

    override fun cellType(): CellType {
        return CellType.NORMAL
    }

    override fun image(): GraphicAction {
        return { g : Graphics, w : Int, h : Int ->
            g.color = Color.RED
            g.fillRect(0, 0, w, h)
            component.image()(g, w, h)
        }
    }

    override fun toString(): String {
        return CELL_NAME
    }

}