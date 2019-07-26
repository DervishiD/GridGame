package game.interactions

import game.fighters.AbstractFighter
import game.gameobjects.AOEObject
import game.world.Position

abstract class AOEInteraction<S : AbstractFighter, O : AOEObject>(source : S, aoeObject : O) : AbstractInteraction<S, O>(source, aoeObject){

    protected fun areaOfEffect() : Collection<Position> = gameObject().areaOfEffect()

}