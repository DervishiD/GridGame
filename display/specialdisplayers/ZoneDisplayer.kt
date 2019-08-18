package display.specialdisplayers

import display.images.ImageLoader
import game.eventhandler.KeyEventHandler
import game.world.Position
import game.world.Zone
import game.world.cells.AbstractCell
import llayout.displayers.Canvas
import llayout.displayers.DisplayerContainer
import llayout.displayers.RegularGrid
import llayout.utilities.plus
import java.awt.Color
import java.awt.Graphics
import java.awt.Robot
import java.awt.event.InputEvent

object ZoneDisplayer : DisplayerContainer() {

    private class Tile(private val cell : AbstractCell) : Canvas(){

        private companion object{

            private const val FRAME_SIZE : Int = 1

            private const val SELECTION_FRAME_SIZE : Int = 4

            private val FRAME_COLOUR : Color = Color.BLACK

            private val SELECTION_FRAME_COLOUR : Color = Color.RED

            private const val COMPONENT_KEY : Int = 2231

        }

        init{
            addGraphicAction(ImageLoader.imageOf(cell) + ImageLoader.imageOf(cell.component()), COMPONENT_KEY)
            cell.addComponentListener { addGraphicAction(ImageLoader.imageOf(cell) + ImageLoader.imageOf(cell.component()), COMPONENT_KEY) }
            nPixelFrame(FRAME_SIZE, FRAME_COLOUR)
            addGraphicAction({ g : Graphics, w : Int, h : Int ->
                if(position() == cell.position()){
                    g.color = SELECTION_FRAME_COLOUR
                    g.fillRect(0, 0, w, SELECTION_FRAME_SIZE)
                    g.fillRect(0, 0, SELECTION_FRAME_SIZE, h)
                    g.fillRect(0, h - SELECTION_FRAME_SIZE, w, SELECTION_FRAME_SIZE)
                    g.fillRect(w - SELECTION_FRAME_SIZE, 0, SELECTION_FRAME_SIZE, h)
                }
            })
        }

    }

    private const val IMAGE_SIZE : Int = 100

    private lateinit var position : Position

    private lateinit var zone : Zone

    private var running : Boolean = false

    private var grid : RegularGrid = RegularGrid(1, 1, 1, 1)

    init{
        addWidthListener { resetGridPosition() }
        addHeightListener { resetGridPosition() }
        setOnKeyPressedAction { e -> KeyEventHandler.handlePress(e.keyCode) }
    }

    fun display(zone : Zone, startingPosition : Position){
        if(startingPosition !in zone) throw IllegalArgumentException("The starting position is not contained in the zone.")
        this.zone = zone
        position = startingPosition
        running = true
        reloadTiles()
        forceGainFocus()
    }

    fun left(){
        if(position.left() in zone){
            position = position.left()
            resetGridPosition()
        }
    }

    fun right(){
        if(position.right() in zone){
            position = position.right()
            resetGridPosition()
        }
    }

    fun up(){
        if(position.up() in zone){
            position = position.up()
            resetGridPosition()
        }
    }

    fun down(){
        if(position.down() in zone){
            position = position.down()
            resetGridPosition()
        }
    }

    private fun reloadTiles(){
        remove(grid)
        grid = RegularGrid(zone.numberOfLines(),
                           zone.numberOfColumns(),
                zone.numberOfColumns() * IMAGE_SIZE,
                zone.numberOfLines() * IMAGE_SIZE)
        add(grid)
        refillGrid()
        resetGridPosition()
    }

    private fun refillGrid(){
        for(line : Int in 0 until zone.numberOfLines())
            for(column : Int in 0 until zone.numberOfColumns() )
                grid[line, column] = Tile(zone.cellAt(line, column))
    }

    private fun resetGridPosition(){
        if(running){
            resetGridX()
            resetGridY()
        }
    }

    private fun resetGridX(){
        if(grid.width() <= width()) resetGridXWhenSmaller() else resetGridXWhenLarger()
    }

    private fun resetGridY(){
        if(grid.height() <= height()) resetGridYWhenSmaller() else resetGridYWhenLarger()
    }

    private fun resetGridXWhenSmaller(){
        grid.setX(width() / 2)
    }

    private fun resetGridXWhenLarger(){
        grid.setX(( ( width() + grid.width() ) * 0.5 - ( position.column() + 0.5 ) * IMAGE_SIZE).toInt())
        if(grid.rightSideX() < width()) grid.moveAlongX(width() - grid.rightSideX())
        if(grid.leftSideX() > 0) grid.moveAlongX(- grid.leftSideX())
    }

    private fun resetGridYWhenSmaller(){
        grid.setY(height() / 2)
    }

    private fun resetGridYWhenLarger(){
        grid.setY(( ( height() + grid.height() ) * 0.5 - ( position.line() + 0.5 ) * IMAGE_SIZE).toInt())
        if(grid.downSideY() < height()) grid.moveAlongY(height() - grid.downSideY())
        if(grid.upSideY() > 0) grid.moveAlongY(- grid.upSideY())
    }

    private fun position() : Position = position

    private fun forceGainFocus(){
        val robot = Robot()
        robot.mouseMove(width() / 2, height() / 2)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        resetGridPosition()
    }

}