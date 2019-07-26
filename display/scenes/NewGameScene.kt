package display.scenes

import display.frame
import llayout6.displayers.TextButton
import llayout6.frame.LScene

object NewGameScene : LScene() {

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    init{
        addBackButton()
    }

    private fun addBackButton(){
        add(BACK_BUTTON.alignLeftTo(0).alignTopTo(0))
    }

    private fun back(){
        frame.setScene(FirstScene)
    }

}