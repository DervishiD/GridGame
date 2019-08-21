package display.guimanager

import display.frame
import display.scenes.GameMenuScene
import display.scenes.GameScene
import display.specialdisplayers.ZoneDisplayer
import game.eventhandler.KeyEventHandler
import game.eventhandler.NoEventReceiver
import game.fighters.AbstractFighter
import game.info.ZoneData
import game.world.cells.CellComponent

object GameGUIManager {

    fun toZoneDisplayer(zoneData : ZoneData){
        frame.setScene(GameScene)
        GameScene.load(zoneData)
    }

    fun moveZoneDisplayerUp() = ZoneDisplayer.up()

    fun moveZoneDisplayerDown() = ZoneDisplayer.down()

    fun moveZoneDisplayerLeft() = ZoneDisplayer.left()

    fun moveZoneDisplayerRight() = ZoneDisplayer.right()

    fun toGameMenu(zoneData : ZoneData){
        KeyEventHandler.setReceiver(NoEventReceiver)
        GameMenuScene.show(zoneData)
    }

    fun displayInGameInformation(component : CellComponent){
        TODO("Not implemented.")
    }

    fun displayFighterMenu(fighter : AbstractFighter){
        GameScene.displayFighterMenu(fighter)
    }

    fun backToDefault(){
        GameScene.removeAdditionalMenus()
    }

}