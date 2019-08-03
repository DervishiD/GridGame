package game.gameobjects

import game.fighters.AbstractFighter
import llayout.utilities.GraphicAction
import llayout.utilities.StringDisplay
import java.awt.Color.BLACK
import java.awt.Color.RED
import java.awt.Graphics

object TestObject : HealingObject {

    private const val HEALING : Float = 2.0f

    override fun name(): String = "Object test"

    override fun image(): GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = BLACK
        g.drawRect(0, 0, w-1, h-1)
        g.fillOval(0, 0, w, h)
    }

    override fun description(): Collection<StringDisplay> = setOf(StringDisplay("This object is a test to see if the display works."),
        StringDisplay("It's irrelevant to the game per se.\n"),
        StringDisplay("Just a new line test.\n"),
        StringDisplay("It heals the target by $HEALING.", RED)
    )

    override fun healing(healer: AbstractFighter, target: AbstractFighter): Float = HEALING

}