package game.fighters.action

class FighterActionSet(private val actions : MutableList<FighterAction> = mutableListOf()) : Iterable<FighterAction> {

    constructor(vararg actions : FighterAction) : this(actions.asList().toMutableList())

    fun size() : Int = actions.size

    fun add(action : FighterAction) = actions.add(action)

    fun actions() : MutableList<FighterAction> = actions

    override fun iterator(): Iterator<FighterAction> = actions.iterator()

}