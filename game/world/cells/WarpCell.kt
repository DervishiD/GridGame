package game.world.cells

import game.eventhandler.KeyEventHandler
import game.gamestate.GameState
import game.info.PlayerData
import game.info.ZoneData
import game.player.Player
import game.world.Position
import game.world.Zone

class WarpCell(position : Position,
               private val cellType : CellType,
               private val warpZoneName : String,
               private val warpPosition: Position,
               private val cellImage : String) : AbstractPlayerCell(position) {

    companion object : CellCompanion<WarpCell>{

        private const val CELL_TYPE_INDEX : Int = 1
        private const val WARP_ZONE_NAME_INDEX : Int = 2
        private const val POSITION_INDEX : Int = 3
        private const val CELL_IMAGE_INDEX : Int = 4

        override fun cellName(): String = "WARP"

        override fun generateFromData(data : List<String>, line : Int, column : Int) : WarpCell{
            return WarpCell(Position(line, column),
                            CellType.decode(data[CELL_TYPE_INDEX]),
                            data[WARP_ZONE_NAME_INDEX],
                            Position.decode(data[POSITION_INDEX]),
                            data[CELL_IMAGE_INDEX])
        }

        override fun encodeData(cell: WarpCell, dataSeparator: String): String {
            return "${cellName()}$dataSeparator${CellType.encode(cell.cellType)}$dataSeparator${cell.warpZoneName}$dataSeparator" +
                    "${Position.encode(cell.warpPosition)}$dataSeparator${cell.cellImage}"
        }

    }

    private fun warpZone() : Zone = Zone.generateZoneByName(warpZoneName)

    private fun generatedPlayerData(player : Player) : PlayerData = PlayerData(player, warpPosition)

    override fun actOnPlayerStep(player: Player) {
        val zone : Zone = warpZone()
        val playerData : PlayerData = generatedPlayerData(player)
        zone.cellAt(warpPosition).setCellComponent(player)
        val isFighting : Boolean = zone.hasAFight()
        val zoneData : ZoneData = if(isFighting){
            val fighterPositions = zone.generateFight(player)
            ZoneData(playerData,
                     zone,
                     GameState.DEFAULT_FIGHT,
                     fighterPositions.first,
                     fighterPositions.second,
                     warpPosition)
        }else{
            ZoneData(playerData,
                     zone,
                     GameState.PLAYER,
                     setOf(),
                     mapOf(),
                     warpPosition)
        }
        KeyEventHandler.setReceiver(zoneData)
    }

    override fun cellType(): CellType = cellType

    override fun cellName(): String = WarpCell.cellName()

    fun cellImage() : String = cellImage

}