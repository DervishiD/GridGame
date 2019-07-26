package game.gameobjects

import game.world.Position

interface AOEObject : GameObject {
    fun areaOfEffect() : Collection<Position>
}