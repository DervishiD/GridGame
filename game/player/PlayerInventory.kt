package game.player

import game.gameobjects.GameObject

class PlayerInventory : Iterable<QuantifiedObject>{

    private val objects : MutableList<QuantifiedObject> = mutableListOf()

    fun add(objects : QuantifiedObject){
        val position : Int = this.objects.indexOf(objects)
        if(position == -1){
            this.objects.add(objects)
        }else{
            this.objects[position] += objects
        }
    }

    fun add(obj : GameObject) = add(QuantifiedObject(obj))

    fun remove(objects : QuantifiedObject){
        val position : Int = this.objects.indexOf(objects)
        if(position != -1){
            this.objects[position] -= objects
        }
    }

    fun remove(obj : GameObject) = remove(QuantifiedObject(obj))

    fun size() : Int = objects.size

    override operator fun iterator() : Iterator<QuantifiedObject> = objects.iterator()

}