package game.skilltree

import game.stats.FighterStats
import game.stats.PlayerStats
import llayout.utilities.Text

class SkillTreeNode(private val parent : SkillTreeNode?,
                             private val requirementFunction : (PlayerStats, FighterStats, SkillTree) -> Boolean,
                             private val effect : (PlayerStats, FighterStats) -> Unit,
                             private val name : CharSequence,
                             private val description : Text,
                             private val tree : SkillTree) {

    private var obtained : Boolean = false

    private val children : MutableCollection<SkillTreeNode> = mutableSetOf()

    init{
        parent?.addChild(this)
    }

    fun takeEffect(playerStats : PlayerStats, fighterStats: FighterStats) = effect(playerStats, fighterStats)

    fun requirementsFulfilled(playerStats: PlayerStats, fighterStats: FighterStats) : Boolean = requirementFunction(playerStats, fighterStats, tree)

    fun isObtained() : Boolean = obtained

    fun isAvailable() : Boolean = parent == null || parent.isObtained()

    fun isObtainable(playerStats: PlayerStats, fighterStats: FighterStats) : Boolean = isAvailable() && requirementsFulfilled(playerStats, fighterStats)

    fun description() : Text = description

    fun name() : CharSequence = name

    internal fun find(name : CharSequence) : SkillTreeNode?{
        if(this.name == name) return this
        for(child : SkillTreeNode in children){
            val result = child.find(name)
            if(result != null) return result
        }
        return null
    }

    fun validate(playerStats: PlayerStats, fighterStats: FighterStats){
        obtain()
        takeEffect(playerStats, fighterStats)
    }

    private fun obtain(){
        obtained = true
    }

    private fun addChild(child : SkillTreeNode){
        children.add(child)
    }

    fun children() : MutableCollection<SkillTreeNode> = children

}