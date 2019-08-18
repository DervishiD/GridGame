package game.info

import display.guimanager.EventReceiver
import display.guimanager.GameGUIManager
import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import game.world.Zone

data class ZoneData(private val playerInfo : PlayerData,
                    private val zone : Zone,
                    private val isFighting : Boolean,
                    private val playerFighterPositions : Iterable<Position>,
                    private val enemies : Map<Position, AbstractFighter>,
                    private var hoveredPosition : Position = playerInfo.position()
) : EventReceiver {

    fun player() : Player = playerInfo.player()

    fun zone() : Zone = zone

    fun isFighting() : Boolean = isFighting

    fun playerFighterPositions() : Iterable<Position> = playerFighterPositions

    fun enemies() : Map<Position, AbstractFighter> = enemies

    fun playerPosition() : Position = playerInfo.position()

    fun hoveredPosition() : Position = hoveredPosition

    private fun playerInfo() : PlayerData = playerInfo

    override fun up() = if(isFighting()) upInFight() else playerUp()

    override fun down() = if(isFighting()) downInFight() else playerDown()

    override fun left() = if(isFighting()) leftInFight() else playerLeft()

    override fun right() = if(isFighting()) rightInFight() else playerRight()

    private fun upInFight(){
        TODO("Not implemented.")
    }

    private fun downInFight(){
        TODO("Not implemented.")
    }

    private fun leftInFight(){
        TODO("Not implemented.")
    }

    private fun rightInFight(){
        TODO("Not implemented.")
    }

    private fun playerUp(){
        player().faceUp()
        val up : Position = playerInfo().up()
        if(up in zone() && player().canStepOn(zone().cellAt(up))){
            movePlayerTo(up)
            GameGUIManager.moveZoneDisplayerUp()
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    private fun playerDown(){
        player().faceDown()
        val down : Position = playerInfo().down()
        if(down in zone() && player().canStepOn(zone().cellAt(down))){
            movePlayerTo(down)
            GameGUIManager.moveZoneDisplayerDown()
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    private fun playerLeft(){
        player().faceLeft()
        val left : Position = playerInfo().left()
        if(left in zone() && player().canStepOn(zone().cellAt(left))){
            movePlayerTo(left)
            GameGUIManager.moveZoneDisplayerLeft()
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    private fun playerRight(){
        player().faceRight()
        val right : Position = playerInfo().right()
        if(right in zone() && player().canStepOn(zone().cellAt(right))){
            movePlayerTo(right)
            GameGUIManager.moveZoneDisplayerRight()
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    override fun select() = if(isFighting()) selectInFight() else playerSelect()

    private fun selectInFight(){
        TODO("Not implemented.")
    }

    private fun playerSelect(){
        val frontPosition : Position = playerInfo().front()
        if(frontPosition in zone()) zone().cellAt(frontPosition).component().reactToPlayerInteraction()
    }

    private fun movePlayerTo(position : Position){
        zone().moveCellComponent(playerPosition(), position)
        playerInfo().setPosition(position)
        zone().cellAt(position).actOnPlayerStep(player())
        setHoveredPosition(position)
    }

    private fun setHoveredPosition(position: Position){
        hoveredPosition = position
    }

    override fun escape() {
        GameGUIManager.toGameMenu(this)
    }

    override fun onSet() = GameGUIManager.toZoneDisplayer(this)

}