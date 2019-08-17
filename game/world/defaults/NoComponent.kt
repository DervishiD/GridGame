package game.world.defaults

import game.world.cells.CellComponent

object NoComponent : CellComponent {
    override fun componentID() : String = "NO_COMPONENT"
    override fun reactToPlayerInteraction() {}
}