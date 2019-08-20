package display.guimanager

import display.specialdisplayers.FighterMenuDisplayer
import game.fighters.AbstractFighter
import game.info.ZoneData
import llayout.utilities.LObservable

object FighterMenuReceiver : EventReceiver {

    private val selectingActions : LObservable<Int> = LObservable(0)

    init{
        selectingActions.addListener { FighterMenuDisplayer.selectingActions(selectingActions.value) }
    }

    fun setData(zoneData : ZoneData, fighter : AbstractFighter){
        TODO("Not implemented.")
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
        TODO("Not implemented.")
    }

    private fun selectMove(){
        TODO("Not implemented.")
    }

    private fun selectObjects(){
        TODO("Not implemented.")
    }

    override fun escape() {
        TODO("Not implemented.")
    }

    override fun onSet() {
        TODO("Do not forget the GUI somewhere")
    }

    private fun selectPrevious(){
        selectingActions.value--
    }

    private fun selectNext(){
        selectingActions.value++
    }

}