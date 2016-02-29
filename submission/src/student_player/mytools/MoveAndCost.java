package student_player.mytools;

import hus.HusMove;


public class MoveAndCost {
  /**
   * relative moving cost of player to the opponent
   */
  double cost;
  public HusMove move;

  public MoveAndCost(HusMove m, double d) {
    move = m;
    this.cost = d;
  }
}
