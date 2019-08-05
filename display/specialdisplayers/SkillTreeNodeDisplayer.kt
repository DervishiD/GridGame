package display.specialdisplayers

import game.fighters.AbstractFighter
import game.player.Player
import game.skilltree.GraphicalSkillTree
import game.skilltree.GraphicalSkillTreeNode
import llayout.displayers.*

object SkillTreeNodeDisplayer : ContainerCanvas() {

    private val IMAGE_KEY : Any? = null

    private const val IMAGE_SIZE : Int = 50

    private const val IMAGE_TOP_GAP : Int = 50

    private const val IMAGE_NAME_GAP : Int = 50

    private const val NAME_DESCRIPTION_GAP : Int = 50

    private const val DESCRIPTION_BUTTON_GAP : Int = 50

    private const val DESCRIPTION_WIDTH : Double = 0.8

    private const val DESCRIPTION_HEIGHT : Int = 400

    private val IMAGE_DISPLAYER : Canvas = Canvas(IMAGE_SIZE, IMAGE_SIZE)

    private val NAME_DISPLAYER : Label = Label()

    private val DESCRIPTION_PANE : TextScrollPane = TextScrollPane(DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT)

    private val BUTTON : TextButton = TextButton("Unlock node") { unlockNode() }

    private lateinit var node : GraphicalSkillTreeNode

    private lateinit var player : Player
    private lateinit var fighter: AbstractFighter
    private lateinit var tree : GraphicalSkillTree

    init{
        IMAGE_DISPLAYER.setX(0.5).alignTopTo(IMAGE_TOP_GAP)
        NAME_DISPLAYER.alignTopToBottom(IMAGE_DISPLAYER, IMAGE_NAME_GAP).setX(0.5)
        DESCRIPTION_PANE.alignTopToBottom(NAME_DISPLAYER, NAME_DESCRIPTION_GAP).setX(0.5)
        BUTTON.setX(0.5).alignTopToBottom(DESCRIPTION_PANE, DESCRIPTION_BUTTON_GAP)
    }

    fun display(node : GraphicalSkillTreeNode, playerStats: Player, fighterStats: AbstractFighter){
        this.node = node
        this.player = playerStats
        this.fighter = fighterStats
        loadDisplay()
    }

    fun setTree(tree : GraphicalSkillTree){
        this.tree = tree
    }

    private fun loadDisplay(){
        add(IMAGE_DISPLAYER.addGraphicAction(node.image(), IMAGE_KEY))
        add(NAME_DISPLAYER.setText(node.name()))
        DESCRIPTION_PANE.clear()
        add(DESCRIPTION_PANE.write(node.description()))
        if(!node.isObtained() && node.isAvailable()) add(BUTTON) else remove(BUTTON)
    }

    private fun unlockNode(){
        tree.find(node.name())!!.validate(player.stats(), fighter.stats())
        SkillTreeDisplayer.setTree(tree, fighter, player)
        display(tree.find(node.name())!!, player, fighter)
    }

}