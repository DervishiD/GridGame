package display.scenes

import display.frame
import game.fighters.AbstractFighter
import game.gameobjects.GameObject
import game.player.Player
import game.player.QuantifiedObject
import game.world.Zone
import llayout.DEFAULT_MEDIUM_FONT
import llayout.displayers.*
import llayout.frame.LScene
import llayout.utilities.StringDisplay
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Graphics

object GameMenuScene : LScene() {

    private const val LOWER_PANE_HEIGHT : Double = 0.08

    private const val UPPER_PANE_HEIGHT : Double = 1 - LOWER_PANE_HEIGHT

    private const val MENU_BUTTONS_Y : Double = UPPER_PANE_HEIGHT + LOWER_PANE_HEIGHT / 2

    private const val FIGHTER_INFORMATION_DISPLAYER_HEIGHT : Int = 180

    private const val OBJECT_INFORMATION_DISPLAYER_HEIGHT : Int = 180

    private val PANE_SEPARATOR_COLOUR : Color = BLACK

    private const val SCROLL_PANE_SIDE_GAP : Double = 0.05

    private const val SCROLL_PANE_WIDTH : Double = 0.25

    private const val SCROLL_PANE_INFO_GAP : Double = 0.05

    private const val INFO_WIDTH : Double = 1 - 2 * ( SCROLL_PANE_SIDE_GAP + SCROLL_PANE_WIDTH + SCROLL_PANE_INFO_GAP )

    private const val PANE_VERTICAL_GAP : Double = 0.1

    private const val PANE_HEIGHT : Double = UPPER_PANE_HEIGHT - 2 * (PANE_VERTICAL_GAP)

    private const val LABEL_PANE_GAP : Int = -10

    private class FighterInformationDisplayer(private val fighter : AbstractFighter) : ContainerCanvas(1.0, FIGHTER_INFORMATION_DISPLAYER_HEIGHT){

        private companion object{

            private const val IMAGE_DISPLAYER_SIZE : Int = 80
            private const val IMAGE_DISPLAYER_TEAM_INDICATOR_GAP : Int = 20

            private const val IMAGE_INFO_GAP : Int = 40

            private const val TYPE_BOTTOM_ALIGNMENT : Double = 0.45

            private const val NAME_TYPE_GAP : Int = -15

            private const val LEVEL_TYPE_GAP : Int = 15

            private const val BUTTON_LEVEL_GAP : Int = 15

            private const val TEAM_INDICATOR_SIZE : Int = 40
            private const val TEAM_INDICATOR_LEFT_GAP : Int = 30

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

            private fun toggleTeam() = if(inTeam()) removeFighterFromTeam() else tryAddToTeam()

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
            addName()
            addType()
            addLevel()
            addButton()
            twoPixelFrame(FRAME_COLOUR)
        }

        private fun addImageDisplayer(){
            add(imageDisplayer
                .addGraphicAction({ g : Graphics, w : Int, h : Int -> fighter.image()(g, w, h) })
                .alignLeftToRight(teamIndicator, IMAGE_DISPLAYER_TEAM_INDICATOR_GAP)
                .setY(0.5))
        }

        private fun addLevel(){
            add(levelLabel.alignTopToBottom(fighterTypeLabel, LEVEL_TYPE_GAP).alignLeftToLeft(fighterTypeLabel))
        }

        private fun addType(){
            add(fighterTypeLabel.alignLeftToRight(imageDisplayer, IMAGE_INFO_GAP).alignBottomTo(TYPE_BOTTOM_ALIGNMENT))
        }

        private fun addName(){
            add(nameLabel.alignBottomToTop(fighterTypeLabel, NAME_TYPE_GAP).alignLeftToLeft(fighterTypeLabel))
        }

        private fun addButton(){
            add(showInformationButton.alignTopToBottom(levelLabel, BUTTON_LEVEL_GAP).alignLeftToLeft(fighterTypeLabel))
        }

        private fun addTeamIndicator(){
            add(teamIndicator.setY(0.5).alignLeftTo(TEAM_INDICATOR_LEFT_GAP))
        }

        private fun showInformation(){
            InfoPane.showInformation(fighter)
        }

    }

    private object InfoPane : DisplayerContainer(INFO_WIDTH, PANE_HEIGHT){

        fun showInformation(obj : GameObject){
            TODO("Not implemented.")
        }

        fun showInformation(fighter : AbstractFighter){
            TODO("Not implemented.")
        }

    }

    private class ObjectInformationDisplayer(private val obj : QuantifiedObject) : ContainerCanvas(1.0, OBJECT_INFORMATION_DISPLAYER_HEIGHT){

        private companion object{

            private const val IMAGE_SIZE : Int = 50

            private const val IMAGE_DISPLAYER_LEFT_ALIGNMENT : Int = 40

            private val FRAME_COLOUR : Color = BLACK

            private const val INFO_IMAGE_GAP : Int = 40

            private const val NAME_BOTTOM_ALIGNMENT : Double = 0.45

            private const val BUTTON_TOP_ALIGNMENT : Double = 1 - NAME_BOTTOM_ALIGNMENT

        }

