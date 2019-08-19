package game.info

import display.guimanager.EventReceiver
import display.guimanager.GameGUIManager
import game.fighters.AbstractFighter
import game.gamestate.GameState
import game.gamestate.GameState.*
import game.player.Player
import game.world.Position
import game.world.Zone

data class ZoneData(private val playerInfo : PlayerData,
                    private val zone : Zone,
                    private var state : GameState,
                    private val playerFighterPositions : Iterable<Position>,
                    private val enemies : Map<Position, AbstractFighter>,
                    private var hoveredPosition : Position = playerInfo.position()
) : EventReceiver {

    fun player() : Player = playerInfo.player()

    fun zone() : Zone = zone

    fun playerFighterPositions() : Iterable<Position> = playerFighterPositions

    fun enemies() : Map<Position, AbstractFighter> = enemies

    fun playerPosition() : Position = playerInfo.position()

    fun hoveredPosition() : Position = hoveredPosition

    fun canSave() : Boolean = state == PLAYER

    private fun playerInfo() : PlayerData = playerInfo

    override fun up() = when(state){
        PLAYER -> playerUp()
        DEFAULT_FIGHT -> TODO()
        FIGHTER_ACTION_SELECTION -> TODO()
        BROWSING_OBJECTS -> TODO()
        BROWSING_ACTIONS -> TODO()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    override fun down() = when(state){
        PLAYER -> playerDown()
        DEFAULT_FIGHT -> TODO()
        FIGHTER_ACTION_SELECTION -> TODO()
        BROWSING_OBJECTS -> TODO()
        BROWSING_ACTIONS -> TODO()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    override fun left() = when(state){
        PLAYER -> playerLeft()
        DEFAULT_FIGHT -> TODO()
        FIGHTER_ACTION_SELECTION -> TODO()
        BROWSING_OBJECTS -> TODO()
        BROWSING_ACTIONS -> TODO()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    override fun right() = when(state){
        PLAYER -> playerRight()
        DEFAULT_FIGHT -> TODO()
        FIGHTER_ACTION_SELECTION -> TODO()
        BROWSING_OBJECTS -> TODO()
        BROWSING_ACTIONS -> TODO()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    private fun playerUp(){
        player().faceUp()
        val up : Position = playerInfo().up()
        if(up in zone() && player().canStepOn(zone().cellAt(up))){
            GameGUIManager.moveZoneDisplayerUp()
            movePlayerTo(up)
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    private fun playerDown(){
        player().faceDown()
        val down : Position = playerInfo().down()
        if(down in zone() && player().canStepOn(zone().cellAt(down))){
            GameGUIManager.moveZoneDisplayerDown()
            movePlayerTo(down)
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    private fun playerLeft(){
        player().faceLeft()
        val left : Position = playerInfo().left()
        if(left in zone() && player().canStepOn(zone().cellAt(left))){
            GameGUIManager.moveZoneDisplayerLeft()
            movePlayerTo(left)
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    private fun playerRight(){
        player().faceRight()
        val right : Position = playerInfo().right()
        if(right in zone() && player().canStepOn(zone().cellAt(right))){
            GameGUIManager.moveZoneDisplayerRight()
            movePlayerTo(right)
        }
        zone().cellAt(playerPosition()).setCellComponent(player())
    }

    override fun select() = when(state){
        PLAYER -> playerSelect()
        DEFAULT_FIGHT -> TODO()
        FIGHTER_ACTION_SELECTION -> TODO()
        BROWSING_OBJECTS -> TODO()
        BROWSING_ACTIONS -> TODO()
        PERFORMING_ACTION_ON_ZONE -> TODO()
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