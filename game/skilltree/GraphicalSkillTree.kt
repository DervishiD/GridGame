package game.skilltree

import game.stats.FighterStats
import game.stats.PlayerStats
import llayout.utilities.GraphicAction
import java.awt.Graphics
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class GraphicalSkillTree(private val rootNodes : MutableCollection<GraphicalSkillTreeNode> = mutableSetOf()) {

    companion object{

        /*
         * name parentName requirementFunction effect description x y image obtainedImage
         */

        private const val FILE_EXTENSION : String = ".txt"

        private const val TREE_FOLDER_NAME : String = "skilltrees"

        private const val SOURCE_FOLDER_NAME : String = "src"

        private const val DATA_SEPARATOR : String = "|"

        private const val NO_PARENT_INDICATOR : String = "NO_PARENT_ROOT_NODE"

        private const val NAME_INDEX : Int = 0
        private const val PARENT_INDEX : Int = 1
        private const val REQUIREMENT_INDEX : Int = 2
        private const val EFFECT_INDEX : Int = 3
        private const val DESCRIPTION_INDEX : Int = 4
        private const val X_INDEX : Int = 5
        private const val Y_INDEX : Int = 6
        private const val IMAGE_INDEX : Int = 7
        private const val OBTAINED_IMAGE_INDEX : Int = 8
        private const val OBTAINED_INDEX : Int = 9

        private fun treeFolderName() : String = SOURCE_FOLDER_NAME + File.separator + TREE_FOLDER_NAME + File.separator

        fun loadFromTreeName(name : String) : GraphicalSkillTree = loadFromFileName(treeFolderName() + name + FILE_EXTENSION)

        private fun loadFromFileName(fileName : String) : GraphicalSkillTree = loadFromReader(BufferedReader(FileReader(File(fileName))))

        private fun loadFromReader(reader : BufferedReader) : GraphicalSkillTree{
            val data : MutableList<List<String>> = mutableListOf()
            for(line in reader.lines()){
                data.add(line.split(DATA_SEPARATOR))
            }
            return loadFromData(data)
        }

        private fun loadFromData(data : MutableList<List<String>>) : GraphicalSkillTree{
            val result = GraphicalSkillTree()
            val skillTree = SkillTree()
            return addRoots(result, skillTree, data)
        }

        private fun addRoots(result : GraphicalSkillTree, tree : SkillTree, data : MutableList<List<String>>) : GraphicalSkillTree{
            for(i : Int in data.size - 1 downTo 0){
                if(isRoot(data[i])){
                    addRootNode(result, tree, data[i])
                    data.removeAt(i)
                }
            }
            return addChildren(result, tree, data)
        }

        private fun addChildren(result : GraphicalSkillTree, tree : SkillTree, data : MutableList<List<String>>) : GraphicalSkillTree{
            while(data.isNotEmpty()){
                for(i : Int in data.size - 1 downTo 0){
                    if(treeContainsParent(result, data[i])){
                        addChild(result, tree, data[i])
                        data.removeAt(i)
                    }
                }
            }
            return result
        }

        private fun addChild(result : GraphicalSkillTree, tree : SkillTree, nodeData : List<String>){
            graphicalNodeFromData(result, nodeFromData(tree, nodeData), nodeData)
        }

        private fun treeContainsParent(result : GraphicalSkillTree, nodeData : List<String>) : Boolean = result.find(nodeData[PARENT_INDEX]) != null

        private fun addRootNode(result : GraphicalSkillTree, tree : SkillTree, nodeData : List<String>){
            graphicalNodeFromData(result, nodeFromData(tree, nodeData), nodeData)
        }

        private fun nodeFromData(tree : SkillTree, nodeData : List<String>) : SkillTreeNode{
            val result = SkillTreeNode(
                if(isRoot(nodeData)) null else tree.find(nodeData[PARENT_INDEX]),
                requirementFunctionFromName(nodeData[REQUIREMENT_INDEX]),
                effectFunctionFromName(nodeData[EFFECT_INDEX]),
                nodeData[NAME_INDEX],
                nodeData[DESCRIPTION_INDEX],
                tree,
                nodeData[OBTAINED_INDEX].toBoolean()
            )
            tree.addRootNode(result)
            return result
        }

        private fun graphicalNodeFromData(result : GraphicalSkillTree, node : SkillTreeNode, data : List<String>){
            result.addRootNode(GraphicalSkillTreeNode(
                data[X_INDEX].toInt(),
                data[Y_INDEX].toInt(),
                imageFromName(data[IMAGE_INDEX]),
                obtainedImageFromName(data[OBTAINED_IMAGE_INDEX]),
                node,
                if(isRoot(data)) null else result.find(data[PARENT_INDEX])
            ))
        }

        private fun isRoot(nodeData : List<String>) : Boolean = nodeData[PARENT_INDEX] == NO_PARENT_INDICATOR

        private fun requirementFunctionFromName(name : String) : (PlayerStats, FighterStats, SkillTree) -> Boolean{
            return { _ : PlayerStats, _ : FighterStats, _ : SkillTree -> true }
        }

        private fun effectFunctionFromName(name : String) : (PlayerStats, FighterStats) -> Unit{
            return { _ : PlayerStats, _ : FighterStats ->  }
        }

        private fun imageFromName(name : String) : GraphicAction{
            return { g : Graphics, w : Int, h : Int ->
                g.color = java.awt.Color.BLACK
                g.fillRect(0, 0, w, h)
                g.color = java.awt.Color.WHITE
                g.drawString(name, 0, h)
            }
        }

        private fun obtainedImageFromName(name : String) : GraphicAction{
            return { g : Graphics, w : Int, h : Int ->
                g.color = java.awt.Color(0, 160, 0)
                g.fillRect(0, 0, w, h)
                g.color = java.awt.Color.WHITE
                g.drawString(name, 0, h)
            }
        }

    }

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

    fun rootNodes() : MutableCollection<GraphicalSkillTreeNode> = rootNodes

}