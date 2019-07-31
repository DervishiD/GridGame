package display.scenes

import display.frame
import game.fighters.AbstractFighter
import game.player.Player
import game.world.Zone
import llayout.displayers.Canvas
import llayout.displayers.DisplayerContainer
import llayout.displayers.Label
import llayout.displayers.TextButton
import llayout.frame.LScene
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Graphics

object GameMenuScene : LScene() {

    private const val LOWER_PANE_HEIGHT : Double = 0.08

    private const val UPPER_PANE_HEIGHT : Double = 1 - LOWER_PANE_HEIGHT

    private const val MENU_BUTTONS_Y : Double = UPPER_PANE_HEIGHT + LOWER_PANE_HEIGHT / 2

    private const val FIGHTER_INFORMATION_DISPLAYER_HEIGHT : Int = 200

    private val PANE_SEPARATOR_COLOUR : Color = BLACK

    private const val SCROLL_PANE_SIDE_GAP : Double = 0.05

    private const val SCROLL_PANE_WIDTH : Double = 0.25

    private const val SCROLL_PANE_INFO_GAP : Double = 0.05

    private const val INFO_WIDTH : Double = 1 - 2 * ( SCROLL_PANE_SIDE_GAP + SCROLL_PANE_WIDTH + SCROLL_PANE_INFO_GAP )

    private const val PANE_VERTICAL_GAP : Double = 0.1
    
    private const val PANE_HEIGHT : Double = UPPER_PANE_HEIGHT - 2 * (PANE_VERTICAL_GAP)

    private class FighterInformationDisplayer(private val fighter : AbstractFighter) : DisplayerContainer(1.0, FIGHTER_INFORMATION_DISPLAYER_HEIGHT){

        private class TeamIndicator(size : Int, private val fighter : AbstractFighter) : Canvas(size, size){

            //TODO

        }

        private val imageDisplayerSize : Int = 50
        private val imageDisplayerLeftGap : Int = 50
        private val imageDisplayer : Canvas = Canvas(imageDisplayerSize, imageDisplayerSize)

        private val levelLabel : Label = Label("Level " + fighter.level())
        private val imageLevelGap : Int = 5
        private val levelTopAlignment : Int = 0

        private val fighterTypeLabel : Label = Label(fighter.type())
        private val typeLevelGap : Int = 10

        private val nameLabel : Label = Label(fighter.name())
        private val nameLevelGap : Int = 5
        private val nameImageGap : Int = 5

        private val showInformationButton : TextButton = TextButton("Show") { showInformation() }
        private val nameButtonGap : Int = 10
        private val typeButtonGap : Int = 10

        init{
            loadImageDisplayer()
            addLevel()
            addType()
            addName()
            addButton()
        }

        private fun loadImageDisplayer(){
            add(imageDisplayer.addGraphicAction({ g : Graphics, w : Int, h : Int -> fighter.image()(g, w, h) }).alignLeftTo(imageDisplayerLeftGap).setY(0.5))
        }

        private fun addLevel(){
            add(levelLabel.alignLeftToRight(imageDisplayer, imageLevelGap).alignTopTo(levelTopAlignment))
        }

        private fun addType(){
            add(fighterTypeLabel.alignLeftToRight(levelLabel, typeLevelGap).alignTopToTop(levelLabel))
        }

        private fun addName(){
            add(nameLabel.alignTopToBottom(levelLabel, nameLevelGap).alignLeftToRight(imageDisplayer, nameImageGap))
        }

        private fun addButton(){
            add(showInformationButton.alignLeftToRight(nameLabel, nameButtonGap).alignTopToBottom(fighterTypeLabel, typeButtonGap))
        }

        private fun showInformation(){
            TODO("Not implemented.")
        }

    }

    private object InfoPane : DisplayerContainer(){

        private lateinit var fighter : AbstractFighter

        //TODO

    }

    private val RESUME_BUTTON : TextButton = TextButton("Resume") { resume() }
    private const val RESUME_BUTTON_X : Double = 0.2

    private val SAVE_BUTTON : TextButton = TextButton("Save") { save() }
    private const val SAVE_BUTTON_X : Double = 0.5

    private val SAVE_AND_QUIT_BUTTON : TextButton = TextButton("Save and quit") { saveAndQuit() }
    private const val SAVE_AND_QUIT_BUTTON_X : Double = 0.8

    init{
        drawPaneSeparator()
        addMenuButtons()
    }

    private lateinit var player : Player

    private lateinit var zone : Zone

    fun show(player : Player, zone : Zone){
        this.player = player
        this.zone = zone
        //TODO
        frame.setScene(this)
    }

    fun player() : Player = player

    fun zone() : Zone = zone

    fun isInTeam(fighter : AbstractFighter) : Boolean = player.teamContains(fighter)

    fun currentTeamSize() : Int = player.teamSize()

    fun fullTeamSize() : Int = player.fullTeamSize()

    private fun drawPaneSeparator(){
        drawLine(0, UPPER_PANE_HEIGHT, 1.0, UPPER_PANE_HEIGHT, PANE_SEPARATOR_COLOUR)
    }

    private fun addMenuButtons(){
        addResumeButton()
        addSaveButton()
        addSaveAndQuitButton()
    }

    private fun addResumeButton(){
        add(RESUME_BUTTON.setX(RESUME_BUTTON_X).setY(MENU_BUTTONS_Y))
    }

    private fun addSaveButton(){
        add(SAVE_BUTTON.setX(SAVE_BUTTON_X).setY(MENU_BUTTONS_Y))
    }

    private fun addSaveAndQuitButton(){
        add(SAVE_AND_QUIT_BUTTON.setX(SAVE_AND_QUIT_BUTTON_X).setY(MENU_BUTTONS_Y))
    }

    private fun resume(){
        TODO("Not implemented.")
    }

    private fun save(){
        TODO("Not implemented.")
    }

    private fun saveAndQuit(){
        save()
        quit()
    }

    private fun quit(){
        TODO("Not implemented.")
    }

}