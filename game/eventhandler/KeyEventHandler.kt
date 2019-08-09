package game.eventhandler

import display.guimanager.EventReceiver
import java.awt.event.KeyEvent.*

object KeyEventHandler {

    private lateinit var receiver : EventReceiver

    fun handlePress(code : Int){
        when(code){
            VK_UP, VK_W , VK_I-> handleUp()
            VK_DOWN, VK_S, VK_K -> handleDown()
            VK_LEFT, VK_A, VK_J -> handleLeft()
            VK_RIGHT, VK_D, VK_L -> handleRight()
            VK_SPACE, VK_ENTER -> handleSelect()
            VK_ESCAPE -> handleEscape()
        }
    }

    fun setReceiver(receiver : EventReceiver){
        this.receiver = receiver
        receiver.onSet()
    }

    private fun handleUp() = receiver.up()

    private fun handleDown() = receiver.down()

    private fun handleLeft() = receiver.left()

    private fun handleRight() = receiver.right()

    private fun handleSelect() = receiver.select()

    private fun handleEscape() = receiver.escape()

}