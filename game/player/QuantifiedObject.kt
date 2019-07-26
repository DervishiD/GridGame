package game.player

import game.gameobjects.GameObject

class QuantifiedObject(private val objectType : GameObject, private var quantity : Int) {

    constructor(objectType: GameObject) : this(objectType, 1)

    fun type() : GameObject = objectType

    fun quantity() : Int = quantity

    operator fun inc() : QuantifiedObject {
        plus(1)
        return this
    }

    operator fun dec() : QuantifiedObject {
        minus(1)
        return this
    }

    operator fun plus(quantity : Int){
        this.quantity += quantity
        checkQuantity()
    }

    operator fun plus(other : QuantifiedObject): QuantifiedObject {
        if(this == other) plus(other.quantity()) else throw IllegalArgumentException("Different QuantifiableObjects")
        return this
    }

    operator fun minus(quantity : Int) = plus( - quantity)

    operator fun minus(other : QuantifiedObject) : QuantifiedObject {
        if(this == other) minus(other.quantity()) else throw IllegalArgumentException("Different QuantifiableObjects")
        return this
    }

    override operator fun equals(other : Any?) : Boolean = other is QuantifiedObject && type() == other.type()

    override fun hashCode(): Int = type().hashCode()

    private fun checkQuantity(){
        if(quantity < 0) quantity = 0
    }

}