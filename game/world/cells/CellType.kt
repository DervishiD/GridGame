package game.world.cells

enum class CellType(private val typeName : String) {
    NORMAL("NORMAL"),
    WATER("WATER");

    companion object{

        fun encode(cellType : CellType) : String = cellType.typeName

        fun decode(cellType : String) : CellType{
            for(type : CellType in values()){
                if(type.typeName == cellType) return type
            }
            throw IllegalArgumentException("The cell type $cellType doesn't exist.")
        }

    }

}