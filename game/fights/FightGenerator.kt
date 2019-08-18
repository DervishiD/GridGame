package game.fights

import game.fighters.AbstractFighter
import game.player.Player
import game.world.Position
import game.world.Zone

class FightGenerator {

    companion object{

        const val NO_FIGHT : String = "NO_FIGHT"

        fun generateFightFor(zone : Zone, player : Player, name : CharSequence) : Pair<Iterable<Position>, Map<Position, AbstractFighter>>{
            return when(name){
                NO_FIGHT -> Pair(setOf(), mapOf())
                else -> TODO("Not implemented.")
            }
        }

    }

}