        private val imageDisplayer : Canvas = Canvas(IMAGE_SIZE, IMAGE_SIZE)

        private val nameLabel : Label = Label("${obj.type().name()}  (${obj.quantity()})")

        private val showInformationButton : TextButton = TextButton("Show") { showInformation() }

        init{
            twoPixelFrame(FRAME_COLOUR)
            addImage()
            addNameAndQuantity()
            addButton()
        }

        private fun addImage(){
            add(imageDisplayer.addGraphicAction(obj.type().image()).setY(0.5).alignLeftTo(IMAGE_DISPLAYER_LEFT_ALIGNMENT))
        }

        private fun addNameAndQuantity(){
            add(nameLabel.alignLeftToRight(imageDisplayer, INFO_IMAGE_GAP).alignBottomTo(NAME_BOTTOM_ALIGNMENT))
        }

        private fun addButton(){
            add(showInformationButton.alignLeftToRight(imageDisplayer, INFO_IMAGE_GAP).alignTopTo(BUTTON_TOP_ALIGNMENT))
        }

        private fun showInformation(){
            InfoPane.showInformation(obj.type())
        }

    }

    private val RESUME_BUTTON : TextButton = TextButton("Resume") { resume() }
    private const val RESUME_BUTTON_X : Double = 0.2

    private val SAVE_BUTTON : TextButton = TextButton("Save") { save() }
    private const val SAVE_BUTTON_X : Double = 0.5

    private val SAVE_AND_QUIT_BUTTON : TextButton = TextButton("Save and quit") { saveAndQuit() }
    private const val SAVE_AND_QUIT_BUTTON_X : Double = 0.8

    private val FIGHTER_SCROLL_PANE : VerticalScrollPane = VerticalScrollPane(SCROLL_PANE_WIDTH, PANE_HEIGHT)

    private val OBJECT_SCROLL_PANE : VerticalScrollPane = VerticalScrollPane(SCROLL_PANE_WIDTH, PANE_HEIGHT)

    private val FIGHTER_LABEL : Label = Label(StringDisplay("Fighters", DEFAULT_MEDIUM_FONT))

    private val OBJECTS_LABEL : Label = Label(StringDisplay("Objects", DEFAULT_MEDIUM_FONT))

    init{
        drawPaneSeparator()
        addMenuButtons()
        addFighterScrollPane()
        addObjectScrollPane()
        addFighterLabel()
        addObjectLabel()
        setOnSaveAction { unloadInformation() }
    }

    private lateinit var player : Player

    private lateinit var zone : Zone

    fun show(player : Player, zone : Zone){
        this.player = player
        this.zone = zone
        loadFighters()
        loadObjects()
        frame.setScene(this)
    }

    private fun player() : Player = player

    private fun zone() : Zone = zone

    private fun isInTeam(fighter : AbstractFighter) : Boolean = player.teamContains(fighter)

    private fun currentTeamSize() : Int = player.teamSize()

    private fun fullTeamSize() : Int = player.fullTeamSize()

    private fun removeFromTeam(fighter : AbstractFighter) = player.removeFromTeam(fighter)

    private fun addToTeam(fighter : AbstractFighter) = player.addToTeam(fighter)

    private fun teamIsNotFull() : Boolean = !teamIsFull()

    private fun teamIsFull() : Boolean = currentTeamSize() == fullTeamSize()

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

    private fun addObjectScrollPane(){
        add(OBJECT_SCROLL_PANE.alignTopTo(PANE_VERTICAL_GAP).alignLeftTo(SCROLL_PANE_SIDE_GAP + SCROLL_PANE_WIDTH + 2 * SCROLL_PANE_INFO_GAP + INFO_WIDTH))
    }

    private fun addFighterLabel(){
        FIGHTER_SCROLL_PANE.addXListener { FIGHTER_LABEL.setX(FIGHTER_SCROLL_PANE.x()) }
        add(FIGHTER_LABEL.alignBottomToTop(FIGHTER_SCROLL_PANE, LABEL_PANE_GAP))
    }

    private fun addObjectLabel(){
        OBJECT_SCROLL_PANE.addXListener { OBJECTS_LABEL.setX(OBJECT_SCROLL_PANE.x()) }
        add(OBJECTS_LABEL.alignBottomToTop(OBJECT_SCROLL_PANE, LABEL_PANE_GAP))
    }

    private fun loadFighters(){
        for(fighter : AbstractFighter in player().fighters()) FIGHTER_SCROLL_PANE.add(FighterInformationDisplayer(fighter))
    }

    private fun loadObjects(){
        for(obj : QuantifiedObject in player().inventory()) OBJECT_SCROLL_PANE.add(ObjectInformationDisplayer(obj))
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
        unloadObjects()
        TODO("Not implemented.")
    }

    private fun unloadFighters(){
        for(i : Int in 0 until player().fighters().size()){
            FIGHTER_SCROLL_PANE.removeAt(0)
        }
    }

    private fun unloadObjects(){
        for(i : Int in 0 until player().inventory().size()){
            OBJECT_SCROLL_PANE.removeAt(0)
        }
    }

}