package game.interactions

import game.fighters.AbstractFighter
import game.gameobjects.weapons.StandardWeapon

abstract class StandardAttack(attacker : AbstractFighter, defender : AbstractFighter, weapon : StandardWeapon)
    : FighterInteraction<AbstractFighter, AbstractFighter, StandardWeapon>(attacker, defender, weapon) {

    override fun act() = target().takeDamage(gameObject().damageDealt(source(), target()))

}