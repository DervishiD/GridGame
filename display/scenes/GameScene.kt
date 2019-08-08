package display.scenes

import display.specialdisplayers.ZoneDisplayer
import game.world.ZoneInfo
import llayout.frame.LScene

object GameScene : LScene() {

    init{
        add(ZoneDisplayer.setWidth(1.0).setHeight(1.0).setX(0.5).setY(0.5))
    }

    fun load(zoneInfo : ZoneInfo){
        /*
         * Manage to record key strokes somehow
         * -- Every object that appears on the pane must have a key pressed/typed action that calls a detector
         */
        ZoneDisplayer.display(zoneInfo.zone(), zoneInfo.playerPosition())
        TODO("Not implemented.")
    }

}