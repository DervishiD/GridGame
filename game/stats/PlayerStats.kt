package game.stats

import game.player.PlayerInventory

class PlayerStats(private val inventory : PlayerInventory, private var points : Int = 0) {

    companion object{

        private const val STARTER_PACK_POINTS : Int = 5

        private fun starterPackInventory() : PlayerInventory{
            return PlayerInventory()
        }

        fun starterPack() : PlayerStats = PlayerStats(starterPackInventory(), STARTER_PACK_POINTS)

    }

    constructor() : this(PlayerInventory())

    fun addPoints(points : Int){
        this.points += points
    }

    fun removePoints(points : Int) = addPoints( - points)

    fun points() : Int = points

    fun inventory() : PlayerInventory = inventory

}