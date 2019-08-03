package game.gameobjects

import llayout.utilities.GraphicAction
import llayout.utilities.StringDisplay

interface GameObject{

    fun name() : String

    fun description() : Collection<StringDisplay>

    fun image() : GraphicAction

}