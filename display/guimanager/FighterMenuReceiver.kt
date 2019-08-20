package display.guimanager

import display.specialdisplayers.FighterMenuDisplayer
import game.eventhandler.KeyEventHandler
import game.fighters.AbstractFighter
import game.info.ZoneData
import llayout.utilities.LObservable

object FighterMenuReceiver : EventReceiver {

    private val selectingActions : LObservable<Int> = LObservable(0)

    private lateinit var zoneData : ZoneData

    private lateinit var fighter : AbstractFighter

    init{
        selectingActions.addListener { FighterMenuDisplayer.selectingActions(selectingActions.value) }
    }

    fun setData(zoneData : ZoneData, fighter : AbstractFighter){
        this.zoneData = zoneData
        this.fighter = fighter
    }

    override fun up() {}

    override fun down() {}

    override fun left() { selectPrevious() }

    override fun right() { selectNext() }

    override fun select() = when(selectingActions.value % 3){
        0 -> selectActions()
        1 -> selectMove()
        else -> selectObjects()
    }

    private fun selectActions(){
        FighterActionSelectorReceiver.setData(zoneData, fighter)
        KeyEventHandler.setReceiver(FighterActionSelectorReceiver)
    }

    private fun selectMove(){
        TODO("Not implemented.")
    }

    private fun selectObjects(){
        ObjectSelectionReceiver.setData(zoneData, fighter)
    }

    override fun escape() {
        KeyEventHandler.setReceiver(zoneData)
        GameGUIManager.backToDefault()
    }

    override fun onSet() {
        GameGUIManager.backToDefault()
        GameGUIManager.displayFighterMenu(fighter)
    }

    private fun selectPrevious(){
        selectingActions.value--
    }

    private fun selectNext(){
        selectingActions.value++
    }

}