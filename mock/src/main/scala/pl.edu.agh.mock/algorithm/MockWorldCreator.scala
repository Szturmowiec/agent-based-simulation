package pl.edu.agh.mock.algorithm

import pl.edu.agh.mock.config.MockConfig
import pl.edu.agh.mock.model.Mock
import pl.edu.agh.xinuk.algorithm.WorldCreator
import pl.edu.agh.xinuk.model._
import pl.edu.agh.xinuk.model.grid.{GridCellId, GridWorldBuilder}

object MockWorldCreator extends WorldCreator[MockConfig] {

  override def prepareWorld()(implicit config: MockConfig): GridWorldBuilder = {
    val worldBuilder: GridWorldBuilder = GridWorldBuilder().withGridConnections().withWrappedBoundaries()

    Seq(
      (config.worldSize / 4, config.worldSize / 4),
      (3 * config.worldSize / 4, config.worldSize / 4),
      (config.worldSize / 4, 3 * config.worldSize / 4),
      (3 * config.worldSize / 4, 3 * config.worldSize / 4),
    )
      .map({ case (x, y) => GridCellId(x, y) })
      .foreach(id => worldBuilder(id) = CellState(Mock, SignalMap.uniform(config.mockInitialSignal)))

    worldBuilder
  }
}