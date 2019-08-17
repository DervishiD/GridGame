package game.info

import game.player.Player
import game.world.Position

class PlayerInfo(private val player : Player, private var position: Position) {

    fun player() : Player = player

    fun position() : Position = position

    fun up() : Position = position.up()

    fun down() : Position = position.down()

    fun left() : Position = position.left()

    fun right() : Position = position.right()

    fun front() : Position = when{
        player.isFacingUp() -> up()
        player.isFacingDown() -> down()
        player.isFacingLeft() -> left()
        else -> right()
    }

    fun setPosition(position: Position){
        this.position = position
    }

}