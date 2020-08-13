package pl.edu.agh.mock.algorithm

import pl.edu.agh.mock.config.MockConfig
import pl.edu.agh.mock.model.Mock
import pl.edu.agh.xinuk.algorithm.{Plan, PlanCreator, StateUpdate}
import pl.edu.agh.xinuk.model.{CellContents, CellState, Direction, Empty, SignalMap}

import scala.util.Random

final case class MockPlanCreator() extends PlanCreator[MockConfig] {

  override def createPlans(iteration: Long, cellState: CellState, neighbourContents: Map[Direction, CellContents])
                          (implicit config: MockConfig): (Map[Direction, Seq[Plan]], MockMetrics) = {
    cellState.contents match {
      case Mock => (randomMove(neighbourContents), MockMetrics(1, 0))
      case _ => (Map.empty, MockMetrics.empty)
    }
  }

  def randomMove(neighbours: Map[Direction, CellContents])(implicit config: MockConfig): Map[Direction, Seq[Plan]] = {
    val availableDirections = neighbours.filter {
      case (_, Empty) => true
      case _ => false
    }

    if (availableDirections.isEmpty) {
      Map.empty
    } else {

      val direction = availableDirections.keys.toSeq(Random.nextInt(availableDirections.size))

      val action = StateUpdate(CellState(Mock, SignalMap.empty))
      val consequence = StateUpdate(CellState(Empty, SignalMap.empty))

      Map((direction, Seq(Plan(action, consequence))))
    }
  }
}