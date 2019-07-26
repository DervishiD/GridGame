package game.world.defaults

import game.world.cells.CellComponent
import llayout6.utilities.GraphicAction
import java.awt.Graphics

object NoComponent : CellComponent {
    override fun image(): GraphicAction = { _ : Graphics, _ : Int, _ : Int ->  }
}