package game.eventhandler

import java.awt.event.KeyEvent.*

object KeyEventHandler {

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

    private fun handleUp(){
        TODO("Not implemented.")
    }

    private fun handleDown(){
        TODO("Not implemented.")
    }

    private fun handleLeft(){
        TODO("Not implemented.")
    }

    private fun handleRight(){
        TODO("Not implemented.")
    }

    private fun handleSelect(){
        TODO("Not implemented.")
    }

    private fun handleEscape(){
        TODO("Not implemented.")
    }

}