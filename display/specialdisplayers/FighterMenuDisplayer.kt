package display.specialdisplayers

import game.eventhandler.KeyEventHandler
import game.fighters.AbstractFighter
import llayout.displayers.Canvas
import llayout.displayers.ContainerCanvas
import llayout.interfaces.StandardLContainer
import llayout.utilities.StringDisplay
import java.awt.Color

object FighterMenuDisplayer : ContainerCanvas() {

    private const val BUTTONS_Y : Double = 0.5
    private const val ACTIONS_BUTTON_X : Double = 0.25
    private const val MOVE_BUTTON_X : Double = 0.5
    private const val OBJECTS_BUTTON_X : Double = 0.75

    private const val BUTTON_WIDTH : Double = 0.2
    private const val BUTTON_HEIGHT : Double = 0.7

    private val FOCUS_COLOUR : Color = Color.RED
    private val NOT_FOCUS_COLOUR : Color = Color.BLACK

    private const val ACTIONS_LABEL : String = "Actions"
    private const val MOVE_LABEL : String = "Move"
    private const val OBJECTS_LABEL : String = "Objects"

    private val ACTIONS_BUTTON : Canvas = Canvas(BUTTON_WIDTH, BUTTON_HEIGHT)
    private val MOVE_BUTTON : Canvas = Canvas(BUTTON_WIDTH, BUTTON_HEIGHT)
    private val OBJECTS_BUTTON : Canvas = Canvas(BUTTON_WIDTH, BUTTON_HEIGHT)

    init{
        twoPixelFrame()
        setOnKeyPressedAction { e -> KeyEventHandler.handlePress(e.keyCode) }
        ACTIONS_BUTTON.setOnKeyPressedAction { e -> KeyEventHandler.handlePress(e.keyCode) }
        MOVE_BUTTON.setOnKeyPressedAction { e -> KeyEventHandler.handlePress(e.keyCode) }
        OBJECTS_BUTTON.setOnKeyPressedAction { e -> KeyEventHandler.handlePress(e.keyCode) }
        add(ACTIONS_BUTTON.setX(ACTIONS_BUTTON_X).setY(BUTTONS_Y))
        add(MOVE_BUTTON.setX(MOVE_BUTTON_X).setY(BUTTONS_Y))
        add(OBJECTS_BUTTON.setX(OBJECTS_BUTTON_X).setY(BUTTONS_Y))
        focusActionsButton()
    }

    fun loadFor(fighter : AbstractFighter){
        if(fighter.canMove()) add(MOVE_BUTTON) else remove(MOVE_BUTTON)
        if(fighter.canAct()) add(ACTIONS_BUTTON) else remove(ACTIONS_BUTTON)
    }

    fun selectingActions(actions : Int) = when(actions % 3){
        0 -> focusActionsButton()
        1 -> focusMoveButton()
        else -> focusObjectsButton()
    }

    private fun focusActionsButton(){
        ACTIONS_BUTTON.writeCentered(StringDisplay(ACTIONS_LABEL, FOCUS_COLOUR), this)
        ACTIONS_BUTTON.twoPixelFrame(FOCUS_COLOUR, this)
        MOVE_BUTTON.writeCentered(StringDisplay(MOVE_LABEL, NOT_FOCUS_COLOUR), this)
        MOVE_BUTTON.twoPixelFrame(NOT_FOCUS_COLOUR, this)
        OBJECTS_BUTTON.writeCentered(StringDisplay(OBJECTS_LABEL, NOT_FOCUS_COLOUR), this)
        OBJECTS_BUTTON.twoPixelFrame(NOT_FOCUS_COLOUR, this)
    }

    private fun focusMoveButton(){
        ACTIONS_BUTTON.writeCentered(StringDisplay(ACTIONS_LABEL, NOT_FOCUS_COLOUR), this)
        ACTIONS_BUTTON.twoPixelFrame(NOT_FOCUS_COLOUR, this)
        MOVE_BUTTON.writeCentered(StringDisplay(MOVE_LABEL, FOCUS_COLOUR), this)
        MOVE_BUTTON.twoPixelFrame(FOCUS_COLOUR, this)
        OBJECTS_BUTTON.writeCentered(StringDisplay(OBJECTS_LABEL, NOT_FOCUS_COLOUR), this)
        OBJECTS_BUTTON.twoPixelFrame(NOT_FOCUS_COLOUR, this)
    }

    private fun focusObjectsButton(){
        ACTIONS_BUTTON.writeCentered(StringDisplay(ACTIONS_LABEL, NOT_FOCUS_COLOUR), this)
        ACTIONS_BUTTON.twoPixelFrame(NOT_FOCUS_COLOUR, this)
        MOVE_BUTTON.writeCentered(StringDisplay(MOVE_LABEL, NOT_FOCUS_COLOUR), this)
        MOVE_BUTTON.twoPixelFrame(NOT_FOCUS_COLOUR, this)
        OBJECTS_BUTTON.writeCentered(StringDisplay(OBJECTS_LABEL, FOCUS_COLOUR), this)
        OBJECTS_BUTTON.twoPixelFrame(FOCUS_COLOUR, this)
    }

    override fun onAdd(container: StandardLContainer) = focusActionsButton()

}