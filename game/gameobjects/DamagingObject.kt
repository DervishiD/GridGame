package game.gameobjects

import game.fighters.AbstractFighter

interface DamagingObject : GameObject {

    fun damageDealt(attacker : AbstractFighter, target : AbstractFighter) : Float

}