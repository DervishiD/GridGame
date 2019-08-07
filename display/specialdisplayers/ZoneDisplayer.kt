package display.specialdisplayers

import game.world.Position
import game.world.Zone
import game.world.cells.AbstractCell
import llayout.displayers.Canvas
import llayout.displayers.DisplayerContainer
import llayout.displayers.RegularGrid
import java.awt.Color
import java.awt.Graphics
import kotlin.math.ceil
import kotlin.math.floor

object ZoneDisplayer : DisplayerContainer() {

    private class Tile(private val cell : AbstractCell) : Canvas(){

        private companion object{

            private const val FRAME_SIZE : Int = 2

            private val FRAME_COLOUR : Color = Color.BLACK

            private val SELECTION_FRAME_COLOUR : Color = Color.ORANGE

        }

        init{
            addGraphicAction(cell.image())
            nPixelFrame(FRAME_SIZE, FRAME_COLOUR)
            addGraphicAction({ g : Graphics, w : Int, h : Int ->
                if(position() == cell.position()){
                    g.color = SELECTION_FRAME_COLOUR
                    g.fillRect(0, 0, w, FRAME_SIZE)
                    g.fillRect(0, 0, FRAME_SIZE, h)
                    g.fillRect(0, h - FRAME_SIZE, w, FRAME_SIZE)
                    g.fillRect(w - FRAME_SIZE, 0, FRAME_SIZE, h)
                }
            })
        }

        fun cell() : AbstractCell = cell

    }

    private const val IMAGE_SIZE : Int = 80

    private lateinit var position : Position

    private var firstLineIndex : Int = 0
    private var lastLineIndex : Int = 0
    private var firstColumnIndex : Int = 0
    private var lastColumnIndex : Int = 0

    private lateinit var zone : Zone

    private var running : Boolean = false

    private var grid : RegularGrid = RegularGrid(1, 1, 1, 1)

    init{
        addWidthListener { resetGridPosition() }
        addHeightListener { resetGridPosition() }
    }

    fun display(zone : Zone, startingPosition : Position){
        this.zone = zone
        position = startingPosition
        if(position !in zone) throw IllegalArgumentException("The starting position is not contained in the zone.")
        running = true
        reloadTiles()
    }

    fun left(){
        if(position.left() in zone){
            position = position.left()
            if(position.column() < firstColumnIndex){
                grid.moveAlongX(- grid.leftSideX() - position.column() * IMAGE_SIZE)
            }
        }
    }

    fun right(){
        if(position.right() in zone){
            position = position.right()
            if(position.column() > lastColumnIndex){
                grid.moveAlongX((1 + position.column()) * IMAGE_SIZE - width() + grid.leftSideX())
            }
        }
    }

    fun up(){
        if(position.up() in zone){
            position = position.up()
            if(position.line() < firstLineIndex){
                grid.moveAlongY( - grid.upSideY() - position.line() * IMAGE_SIZE )
            }
        }
    }

    fun down(){
        if(position.down() in zone){
            position = position.down()
            if(position.line() > lastLineIndex){
                grid.moveAlongY((1 + position.line()) * IMAGE_SIZE - height() + grid.upSideY())
            }
        }
    }

    private fun reloadTiles(){
        remove(grid)
        grid = RegularGrid(zone.numberOfLines(),
                           zone.numberOfColumns(),
                zone.numberOfLines() * IMAGE_SIZE,
                zone.numberOfColumns() * IMAGE_SIZE)
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
        resetGridX()
        resetGridY()
    }

    private fun resetGridX(){
        if(grid.width() <= width()) resetGridXWhenSmaller() else resetGridXWhenLarger()
    }

    private fun resetGridY(){
        if(grid.height() <= height()) resetGridYWhenSmaller() else resetGridYWhenLarger()
    }

    private fun resetGridXWhenSmaller(){
        grid.setX(width() / 2)
        firstColumnIndex = 0
        lastColumnIndex = zone.numberOfColumns() - 1
    }

    private fun resetGridXWhenLarger(){
        grid.setX((width() * 0.5 + grid.width() * 0.5 - ( position.column() + 0.5 ) * IMAGE_SIZE).toInt())
        if(grid.rightSideX() < width()) grid.moveAlongX(width() - grid.rightSideX())
        if(grid.leftSideX() > 0) grid.moveAlongX(- grid.leftSideX())
        firstColumnIndex = ceil(- grid.leftSideX().toFloat() / IMAGE_SIZE).toInt()
        lastColumnIndex = floor( ( width().toFloat() - grid.leftSideX() ) / IMAGE_SIZE ).toInt()
    }

    private fun resetGridYWhenSmaller(){
        grid.setY(height() / 2)
        firstLineIndex = 0
        lastLineIndex = zone.numberOfLines() - 1
    }

    private fun resetGridYWhenLarger(){
        grid.setY((height() * 0.5 + grid.height() * 0.5 - ( position.line() + 0.5 ) * IMAGE_SIZE).toInt())
        if(grid.downSideY() < height()) grid.moveAlongY(height() - grid.downSideY())
        if(grid.upSideY() > 0) grid.moveAlongY(- grid.upSideY())
        firstLineIndex = ceil(- grid.upSideY().toFloat() / IMAGE_SIZE).toInt()
        lastLineIndex = floor( ( height().toFloat() - grid.upSideY() ) / IMAGE_SIZE ).toInt()
    }

    private fun position() : Position = position

    override fun updateRelativeValues(frameWidth: Int, frameHeight: Int) {
        super.updateRelativeValues(frameWidth, frameHeight)
        resetGridPosition()
    }

}