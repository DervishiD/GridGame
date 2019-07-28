package game.stats

import game.player.PlayerInventory

class PlayerStats(private val inventory : PlayerInventory, private var points : Int = 0) {

    companion object{

        private const val STARTER_PACK_POINTS : Int = 5

        private val STARTER_PACK_INVENTORY : PlayerInventory = PlayerInventory()

        fun starterPack() : PlayerStats = PlayerStats(STARTER_PACK_INVENTORY, STARTER_PACK_POINTS)

    }

    constructor() : this(PlayerInventory())

    fun addPoints(points : Int){
        this.points += points
    }

    fun removePoints(points : Int) = addPoints( - points)

    fun points() : Int = points

}