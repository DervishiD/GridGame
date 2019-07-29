package display.scenes

import display.frame
import llayout.displayers.TextButton
import llayout.frame.LScene

object LoadGameScene : LScene() {

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