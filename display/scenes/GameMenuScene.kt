package display.scenes

import display.frame
import game.fighters.AbstractFighter
import game.player.Player
import game.world.Zone
import llayout.displayers.*
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

    private class FighterInformationDisplayer(private val fighter : AbstractFighter) : ContainerCanvas(1.0, FIGHTER_INFORMATION_DISPLAYER_HEIGHT){

        private companion object{

            private const val IMAGE_DISPLAYER_SIZE : Int = 50
            private const val IMAGE_DISPLAYER_TEAM_INDICATOR_GAP : Int = 10

            private const val IMAGE_LEVEL_GAP : Int = 5
            private const val LEVEL_TOP_ALIGNMENT : Int = 0

            private const val TYPE_LEVEL_GAP : Int = 10

            private const val NAME_LEVEL_GAP : Int = 5
            private const val NAME_IMAGE_GAP : Int = 5

            private const val NAME_BUTTON_GAP : Int = 10
            private const val TYPE_BUTTON_GAP : Int = 10

            private const val TEAM_INDICATOR_SIZE : Int = 40
            private const val TEAM_INDICATOR_LEFT_GAP : Int = 10

            private val FRAME_COLOUR : Color = BLACK

        }

        private class TeamIndicator(size : Int, private val fighter : AbstractFighter) : Canvas(size, size){

            private companion object{

                private val IN_TEAM_COLOUR : Color = Color(0, 162, 0)

                private val FRAME_COLOUR : Color = BLACK

            }

            private var inTeam : Boolean = isInTeam(fighter)

            init{
                addGraphicAction({ g : Graphics, w : Int, h : Int ->
                    if(inTeam()){
                        g.color = IN_TEAM_COLOUR
                        g.fillRect(0, 0, w, h)
                    }
                })
                twoPixelFrame(FRAME_COLOUR)
                setOnMouseReleasedAction { toggleTeam() }
            }

            private fun inTeam() : Boolean = inTeam

            private fun toggleTeam() = if(inTeam()) tryAddToTeam() else removeFighterFromTeam()

            private fun tryAddToTeam(){
                if(teamIsNotFull()) addFighterToTeam()
            }

            private fun addFighterToTeam(){
                inTeam = true
                addToTeam(fighter)
            }

            private fun removeFighterFromTeam(){
                inTeam = false
                removeFromTeam(fighter)
            }

        }

        private val imageDisplayer : Canvas = Canvas(IMAGE_DISPLAYER_SIZE, IMAGE_DISPLAYER_SIZE)

        private val levelLabel : Label = Label("Level ${fighter.level()}")

        private val fighterTypeLabel : Label = Label(fighter.type())

        private val nameLabel : Label = Label(fighter.name())

        private val showInformationButton : TextButton = TextButton("Show") { showInformation() }

        private val teamIndicator : TeamIndicator = TeamIndicator(TEAM_INDICATOR_SIZE, fighter)

        init{
            addTeamIndicator()
            addImageDisplayer()
            addLevel()
            addType()
            addName()
            addButton()
            twoPixelFrame(FRAME_COLOUR)
        }

        private fun addImageDisplayer(){
            add(imageDisplayer
                .addGraphicAction({ g : Graphics, w : Int, h : Int ->
                    g.color = BLACK
                    g.fillRect(0, 0, w, h)
                    fighter.image()(g, w, h)
                })
                .alignLeftToRight(teamIndicator, IMAGE_DISPLAYER_TEAM_INDICATOR_GAP)
                .setY(0.5))
        }

        private fun addLevel(){
            add(levelLabel.alignLeftToRight(imageDisplayer, IMAGE_LEVEL_GAP).alignTopTo(LEVEL_TOP_ALIGNMENT))
        }

        private fun addType(){
            add(fighterTypeLabel.alignLeftToRight(levelLabel, TYPE_LEVEL_GAP).alignTopToTop(levelLabel))
        }

        private fun addName(){
            add(nameLabel.alignTopToBottom(levelLabel, NAME_LEVEL_GAP).alignLeftToRight(imageDisplayer, NAME_IMAGE_GAP))
        }

        private fun addButton(){
            add(showInformationButton.alignLeftToRight(nameLabel, NAME_BUTTON_GAP).alignTopToBottom(fighterTypeLabel, TYPE_BUTTON_GAP))
        }

        private fun addTeamIndicator(){
            add(teamIndicator.setY(0.5).alignLeftTo(TEAM_INDICATOR_LEFT_GAP))
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

    private val FIGHTER_SCROLL_PANE : VerticalScrollPane = VerticalScrollPane(SCROLL_PANE_WIDTH, PANE_HEIGHT)

    init{
        drawPaneSeparator()
        addMenuButtons()
        addFighterScrollPane()
        setOnSaveAction { unloadInformation() }
    }

    private lateinit var player : Player

    private lateinit var zone : Zone

    fun show(player : Player, zone : Zone){
        this.player = player
        this.zone = zone
        loadFighters()
        //TODO
        frame.setScene(this)
    }

    fun player() : Player = player

    fun zone() : Zone = zone

    fun isInTeam(fighter : AbstractFighter) : Boolean = player.teamContains(fighter)

    fun currentTeamSize() : Int = player.teamSize()

    fun fullTeamSize() : Int = player.fullTeamSize()

    fun removeFromTeam(fighter : AbstractFighter) = player.removeFromTeam(fighter)

    fun addToTeam(fighter : AbstractFighter) = player.addToTeam(fighter)

    fun teamIsNotFull() : Boolean = !teamIsFull()

    fun teamIsFull() : Boolean = currentTeamSize() == fullTeamSize()

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

    private fun addFighterScrollPane(){
        add(FIGHTER_SCROLL_PANE.alignLeftTo(SCROLL_PANE_SIDE_GAP).alignTopTo(PANE_VERTICAL_GAP))
    }

    private fun loadFighters(){
        for(fighter : AbstractFighter in player().fighters()) FIGHTER_SCROLL_PANE.add(FighterInformationDisplayer(fighter))
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

    private fun unloadInformation(){
        unloadFighters()
        TODO("Not implemented.")
    }

    private fun unloadFighters(){
        for(i : Int in 0 until player().fighters().size()){
            FIGHTER_SCROLL_PANE.removeAt(0)
        }
    }

}