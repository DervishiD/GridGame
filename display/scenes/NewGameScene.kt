package display.scenes

import display.frame
import game.fighters.AbstractFighter
import game.fighters.TestFighter
import game.player.Player
import game.world.Zone
import llayout.DEFAULT_LARGE_FONT
import llayout.DEFAULT_MEDIUM_FONT
import llayout.displayers.Label
import llayout.displayers.TextArrowSelector
import llayout.displayers.TextButton
import llayout.displayers.TextField
import llayout.frame.LScene
import llayout.utilities.StringDisplay
import llayout.utilities.Text
import java.awt.Color.BLACK
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object NewGameScene : LScene() {

    private const val GAMES_FOLDER : String = "gamesfolder"

    private const val SOURCE_FOLDER_NAME : String = "src"

    private const val GAMES_FILE_NAME : String = "games.txt"

    private const val DATA_SEPARATOR : String = "|"

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    private val TITLE : Label = Label(StringDisplay("New Game", DEFAULT_LARGE_FONT))
    private const val TITLE_X : Double = 0.5
    private const val TITLE_Y : Double = 0.2

    private val NAME_TAG : Label = Label(StringDisplay("Name : ", DEFAULT_MEDIUM_FONT))
    private val NAME_FIELD : TextField = TextField()
    private const val NAME_SELECTION_X_ALIGNMENT : Double = 0.4
    private const val NAME_Y : Double = 0.35

    private val FIGHTER_TYPE_TAG : Label = Label(StringDisplay("Fighter : ", DEFAULT_MEDIUM_FONT))
    private val FIGHTER_TYPES : Map<Text, AbstractFighter> = mapOf(
        Text("Test") to TestFighter()
    )
    private val FIGHTER_TYPE_SELECTOR : TextArrowSelector<AbstractFighter> = TextArrowSelector(FIGHTER_TYPES)
    private const val FIGHTER_SELECTION_X_ALIGNMENT : Double = NAME_SELECTION_X_ALIGNMENT
    private const val FIGHTER_Y : Double = 0.4

    private val GAME_SELECTOR_TAG : Label = Label(StringDisplay("Choose your game : ", DEFAULT_MEDIUM_FONT))
    private val GAME_SELECTOR : TextArrowSelector<String> = TextArrowSelector(generateGamesMap())
    private const val GAME_SELECTION_X_ALIGNMENT : Double = NAME_SELECTION_X_ALIGNMENT
    private const val GAME_SELECTION_Y : Double = 0.45

    private val SAVE_GAME_BUTTON : TextButton = TextButton("Save Game") { saveGameAndLeave() }
    private const val SAVE_GAME_BUTTON_X : Double = 0.4
    private const val SAVE_GAME_BUTTON_Y : Double = 0.7

    private val START_GAME_BUTTON : TextButton = TextButton("Start Game") { startGame() }
    private const val START_GAME_BUTTON_X : Double = 0.6
    private const val START_GAME_BUTTON_Y : Double = SAVE_GAME_BUTTON_Y

    init{
        addBackButton()
        addTitle()
        addName()
        addFighter()
        addGameSelection()
        addSaveGameButton()
        addStartGameButton()
    }

    private fun addBackButton(){
        add(BACK_BUTTON.alignLeftTo(0).alignTopTo(0))
    }

    private fun addTitle(){
        add(TITLE.setX(TITLE_X).setY(TITLE_Y))
    }

    private fun addName(){
        addNameTag()
        addNameField()
    }

    private fun addNameTag(){
        add(NAME_TAG.alignRightTo(NAME_SELECTION_X_ALIGNMENT).setY(NAME_Y))
    }

    private fun addNameField(){
        add(NAME_FIELD.matchWordIncludingSpaces().alignLeftTo(NAME_SELECTION_X_ALIGNMENT).setY(NAME_Y))
    }

    private fun addFighter(){
        addFighterTag()
        addFighterSelector()
    }

    private fun addFighterTag(){
        add(FIGHTER_TYPE_TAG.alignRightTo(FIGHTER_SELECTION_X_ALIGNMENT).setY(FIGHTER_Y))
    }

    private fun addFighterSelector(){
        add(FIGHTER_TYPE_SELECTOR.setArrowsColor(BLACK).alignLeftTo(FIGHTER_SELECTION_X_ALIGNMENT).setY(FIGHTER_Y))
    }

    private fun addGameSelection(){
        addGameSelectionTag()
        addGameSelector()
    }

    private fun addGameSelectionTag(){
        add(GAME_SELECTOR_TAG.alignRightTo(GAME_SELECTION_X_ALIGNMENT).setY(GAME_SELECTION_Y))
    }

    private fun addGameSelector(){
        add(GAME_SELECTOR.setArrowsColor(BLACK).alignLeftTo(GAME_SELECTION_X_ALIGNMENT).setY(GAME_SELECTION_Y))
    }

    private fun addSaveGameButton(){
        add(SAVE_GAME_BUTTON.setX(SAVE_GAME_BUTTON_X).setY(SAVE_GAME_BUTTON_Y))
    }

    private fun addStartGameButton(){
        add(START_GAME_BUTTON.setX(START_GAME_BUTTON_X).setY(START_GAME_BUTTON_Y))
    }

    private fun back(){
        frame.setScene(FirstScene)
    }

    private fun generateGamesMap() : Map<Text, String>{
        val result : MutableMap<Text, String> = mutableMapOf()
        val reader = BufferedReader(FileReader(File(SOURCE_FOLDER_NAME + File.separator + GAMES_FOLDER + File.separator + GAMES_FILE_NAME)))
        for(line : String in reader.lines()){
            val data : List<String> = line.split(DATA_SEPARATOR)
            result[Text(data[0])] = data[1]
        }
        return result
    }

    private fun name() : String = NAME_FIELD.text()

    private fun fighter() : AbstractFighter = FIGHTER_TYPE_SELECTOR.selectedOption()

    private fun zoneName() : String = GAME_SELECTOR.selectedOption()

    private fun player() : Player{
        val fighter : AbstractFighter = fighter()
        fighter.setName(name())
        return Player(name(), fighter)
    }

    private fun zone() : Zone = Zone.generateZoneByName(zoneName())

    private fun saveGameAndLeave(){
        saveGame()
        back()
    }

    private fun saveGame(){
        TODO("Not implemented.")
    }

    private fun startGame(){
        GameMenuScene.show(player(), zone())
    }

}