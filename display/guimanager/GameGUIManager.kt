package display.guimanager

import display.frame
import display.scenes.GameMenuScene
import display.scenes.GameScene
import display.specialdisplayers.ZoneDisplayer
import game.eventhandler.KeyEventHandler
import game.eventhandler.NoEventReceiver
import game.info.ZoneInfo

object GameGUIManager {

    fun toZoneDisplayer(zoneInfo : ZoneInfo){
        frame.setScene(GameScene)
        GameScene.load(zoneInfo)
    }

    fun moveZoneDisplayerUp() = ZoneDisplayer.up()

    fun moveZoneDisplayerDown() = ZoneDisplayer.down()

    fun moveZoneDisplayerLeft() = ZoneDisplayer.left()

    fun moveZoneDisplayerRight() = ZoneDisplayer.right()

    fun toGameMenu(zoneInfo : ZoneInfo){
        KeyEventHandler.setReceiver(NoEventReceiver)
        GameMenuScene.show(zoneInfo)
    }

}