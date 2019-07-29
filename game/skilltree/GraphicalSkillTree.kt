package game.skilltree

class GraphicalSkillTree(private val rootNodes : MutableCollection<GraphicalSkillTreeNode> = mutableSetOf()) {

    fun addRootNode(node : GraphicalSkillTreeNode){
        rootNodes.add(node)
    }

    fun find(name : CharSequence) : GraphicalSkillTreeNode?{
        for(root : GraphicalSkillTreeNode in rootNodes){
            val result = root.find(name)
            if(result != null) return result
        }
        return null
    }

    fun nodeIsObtained(name: CharSequence) : Boolean{
        val node : GraphicalSkillTreeNode? = find(name)
        return node != null && node.isObtained()
    }

    fun nodeIsAvailable(name : CharSequence) : Boolean{
        val node : GraphicalSkillTreeNode? = find(name)
        return node != null && node.isAvailable()
    }

}