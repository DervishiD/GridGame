package game.world.cells

interface CellComponent {

    fun componentID() : String

    fun reactToPlayerInteraction()

}