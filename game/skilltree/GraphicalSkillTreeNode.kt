package game.skilltree

import game.stats.FighterStats
import game.stats.PlayerStats
import llayout.utilities.GraphicAction
import llayout.utilities.Text

class GraphicalSkillTreeNode(private val x : Int,
                             private val y : Int,
                             private val image : GraphicAction,
                             private val obtainedImage : GraphicAction,
                             private val node : SkillTreeNode,
                             private val parent : GraphicalSkillTreeNode?  = null) {

    private val children : MutableCollection<GraphicalSkillTreeNode> = mutableSetOf()

    init{
        parent?.addChild(this)
    }

    fun image() : GraphicAction = if(isObtained()) obtainedImage else image

    fun isObtained() : Boolean = node.isObtained()

    fun isAvailable() : Boolean = node.isAvailable()

    fun isObtainable(playerStats: PlayerStats, fighterStats: FighterStats) : Boolean = node.isObtainable(playerStats, fighterStats)

    fun name() : CharSequence = node.name()

    fun description() : Text = node.description()

    fun x() : Int = x

    fun y() : Int = y

    fun addChild(child : GraphicalSkillTreeNode){
        children.add(child)
    }

    fun children() : MutableCollection<GraphicalSkillTreeNode> = children

    internal fun find(name : CharSequence) : GraphicalSkillTreeNode?{
        if(name() == name) return this
        for(child : GraphicalSkillTreeNode in children){
            val result = child.find(name)
            if(result != null) return result
        }
        return null
    }

}