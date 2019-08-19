package game.info

import game.fighters.AbstractFighter

class FighterData(private val fighter : AbstractFighter,
                  private var canMove : Boolean,
                  private var canAct : Boolean) {

    fun fighter() : AbstractFighter = fighter

    fun canMove() : Boolean = canMove

    fun canAct() : Boolean = canAct

    fun cantMove() : Boolean = !canMove()

    fun cantAct() : Boolean = !canAct()

    fun isBlocked() : Boolean = cantMove() && cantAct()

}