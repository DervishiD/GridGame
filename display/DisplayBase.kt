package display

import display.scenes.FirstScene
import llayout.frame.LFrame

private const val TIMER_PERIOD : Int = 20

internal val frame : LFrame = LFrame(FirstScene).setTimerPeriod(TIMER_PERIOD).setFullscreen().setTitle("Grid Game")
