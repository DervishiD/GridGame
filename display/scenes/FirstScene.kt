package display.scenes

import display.frame
import llayout6.DEFAULT_LARGE_FONT
import llayout6.DEFAULT_MEDIUM_FONT
import llayout6.displayers.Label
import llayout6.displayers.TextButton
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay

object FirstScene : LScene() {

    private val EXIT_BUTTON : TextButton = TextButton("Exit") { exit() }

    private val TITLE : Label = Label(StringDisplay("Grid Game", DEFAULT_LARGE_FONT))
    private const val TITLE_X : Double = 0.5
    private const val TITLE_Y : Double = 0.3

    private val NEW_GAME_BUTTON : TextButton = TextButton(StringDisplay("New Game", DEFAULT_MEDIUM_FONT)) { newGame() }
    private const val NEW_GAME_X : Double = 0.5
    private const val NEW_GAME_Y : Double = 0.5

    private val LOAD_GAME_BUTTON : TextButton = TextButton(StringDisplay("Load Game", DEFAULT_MEDIUM_FONT)) { loadGame() }
    private const val LOAD_GAME_X : Double = 0.5
    private const val LOAD_GAME_Y : Double = 0.6

    init{
        addExitButton()
        addTitle()
        addNewGameButton()
        addLoadGameButton()
    }

    private fun addExitButton(){
        add(EXIT_BUTTON.alignLeftTo(0).alignTopTo(0))
    }

    private fun addTitle(){
        add(TITLE.setX(TITLE_X).setY(TITLE_Y))
    }

    private fun addNewGameButton(){
        add(NEW_GAME_BUTTON.setX(NEW_GAME_X).setY(NEW_GAME_Y))
    }

    private fun addLoadGameButton(){
        add(LOAD_GAME_BUTTON.setX(LOAD_GAME_X).setY(LOAD_GAME_Y))
    }

    private fun exit(){
        frame.close()
    }

    private fun newGame(){
        frame.setScene(NewGameScene)
    }

    private fun loadGame(){
        frame.setScene(LoadGameScene)
    }

}