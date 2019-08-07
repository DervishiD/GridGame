package game.world

import game.fighters.AbstractFighter
import game.player.Player

data class ZoneInfo(private val isANewZone : Boolean,
               private val player : Player,
               private val zone : Zone,
               private val isFighting : Boolean,
               private val playerFighterPositions : Iterable<Position>,
               private val ennemies : Map<Position, AbstractFighter>,
               private val playerPosition: Position) {

    fun player() : Player = player

    fun isANewZone() : Boolean = isANewZone

    fun zone() : Zone = zone

    fun isFighting() : Boolean = isFighting

    fun playerFighterPositions() : Iterable<Position> = playerFighterPositions

    fun ennemies() : Map<Position, AbstractFighter> = ennemies

    fun playerPosition() : Position = playerPosition

}