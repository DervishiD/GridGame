package display.images

import game.fighters.AbstractFighter
import game.fighters.TestFighter
import game.player.Player
import game.world.cells.AbstractCell
import game.world.cells.CellComponent
import game.world.cells.SandCell
import game.world.cells.WaterCell
import llayout.utilities.GraphicAction
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ImageLoader {

    private val IMAGES_FOLDER_NAME : String = "src${File.separator}display${File.separator}images${File.separator}files${File.separator}"
    private const val FILE_EXTENSION : String = ".jpg"

    private const val SAND_NAME : String = "sand"
    private const val GRASS_NAME : String = "grass"
    private const val WATER_NAME : String = "water"

    private val SAND_IMAGE : BufferedImage by lazy { readImageFromName(SAND_NAME) }
    private val GRASS_IMAGE : BufferedImage by lazy { readImageFromName(GRASS_NAME) }
    private val WATER_IMAGE : BufferedImage by lazy { readImageFromName(WATER_NAME) }

    private val NO_IMAGE : GraphicAction = { _ : Graphics, _ : Int, _ : Int ->  }

    private fun readImageFromName(name : String) : BufferedImage = ImageIO.read(File("$IMAGES_FOLDER_NAME$name$FILE_EXTENSION"))

    private fun graphicAction(image : BufferedImage) : GraphicAction = { g : Graphics, _ : Int, _ : Int -> g.drawImage(image, 0, 0, null) }

    private fun playerFacingUp() : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = java.awt.Color.BLACK
        g.fillPolygon(intArrayOf(w/4, w/2, 3*w/4), intArrayOf(2*h/3, h/3, 2*h/3), 3)
    }

    private fun playerFacingDown() : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = java.awt.Color.BLACK
        g.fillPolygon(intArrayOf(w/4, w/2, 3*w/4), intArrayOf(h/3, 2*h/3, h/3), 3)
    }

    private fun playerFacingLeft() : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = java.awt.Color.BLACK
        g.fillPolygon(intArrayOf(w/3, 2*w/3, 2*w/3), intArrayOf(h/2, h/4, 3*h/4), 3)
    }

    private fun playerFacingRight() : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = java.awt.Color.BLACK
        g.fillPolygon(intArrayOf(w/3, w/3, 2*w/3), intArrayOf(h/4, 3*h/4, h/2), 3)
    }

    private fun imageOf(player : Player) : GraphicAction{
        return when{
            player.isFacingUp() -> playerFacingUp()
            player.isFacingDown() -> playerFacingDown()
            player.isFacingLeft() -> playerFacingLeft()
            player.isFacingRight() -> playerFacingRight()
            else -> throw IllegalStateException("The player is not facing anywhere.")
        }
    }

    private fun sandCellImage() : GraphicAction = graphicAction(SAND_IMAGE)

    private fun waterCellImage() : GraphicAction = graphicAction(WATER_IMAGE)

    fun imageOf(cell : AbstractCell) : GraphicAction = when(cell.cellName()){
        WaterCell.cellName() -> waterCellImage()
        SandCell.cellName() -> sandCellImage()
        else -> NO_IMAGE
    }

    fun imageOf(fighter : AbstractFighter) : GraphicAction = when(fighter.componentID()){
        TestFighter.cellComponentID() -> imageOf(fighter as TestFighter)
        else -> NO_IMAGE
    }

    fun imageOf(fighter : TestFighter) : GraphicAction = { g : Graphics, w : Int, h : Int ->
        g.color = java.awt.Color.BLUE
        g.drawRect(0, 0, w-1, h-1)
        g.fillPolygon(intArrayOf(0, w, w), intArrayOf(h, h, 0), 3)
    }

    fun imageOf(component : CellComponent) : GraphicAction = when(component.componentID()){
        TestFighter.cellComponentID() -> imageOf(component as TestFighter)
        Player.cellComponentID() -> imageOf(component as Player)
        else -> NO_IMAGE
    }

}