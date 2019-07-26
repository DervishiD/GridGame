package game.gameobjects

import game.fighters.AbstractFighter

interface SelfDamagingObject : GameObject {
    fun selfDamage(source : AbstractFighter, target : AbstractFighter)
}