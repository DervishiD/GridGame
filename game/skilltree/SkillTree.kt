package game.skilltree

class SkillTree(private val rootNodes : MutableCollection<SkillTreeNode> = mutableSetOf()) {

    fun addRootNode(node : SkillTreeNode){
        rootNodes.add(node)
    }

    fun find(name : CharSequence) : SkillTreeNode?{
        for(root : SkillTreeNode in rootNodes){
            val result = root.find(name)
            if(result != null) return result
        }
        return null
    }

    fun nodeIsObtained(name: CharSequence) : Boolean{
        val node : SkillTreeNode? = find(name)
        return node != null && node.isObtained()
    }

    fun nodeIsAvailable(name : CharSequence) : Boolean{
        val node : SkillTreeNode? = find(name)
        return node != null && node.isAvailable()
    }

}