package game.gameobjects

import game.fighters.AbstractFighter

interface HealingObject : GameObject {
    fun healing(healer : AbstractFighter, target : AbstractFighter) : Float
}