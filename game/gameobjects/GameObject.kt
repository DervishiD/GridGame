package game.gameobjects

import llayout.utilities.GraphicAction

interface GameObject{

    fun name() : CharSequence

    fun description() : CharSequence

    fun image() : GraphicAction

}