package display.specialdisplayers

import game.fighters.AbstractFighter
import game.player.Player
import game.skilltree.GraphicalSkillTree
import game.skilltree.GraphicalSkillTreeNode
import game.stats.FighterStats
import game.stats.PlayerStats
import llayout.displayers.ContainerCanvas
import llayout.displayers.ImageButton
import llayout.utilities.GraphicAction
import java.awt.Graphics

object SkillTreeDisplayer : ContainerCanvas() {

    private const val NODE_IMAGE_SIZE : Int = 50

    private val BACKGROUND : GraphicAction = { g : Graphics, w : Int, h : Int ->  }

    private class NodeButton(private val node : GraphicalSkillTreeNode) : ImageButton(NODE_IMAGE_SIZE, NODE_IMAGE_SIZE, node.image(), {}){

        init{
            setX(node.x() + zeroX)
            setY(node.y() + zeroY)
            setOnMouseReleasedAction { displayNodeInformation() }
        }

        private fun displayNodeInformation(){
            TODO("Not implemented.")
        }

        fun takeEffect(playerStats : PlayerStats, fighterStats: FighterStats){
            node.takeEffect(playerStats, fighterStats)
            reloadNodeButtons()
        }

        internal fun updatePosition(){
            setX(node.x() + zeroX)
            setY(node.y() + zeroY)
        }

    }

    private var tree : GraphicalSkillTree = GraphicalSkillTree()
    private lateinit var fighter : AbstractFighter
    private lateinit var player : Player

    private var buttons : MutableCollection<NodeButton> = mutableSetOf()

    private var zeroX : Int = 0
    private var zeroY : Int = 0

    private var lastMouseX : Int = 0
    private var lastMouseY : Int = 0

    init{
        setOnMousePressedAction { e -> updateMousePosition(e.x, e.y) }
        setOnMouseDraggedAction { e -> drag(e.x, e.y) }
        addGraphicAction(BACKGROUND)
        addGraphicAction({ g : Graphics, _ : Int, _ : Int -> drawTree(g) })
    }

    fun setTree(fighter : AbstractFighter, player: Player){
        this.tree = fighter.skillTree()
        this.fighter = fighter
        this.player = player
        reloadNodeButtons()
        resize()
    }

    private fun updateMousePosition(x : Int, y : Int){
        lastMouseX = x
        lastMouseY = y
    }

    private fun drag(x : Int, y : Int){
        moveDisplayBy(x - lastMouseX, y - lastMouseY)
        updateMousePosition(x, y)
    }

    private fun moveDisplayBy(deltaX : Int, deltaY : Int){
        zeroX += deltaX
        zeroY += deltaY
        updateNodesPosition()
    }

    private fun updateNodesPosition(){
        for(button : NodeButton in buttons){
            button.updatePosition()
        }
    }

    private fun reloadNodeButtons(){
        removeNodeButtons()
        addNodeButtons()
    }

    private fun removeNodeButtons(){
        for(button : NodeButton in buttons){
            remove(button)
        }
        buttons.clear()
    }

    private fun addNodeButtons(){
        for(node : GraphicalSkillTreeNode in tree.rootNodes()){
            addNode(node)
        }
    }

    private fun addNode(node : GraphicalSkillTreeNode){
        if(node.isAvailable()){
            val button = NodeButton(node)
            buttons.add(button)
            add(button)
        }
        if(node.isObtained()){
            for(child : GraphicalSkillTreeNode in node.children()){
                addNode(child)
            }
        }
    }

    private fun drawTree(g : Graphics){
        //Nothing?
    }

    private fun resize(){
        var maxX = 0
        var maxY = 0
        for(b : NodeButton in buttons){
            if(b.rightSideX() > maxX) maxX = b.rightSideX()
            if(b.downSideY() > maxY) maxY = b.downSideY()
        }
        setWidth(maxX)
        setHeight(maxY)
    }

}