package display.guimanager

import display.specialdisplayers.FighterMenuDisplayer
import game.eventhandler.KeyEventHandler
import game.fighters.AbstractFighter
import game.info.ZoneData
import llayout.utilities.LObservable

object FighterActionSelectorReceiver : EventReceiver {

    private val selectedAction : LObservable<Int> = LObservable(0)

    private lateinit var zoneData : ZoneData

    private lateinit var fighter : AbstractFighter

    init{
        selectedAction.addListener { FighterMenuDisplayer.selectingActions(selectedAction.value) }
    }

    fun setData(zoneData : ZoneData, fighter : AbstractFighter){
        this.zoneData = zoneData
        this.fighter = fighter
    }

    override fun up() {
        TODO("Not implemented.")
    }

    override fun down() {
        TODO("Not implemented.")
    }

    override fun left() {
        TODO("Not implemented.")
    }

    override fun right() {
        TODO("Not implemented.")
    }

    override fun select() {
        TODO("Not implemented.")
    }

    override fun escape() {
        GameGUIManager.backToDefault()
        FighterMenuReceiver.setData(zoneData, fighter)
        KeyEventHandler.setReceiver(FighterMenuReceiver)
        GameGUIManager.displayFighterMenu(fighter)
    }

    override fun onSet() {
        TODO("Not implemented.")
    }

}