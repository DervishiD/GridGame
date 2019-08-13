package game.fighters

import game.fighters.action.AOEActionSet
import game.fighters.action.FighterAction
import game.fighters.action.FighterActionSet
import game.fighters.action.InteractionEffectType
import game.skilltree.GraphicalSkillTree
import game.stats.FighterModifier
import game.stats.FighterStats
import game.world.cells.AbstractCell
import game.world.cells.CellType
import game.world.defaults.EmptyCell
import llayout.utilities.GraphicAction
import llayout.utilities.StringDisplay
import java.awt.Graphics

class TestFighter(name : String = "NO_NAME") : AbstractFighter(name) {
    
    private companion object{
        private const val DEFAULT_MAX_HEALTH : Float = 20.0f
        private const val DEFAULT_MOVING_DISTANCE : Float = 2.0f
        private val NEXT_LEVEL_REQUIREMENT_FUNCTION : (Int) -> Int = { i -> i }
        private val MAX_HEALTH_LEVEL_FUNCTION : (Int) -> Float = { _ -> 1f }
        private val MOVING_DISTANCE_LEVEL_FUNCTION : (Int) -> Float = { _ -> 0.1f }
        private val FIGHTER_ACTIONS : FighterActionSet = FighterActionSet(object : FighterAction{
            override fun act(actor: AbstractFighter, target: AbstractFighter) {}
            override fun image(): GraphicAction = { g : Graphics, w : Int, h : Int ->
                g.color = java.awt.Color(0, 120, 0)
                g.fillPolygon(intArrayOf(0, w/2, w), intArrayOf(h, 0, h), 3)
            }
            override fun description(): Collection<StringDisplay> = setOf(StringDisplay("Test action that does nothing"))
            override fun isAvailable(actor: AbstractFighter, target: AbstractFighter): Boolean = true
            override fun name(): CharSequence = "Test action"
            override fun type() : InteractionEffectType = InteractionEffectType.TEST_A
        })
        private val AOE_ACTIONS : AOEActionSet = AOEActionSet()
        private val ACTIVE_MODIFIERS : FighterModifier = FighterModifier()
        private val PASSIVE_MODIFIERS : FighterModifier = FighterModifier()
    }

    override var stats : FighterStats = FighterStats(0, DEFAULT_MAX_HEALTH, DEFAULT_MAX_HEALTH, DEFAULT_MOVING_DISTANCE,
        NEXT_LEVEL_REQUIREMENT_FUNCTION, MAX_HEALTH_LEVEL_FUNCTION, MOVING_DISTANCE_LEVEL_FUNCTION, FIGHTER_ACTIONS, AOE_ACTIONS,
        ACTIVE_MODIFIERS, PASSIVE_MODIFIERS)

    private var currentCell : AbstractCell = EmptyCell

    override var graphicalSkillTree: GraphicalSkillTree = GraphicalSkillTree.loadFromTreeName("testTree")

    override fun takeDamage(damage: Float) {
        stats.damage(damage)
        deathCheck()
    }

    private fun deathCheck(){
        if(isDead()) println("The test fighter is dead")
    }

    override fun isImmuneToDamage(): Boolean {
        return isDead()
    }

    override fun canBeHealed(): Boolean {
        return true
    }

    override fun heal(health: Float) = stats.heal(health)

    override fun currentCell(): AbstractCell = currentCell

    override fun canStepOn(cell: AbstractCell): Boolean {
        return cell.cellType() != CellType.WATER
    }

    override fun image(): GraphicAction {
        return { g : Graphics, w : Int, h : Int ->
            g.color = java.awt.Color.BLUE
            g.drawRect(0, 0, w-1, h-1)
            g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h, h, 0), 3)
        }
    }

    override fun type(): CharSequence = "testFighter"

}