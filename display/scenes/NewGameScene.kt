package display.scenes

import display.frame
import game.fighters.AbstractFighter
import game.fighters.TestFighter
import llayout6.DEFAULT_LARGE_FONT
import llayout6.DEFAULT_MEDIUM_FONT
import llayout6.displayers.Label
import llayout6.displayers.TextArrowSelector
import llayout6.displayers.TextButton
import llayout6.displayers.TextField
import llayout6.frame.LScene
import llayout6.utilities.StringDisplay
import llayout6.utilities.Text
import java.awt.Color.BLACK

object NewGameScene : LScene() {

    private val BACK_BUTTON : TextButton = TextButton("Back") { back() }

    private val TITLE : Label = Label(StringDisplay("New Game", DEFAULT_LARGE_FONT))
    private const val TITLE_X : Double = 0.5
    private const val TITLE_Y : Double = 0.2

    private val NAME_TAG : Label = Label(StringDisplay("Name : ", DEFAULT_MEDIUM_FONT))
    private val NAME_FIELD : TextField = TextField()
    private const val NAME_X_ALIGNMENT : Double = 0.4
    private const val NAME_Y : Double = 0.35

    private val FIGHTER_TYPE_TAG : Label = Label(StringDisplay("Fighter : ", DEFAULT_MEDIUM_FONT))
    private val FIGHTER_TYPES : Map<Text, AbstractFighter> = mapOf(
        Text("Test") to TestFighter()
    )
    private val FIGHTER_TYPE_SELECTOR : TextArrowSelector<AbstractFighter> = TextArrowSelector(FIGHTER_TYPES)
    private const val FIGHTER_X_ALIGNMENT : Double = NAME_X_ALIGNMENT
    private const val FIGHTER_Y : Double = 0.4

    init{
        addBackButton()
        addTitle()
        addName()
        addFighter()
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
        add(NAME_TAG.alignRightTo(NAME_X_ALIGNMENT).setY(NAME_Y))
    }

    private fun addNameField(){
        add(NAME_FIELD.matchWord().alignLeftTo(NAME_X_ALIGNMENT).setY(NAME_Y))
    }

    private fun addFighter(){
        addFighterTag()
        addFighterSelector()
    }

    private fun addFighterTag(){
        add(FIGHTER_TYPE_TAG.alignRightTo(FIGHTER_X_ALIGNMENT).setY(FIGHTER_Y))
    }

    private fun addFighterSelector(){
        add(FIGHTER_TYPE_SELECTOR.setArrowsColor(BLACK).alignLeftTo(FIGHTER_X_ALIGNMENT).setY(FIGHTER_Y))
    }

    private fun back(){
        frame.setScene(FirstScene)
    }

    private fun name() : String = NAME_FIELD.text()

    private fun fighter() : AbstractFighter = FIGHTER_TYPE_SELECTOR.selectedOption()

}