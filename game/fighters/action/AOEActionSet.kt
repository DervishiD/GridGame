package game.fighters.action

class AOEActionSet(private val actions : MutableList<AOEAction> = mutableListOf()) : Iterable<AOEAction> {

    fun size() : Int = actions.size

    fun add(action : AOEAction) = actions.add(action)

    fun actions() : MutableList<AOEAction> = actions

    override fun iterator(): Iterator<AOEAction> = actions.iterator()

}