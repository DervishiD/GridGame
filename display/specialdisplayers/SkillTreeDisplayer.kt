package display.specialdisplayers

import game.skilltree.GraphicalSkillTree
import llayout.displayers.ContainerCanvas

object SkillTreeDisplayer : ContainerCanvas() {

    private var tree : GraphicalSkillTree = GraphicalSkillTree()

    fun setTree(tree : GraphicalSkillTree){
        this.tree = tree
        resetDisplay()
    }

    private fun resetDisplay(){
        TODO("Not implemented.")
    }

}