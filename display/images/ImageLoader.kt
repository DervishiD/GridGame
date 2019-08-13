package display.images

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

    private fun readImageFromName(name : String) : BufferedImage = ImageIO.read(File("$IMAGES_FOLDER_NAME$name$FILE_EXTENSION"))

    private fun graphicAction(image : BufferedImage) : GraphicAction = { g : Graphics, _ : Int, _ : Int -> g.drawImage(image, 0, 0, null) }

    fun loadSandImage() : GraphicAction = graphicAction(SAND_IMAGE)

    fun loadGrassImage() : GraphicAction = graphicAction(GRASS_IMAGE)

    fun loadWaterImage() : GraphicAction = graphicAction(WATER_IMAGE)

}