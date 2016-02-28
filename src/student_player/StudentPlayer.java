package student_player;

import hus.HusBoardState;
import hus.HusMove;
import hus.HusPlayer;

import java.util.ArrayList;
import java.util.PriorityQueue;

import student_player.mytools.MoveAndCost;
import student_player.mytools.MyTools;
import student_player.mytools.MyTools.PQsort;

/** A Hus player submitted by a student. */
public class StudentPlayer extends HusPlayer {

  /**
   * You must modify this constructor to return your student number. This is
   * important, because this is what the code that runs the competition uses to
   * associate you with your agent. The constructor should do nothing else.
   */
  public StudentPlayer() {
    super("260569675");
  }

  /**
   * This is the primary method that you need to implement. The ``board_state``
   * object contains the current state of the game, which your agent can use to
   * make decisions. See the class hus.RandomHusPlayer for another example
   * agent.
   */
  public HusMove chooseMove(HusBoardState board_state) {
    PriorityQueue<MoveAndCost> pq =
        new PriorityQueue<MoveAndCost>(32, new PQsort());
    HusBoardState cloned_board_state = null;

    // Get the legal moves for the current board state.
    ArrayList<HusMove> moves = board_state.getLegalMoves();
    long startTime = System.nanoTime();
    for (HusMove m : moves) {
      // We can see the effects of a move like this...
      cloned_board_state = (HusBoardState) board_state.clone();
      if (cloned_board_state.move(m))
        pq.add(new MoveAndCost(m, MyTools.getCost(cloned_board_state,
            player_id, opponent_id)));
      if (System.nanoTime() - startTime > 1900000000) // 1.9 seconds or more has elapsed
        break;
    }
//    System.out.println((System.nanoTime() - startTime) / 1000000000.0 + "s");
//    return pq.poll().move;
    return MyTools.getMoveFromPQ(pq);
  }
}
