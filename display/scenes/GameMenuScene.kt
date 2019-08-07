package display.scenes

import display.frame
import display.specialdisplayers.SkillTreeDisplayer
import display.specialdisplayers.SkillTreeNodeDisplayer
import game.fighters.AbstractFighter
import game.fighters.action.AOEAction
import game.fighters.action.FighterAction
import game.gameobjects.GameObject
import game.player.Player
import game.player.QuantifiedObject
import game.world.ZoneInfo
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

            private fun toggleTeam() = if(inTeam()) tryRemoveFighterFromTeam() else tryAddToTeam()

            private fun tryAddToTeam(){
                if(teamIsNotFull()) addFighterToTeam()
            }

            private fun addFighterToTeam(){
                inTeam = true
                addToTeam(fighter)
            }

            private fun tryRemoveFighterFromTeam(){
                if(player().teamSize() > 1) removeFighterFromTeam()
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

        private const val IMAGE_LEFT_GAP : Int = 30
        private const val IMAGE_TOP_GAP : Int = 30
        private const val IMAGE_SIZE : Int = 50

        private const val IMAGE_INFO_GAP : Int = 10

        private const val INFO_TAG_GAP : Int = 10

        private const val GAP_BETWEEN_INFO : Int = 25

        private const val INSIDE_INFO_GAP : Int = 10

        private const val INFO_TAG_LEFT_GAP : Int = 25

        private const val ACTION_PANE_HEIGHT : Int = 400

        private const val ACTION_DISPLAYER_HEIGHT : Int = 200

        private const val ACTION_DESCRIPTION_HEIGHT : Int = 80

        private val POP_UP_PANE_COLOUR : Color = Color(0.8f, 0.8f, 0.8f, 0.2f)

        private const val SKILL_TREE_WIDTH : Double = 0.7

        private val displayers : MutableCollection<Displayer> = mutableSetOf()

        private val scrollPane : VerticalScrollPane = VerticalScrollPane(1.0, 1.0)

        private const val INTERNAL_PANE_HEIGHT : Int = 920
        private val pane : DisplayerContainer = DisplayerContainer(1.0, INTERNAL_PANE_HEIGHT)

        init{
            add(scrollPane.setX(0.5).setY(0.5))
            scrollPane.add(pane)
        }

        fun showInformation(obj : GameObject){
            clearPane()

            val image = Canvas(IMAGE_SIZE, IMAGE_SIZE).addGraphicAction(obj.image())
            displayers.add(image)
            pane.add(image.alignLeftTo(IMAGE_LEFT_GAP).alignTopTo(IMAGE_TOP_GAP))

            val nameTag = Label("Name")
            val nameLabel = Label(obj.name())
            displayers.add(nameTag)
            displayers.add(nameLabel)
            pane.add(nameTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToTop(image))
            pane.add(nameLabel.alignLeftToLeft(nameTag, INFO_TAG_LEFT_GAP).alignTopToBottom(nameTag, INFO_TAG_GAP))

            val descriptionTag = Label("Description")
            val description = TextScrollPane(width() - IMAGE_LEFT_GAP - IMAGE_SIZE - IMAGE_INFO_GAP - INFO_TAG_LEFT_GAP, 0.5)
            addWidthListener { description.setWidth(width() - IMAGE_LEFT_GAP - IMAGE_SIZE - IMAGE_INFO_GAP) }
            description.write(obj.description())
            displayers.add(descriptionTag)
            displayers.add(description)
            pane.add(descriptionTag.alignLeftToLeft(nameTag).alignTopToBottom(nameLabel, GAP_BETWEEN_INFO))
            pane.add(description.alignLeftToLeft(descriptionTag, INFO_TAG_LEFT_GAP).alignTopToBottom(descriptionTag, INFO_TAG_GAP))
        }

        fun showInformation(fighter : AbstractFighter){
            clearPane()

            val image = Canvas(IMAGE_SIZE, IMAGE_SIZE)
            image.addGraphicAction(fighter.image())
            displayers.add(image)
            pane.add(image.alignLeftTo(IMAGE_LEFT_GAP).alignTopTo(IMAGE_TOP_GAP))

            val nameTag = Label("Name")
            val nameLabel = Label(fighter.name())
            displayers.add(nameTag)
            displayers.add(nameLabel)
            pane.add(nameTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToTop(image))
            pane.add(nameLabel.alignLeftToLeft(nameTag, INFO_TAG_LEFT_GAP).alignTopToBottom(nameTag, INFO_TAG_GAP))

            val typeTag = Label("Type")
            val typeLabel = Label(fighter.type())
            displayers.add(typeTag)
            displayers.add(typeLabel)
            pane.add(typeTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(nameLabel, GAP_BETWEEN_INFO))
            pane.add(typeLabel.alignTopToBottom(typeTag, INFO_TAG_GAP).alignLeftToLeft(typeTag, INFO_TAG_LEFT_GAP))

            val levelTag = Label("Level")
            val levelLabel = Label(fighter.level())
            val levelUpButton = TextButton("Level up") { levelUpPopUp(fighter) }
            displayers.add(levelTag)
            displayers.add(levelLabel)
            displayers.add(levelUpButton)
            pane.add(levelTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(typeLabel, GAP_BETWEEN_INFO))
            pane.add(levelLabel.alignLeftToLeft(levelTag, INFO_TAG_LEFT_GAP).alignTopToBottom(levelTag, INFO_TAG_GAP))
            pane.add(levelUpButton.alignLeftToLeft(levelLabel).alignTopToBottom(levelLabel, INSIDE_INFO_GAP))

            val HPTag = Label("Health points")
            val HPLabel = Label("${fighter.maxHealth()}")
            displayers.add(HPTag)
            displayers.add(HPLabel)
            pane.add(HPTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(levelUpButton, GAP_BETWEEN_INFO))
            pane.add(HPLabel.alignLeftToLeft(HPTag, INFO_TAG_LEFT_GAP).alignTopToBottom(HPTag, INFO_TAG_GAP))

            val distanceTag = Label("Moving distance")
            val distanceLabel = Label("%.1f".format(fighter.movingDistance()))
            displayers.add(distanceTag)
            displayers.add(distanceLabel)
            pane.add(distanceTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(HPLabel, GAP_BETWEEN_INFO))
            pane.add(distanceLabel.alignLeftToLeft(distanceTag, INFO_TAG_LEFT_GAP).alignTopToBottom(distanceTag, INFO_TAG_GAP))

            val actionTag = Label("Actions")
            val actionPane =
                VerticalScrollPane(
                    width() - IMAGE_LEFT_GAP - IMAGE_SIZE - IMAGE_INFO_GAP - INFO_TAG_LEFT_GAP - VerticalScrollPane.SLIDER_WIDTH,
                    ACTION_PANE_HEIGHT
                )
            displayers.add(actionTag)
            displayers.add(actionPane)
            pane.add(actionTag.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(distanceLabel, GAP_BETWEEN_INFO))
            pane.add(actionPane.alignLeftToLeft(actionTag, INFO_TAG_LEFT_GAP).alignTopToBottom(actionTag, INFO_TAG_GAP))

            for(action : FighterAction in fighter.fighterActions()){
                actionPane.add(actionDisplayerFor(action))
            }

            for(action : AOEAction in fighter.aoeActions()){
                actionPane.add(actionDisplayerFor(action))
            }

            val skillTreeButton = TextButton("Open Skill Tree") { skillTreePaneFor(fighter) }
            displayers.add(skillTreeButton)
            pane.add(skillTreeButton.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(actionPane, GAP_BETWEEN_INFO))
        }

        private fun clearPane(){
            pane.remove(displayers)
            displayers.clear()
        }

        private fun actionDisplayerFor(action : FighterAction) : ContainerCanvas{
            val result = ContainerCanvas(1.0, ACTION_DISPLAYER_HEIGHT)

            val image = Canvas(IMAGE_SIZE, IMAGE_SIZE).addGraphicAction(action.image())
            result.add(image.alignLeftTo(IMAGE_LEFT_GAP).alignTopTo(IMAGE_TOP_GAP))

            val name = Label(action.name())
            result.add(name.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToTop(image))

            val description = TextScrollPane(1, ACTION_DESCRIPTION_HEIGHT)
            result.addWidthListener { description.setWidth(result.width() - IMAGE_LEFT_GAP - IMAGE_SIZE - IMAGE_INFO_GAP) }
            description.write(action.description())
            result.add(description.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(name, GAP_BETWEEN_INFO))

            return result
        }

        private fun actionDisplayerFor(action : AOEAction) : ContainerCanvas{
            val result = ContainerCanvas(1.0, ACTION_DISPLAYER_HEIGHT)

            val image = Canvas(IMAGE_SIZE, IMAGE_SIZE).addGraphicAction(action.image())
            result.add(image.alignLeftTo(IMAGE_LEFT_GAP).alignTopTo(IMAGE_TOP_GAP))

            val name = Label(action.name())
            result.add(name.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToTop(image))

            val description = TextScrollPane(1, ACTION_DESCRIPTION_HEIGHT)
            result.addWidthListener { description.setWidth(result.width() - IMAGE_LEFT_GAP - IMAGE_SIZE - IMAGE_INFO_GAP) }
            description.write(action.description())
            result.add(description.alignLeftToRight(image, IMAGE_INFO_GAP).alignTopToBottom(name, GAP_BETWEEN_INFO))

            return result
        }

        private fun levelUpPopUp(fighter : AbstractFighter){
            val popUpPane = ContainerCanvas(1.0, 1.0)
            popUpPane.fillBackground(POP_UP_PANE_COLOUR)
            popUpPane.setOnMouseReleasedAction { GameMenuScene.remove(popUpPane) }

            val dialogPane = ContainerCanvas(0.5, 0.5)
            dialogPane.twoPixelFrame()
            popUpPane.add(dialogPane.setX(0.5).setY(0.5))

            val canLevelUp : Boolean = fighter.nextLevelRequirements() <= player().points()

            val text = Label(if(canLevelUp)
                levelUpText(fighter.nextLevelRequirements(), player().points())
                             else
                noLevelUpText(fighter.nextLevelRequirements(), player().points()))
            popUpPane.add(text.setX(0.5).setY(0.3))

            if(canLevelUp){
                val levelUpButton = TextButton("Level up") {
                    levelUp(fighter)
                    GameMenuScene.remove(popUpPane)
                }
                popUpPane.add(levelUpButton.setX(0.5).setY(0.7))
            }

            GameMenuScene.add(popUpPane.setX(0.5).setY(0.5))
        }

        private fun levelUp(fighter : AbstractFighter){
            player().removePoints(fighter.nextLevelRequirements())
            fighter.levelUp()
            showInformation(fighter)
            unloadFighters()
            loadFighters()
        }

        private fun levelUpText(fighterPoints : Int, playerPoints : Int) : Collection<StringDisplay>{
            return setOf(StringDisplay("Leveling up will cost you $fighterPoints points.\nYou have $playerPoints points."))
        }

        private fun noLevelUpText(fighterPoints: Int, playerPoints: Int) : Collection<StringDisplay>{
            return setOf(StringDisplay("Leveling up costs $fighterPoints points, but you only have $playerPoints."))
        }

        private fun skillTreePaneFor(fighter : AbstractFighter){
            val backgroundPane = ContainerCanvas(1.0, 1.0)
            backgroundPane.fillBackground(POP_UP_PANE_COLOUR)
            backgroundPane.setOnMouseReleasedAction { GameMenuScene.remove(backgroundPane) }

            val treePane = ContainerCanvas(1.0, 1.0)
            backgroundPane.add(treePane.setX(0.5).setY(0.5))
            treePane.add(SkillTreeDisplayer.setWidth(SKILL_TREE_WIDTH).setHeight(1.0).alignTopTo(0).alignRightTo(1.0))

            SkillTreeDisplayer.setTree(fighter, player())

            SkillTreeNodeDisplayer.setTree(fighter.skillTree())
            val back = TextButton("Back") {}
            back.setOnMouseReleasedAction {
                GameMenuScene.remove(backgroundPane)
                SkillTreeNodeDisplayer.remove(back)
            }
            SkillTreeNodeDisplayer.add(back.alignTopTo(0).alignLeftTo(0))
            treePane.add(SkillTreeNodeDisplayer.setWidth(1 - SKILL_TREE_WIDTH).setHeight(1.0).alignTopTo(0).alignLeftTo(0))

            GameMenuScene.add(backgroundPane.setX(0.5).setY(0.5))
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
        addInfoPane()
        setOnSaveAction { unloadInformation() }
    }

    private lateinit var zoneInfo : ZoneInfo

    fun show(zoneInfo : ZoneInfo){
        this.zoneInfo = zoneInfo
        loadFighters()
        loadObjects()
        frame.setScene(this)
    }

    private fun player() : Player = zoneInfo.player()

    private fun isInTeam(fighter : AbstractFighter) : Boolean = player().teamContains(fighter)

    private fun currentTeamSize() : Int = player().teamSize()

    private fun fullTeamSize() : Int = player().fullTeamSize()

    private fun removeFromTeam(fighter : AbstractFighter) = player().removeFromTeam(fighter)

    private fun addToTeam(fighter : AbstractFighter) = player().addToTeam(fighter)

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

    private fun addInfoPane(){
        add(InfoPane.alignTopTo(PANE_VERTICAL_GAP).alignLeftTo(SCROLL_PANE_SIDE_GAP + SCROLL_PANE_WIDTH + SCROLL_PANE_INFO_GAP))
    }

    private fun loadFighters(){
        for(fighter : AbstractFighter in player().fighters()) FIGHTER_SCROLL_PANE.add(FighterInformationDisplayer(fighter))
    }

    private fun loadObjects(){
        for(obj : QuantifiedObject in player().inventory()) OBJECT_SCROLL_PANE.add(ObjectInformationDisplayer(obj))
    }

    private fun resume(){
        frame.setScene(GameScene)
        GameScene.load(zoneInfo)
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