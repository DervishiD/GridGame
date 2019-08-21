package display.scenes

import display.specialdisplayers.FighterMenuDisplayer
import display.specialdisplayers.InGameInformation
import display.specialdisplayers.ZoneDisplayer
import game.fighters.AbstractFighter
import game.info.ZoneData
import llayout.frame.LScene

object GameScene : LScene() {

    private const val ZONE_DISPLAYER_WIDTH : Double = 0.8
    private const val INFO_WIDTH : Double = 1.0 - ZONE_DISPLAYER_WIDTH

    private const val ZONE_DISPLAYER_X : Double = INFO_WIDTH + ZONE_DISPLAYER_WIDTH / 2
    private const val INFO_X : Double = INFO_WIDTH / 2

    private const val FIGHTER_MENU_DISPLAYER_X : Double = 0.5
    private const val FIGHTER_MENU_DISPLAYER_Y : Double = 0.5

    init{
        add(ZoneDisplayer.setWidth(ZONE_DISPLAYER_WIDTH).setHeight(1.0).setX(ZONE_DISPLAYER_X).setY(0.5))
        add(InGameInformation.setWidth(INFO_WIDTH).setHeight(1.0).setX(INFO_X).setY(0.5))
    }

    fun load(zoneData : ZoneData){
        ZoneDisplayer.display(zoneData.zone(), zoneData.hoveredPosition())
    }

    fun displayFighterMenu(fighter : AbstractFighter){
        FighterMenuDisplayer.loadFor(fighter)
        add(FighterMenuDisplayer.setX(FIGHTER_MENU_DISPLAYER_X).setY(FIGHTER_MENU_DISPLAYER_Y))
    }

    fun removeAdditionalMenus(){
        remove(FighterMenuDisplayer)
        //TODO
    }

}