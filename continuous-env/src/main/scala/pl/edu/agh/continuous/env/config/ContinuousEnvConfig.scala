package pl.edu.agh.continuous.env.config

import pl.edu.agh.xinuk.config.{GuiType, XinukConfig}
import pl.edu.agh.xinuk.model.{WorldType}

final case class ContinuousEnvConfig(
                                worldType: WorldType,
                                worldWidth: Int,
                                worldHeight: Int,
                                iterationsNumber: Long,

                                signalSuppressionFactor: Double,
                                signalAttenuationFactor: Double,
                                signalSpeedRatio: Int,

                                workersRoot: Int,
                                isSupervisor: Boolean,
                                shardingMod: Int,

                                guiType: GuiType,
                                guiCellSize: Int,

                                cellSize: Int,

                                beingSpawnChance: Double
                              ) extends XinukConfig
