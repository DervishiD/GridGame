package display.guimanager

interface EventReceiver {

    fun up()

    fun down()

    fun left()

    fun right()

    fun select()

    fun escape()

    fun onSet()

}