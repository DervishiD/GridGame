package game.info

import display.guimanager.EventReceiver
import display.guimanager.GameGUIManager
import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import game.world.Zone

data class ZoneInfo(private val isANewZone : Boolean,
                    private val playerInfo : PlayerInfo,
                    private val zone : Zone,
                    private val isFighting : Boolean,
                    private val playerFighterPositions : Iterable<Position>,
                    private val ennemies : Map<Position, AbstractFighter>,
                    private var hoveredPosition : Position = playerInfo.position()
) : EventReceiver {

    fun player() : Player = playerInfo.player()

    fun isANewZone() : Boolean = isANewZone

    fun zone() : Zone = zone

    fun isFighting() : Boolean = isFighting

    fun playerFighterPositions() : Iterable<Position> = playerFighterPositions

    fun ennemies() : Map<Position, AbstractFighter> = ennemies

    fun playerPosition() : Position = playerInfo.position()

    fun hoveredPosition() : Position = hoveredPosition

    private fun playerInfo() : PlayerInfo = playerInfo

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
        if(up in zone()){
            movePlayerTo(up)
            GameGUIManager.moveZoneDisplayerUp()
        }
    }

    private fun playerDown(){
        player().faceDown()
        val down : Position = playerInfo().down()
        if(down in zone()){
            movePlayerTo(down)
            GameGUIManager.moveZoneDisplayerDown()
        }
    }

    private fun playerLeft(){
        player().faceLeft()
        val left : Position = playerInfo().left()
        if(left in zone()){
            movePlayerTo(left)
            GameGUIManager.moveZoneDisplayerLeft()
        }
    }

    private fun playerRight(){
        player().faceRight()
        val right : Position = playerInfo().right()
        if(right in zone()){
            movePlayerTo(right)
            GameGUIManager.moveZoneDisplayerRight()
        }
    }

    override fun select() {
        TODO("Not implemented.")
    }

    private fun movePlayerTo(position : Position){
        if(player().canStepOn(zone().cellAt(position))){
            playerInfo().setPosition(position)
            zone().moveCellComponent(playerPosition(), position)
            zone().cellAt(position).actOnPlayerStep(player())
            setHoveredPosition(position)
        }
    }

    private fun setHoveredPosition(position: Position){
        hoveredPosition = position
    }

    override fun escape() {
        TODO("Not implemented.")
    }

    override fun onSet() = GameGUIManager.toZoneDisplayer(this)

}