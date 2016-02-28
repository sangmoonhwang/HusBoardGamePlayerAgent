package student_player.mytools;

import hus.HusMove;


public class MoveAndCost {
  double cost;
  public HusMove move;

  public MoveAndCost(HusMove m, double d) {
    move = m;
    this.cost = d;
  }
}
