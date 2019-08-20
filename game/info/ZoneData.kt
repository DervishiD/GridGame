package game.info

import display.guimanager.EventReceiver
import display.guimanager.FighterMenuReceiver
import display.guimanager.GameGUIManager
import game.eventhandler.KeyEventHandler
import game.fighters.AbstractFighter
import game.gamestate.GameState
import game.gamestate.GameState.*
import game.player.Player
import game.world.Position
import game.world.Zone
import game.world.cells.CellComponent

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

    private fun canPause() : Boolean = state == PLAYER || state == DEFAULT_FIGHT

    private fun playerInfo() : PlayerData = playerInfo

    override fun up() = when(state){
        PLAYER -> playerUp()
        DEFAULT_FIGHT -> defaultFightUp()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    override fun down() = when(state){
        PLAYER -> playerDown()
        DEFAULT_FIGHT -> defaultFightDown()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    override fun left() = when(state){
        PLAYER -> playerLeft()
        DEFAULT_FIGHT -> defaultFightLeft()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    override fun right() = when(state){
        PLAYER -> playerRight()
        DEFAULT_FIGHT -> defaultFightRight()
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

    private fun defaultFightUp(){
        setHoveredPosition(hoveredPosition().up())
        GameGUIManager.moveZoneDisplayerUp()
        GameGUIManager.displayInGameInformation(zone().componentAt(hoveredPosition()))
    }

    private fun defaultFightDown(){
        setHoveredPosition(hoveredPosition().down())
        GameGUIManager.moveZoneDisplayerDown()
        GameGUIManager.displayInGameInformation(zone().componentAt(hoveredPosition()))
    }

    private fun defaultFightLeft(){
        setHoveredPosition(hoveredPosition().left())
        GameGUIManager.moveZoneDisplayerLeft()
        GameGUIManager.displayInGameInformation(zone().componentAt(hoveredPosition()))
    }

    private fun defaultFightRight(){
        setHoveredPosition(hoveredPosition().right())
        GameGUIManager.moveZoneDisplayerRight()
        GameGUIManager.displayInGameInformation(zone().componentAt(hoveredPosition()))
    }

    override fun select() = when(state){
        PLAYER -> playerSelect()
        DEFAULT_FIGHT -> defaultFightSelect()
        PERFORMING_ACTION_ON_ZONE -> TODO()
    }

    private fun playerSelect(){
        val frontPosition : Position = playerInfo().front()
        if(frontPosition in zone()) zone().cellAt(frontPosition).component().reactToPlayerInteraction()
    }

    private fun defaultFightSelect(){
        val hoveredComponent : CellComponent = zone().componentAt(hoveredPosition())
        if(hoveredComponent.componentID() == AbstractFighter.componentID() && player().teamContains(hoveredComponent as AbstractFighter)){
            FighterMenuReceiver.setData(this, hoveredComponent)
            KeyEventHandler.setReceiver(FighterMenuReceiver)
        }
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
        if(canPause()) GameGUIManager.toGameMenu(this) else TODO("Not implemented.")
    }

    override fun onSet() = GameGUIManager.toZoneDisplayer(this)

}