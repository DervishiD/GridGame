package display.scenes

import display.specialdisplayers.ZoneDisplayer
import game.info.ZoneInfo
import llayout.frame.LScene

object GameScene : LScene() {

    init{
        add(ZoneDisplayer.setWidth(1.0).setHeight(1.0).setX(0.5).setY(0.5))
    }

    fun load(zoneInfo : ZoneInfo){
        ZoneDisplayer.display(zoneInfo.zone(), zoneInfo.playerPosition())
    }

}