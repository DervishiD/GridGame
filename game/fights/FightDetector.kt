package game.fights

class FightDetector {

    companion object{

        const val NO_FIGHT : String = "NO_FIGHT"

        fun detectFightFor(name : CharSequence) : Boolean{
            return when(name){
                NO_FIGHT -> false
                else -> TODO("Not implemented.")
            }
        }

    }

}