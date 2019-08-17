package game.eventhandler

import display.guimanager.EventReceiver

object NoEventReceiver : EventReceiver {

    override fun up() {}

    override fun down() {}

    override fun left() {}

    override fun right() {}

    override fun select() {}

    override fun escape() {}

    override fun onSet() {}

}