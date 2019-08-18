package display.scenes

import display.specialdisplayers.ZoneDisplayer
import game.info.ZoneData
import llayout.frame.LScene

object GameScene : LScene() {

    init{
        add(ZoneDisplayer.setWidth(1.0).setHeight(1.0).setX(0.5).setY(0.5))
    }

    fun load(zoneData : ZoneData){
        ZoneDisplayer.display(zoneData.zone(), zoneData.playerPosition())
    }

}