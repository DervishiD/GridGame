package game.gamestate

enum class GameState(private val stateName : String) {
    PLAYER("PLAYER"),
    DEFAULT_FIGHT("DEFAULT_FIGHT"),
    FIGHTER_ACTION_SELECTION("FIGHTER_ACTION_SELECTION"),
    BROWSING_OBJECTS("BROWSING_OBJECTS"),
    BROWSING_ACTIONS("BROWSING_ACTIONS"),
    PERFORMING_ACTION_ON_ZONE("PERFORMING_ACTION_ON_ZONE");

    companion object{

        fun encode(gameState : GameState) : String = gameState.stateName

        fun decode(gameState : String) : GameState{
            for(state : GameState in values()){
                if(state.stateName == gameState) return state
            }
            throw IllegalArgumentException("The state $gameState doesn't exist.")
        }

    }

}