package student_player.mytools;

import hus.HusBoardState;
import hus.HusMove;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyTools {

  /**
   * Mapping of adjacent pits. ex) NEIGHBORS[0] == 31; NEIGHBORS[31] == 0
   */
  public static final int[] NEIGHBORS = {31, 30, 29, 28, 27, 26, 25, 24, 23,
      22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3,
      2, 1, 0};

  /**
   * Returns the index of the adjacent opposite pit.
   * 
   * @param index of the pit
   * @return index of adjacent opposite pit. If negative, then should be seen as
   *         invalid
   */
  public static int getOppositePit(int index) {
    return 47 - index;
  }

  public static HusMove getMoveFromPQ(PriorityQueue<MoveAndCost> pq) {
    double r = Math.random();
    if (r < 0.41)
      return pq.poll().move;
    else if (r % 2 == 0) {
      // Remove the first head
      pq.poll();
      return pq.poll().move;
    } else {
      // Remove the first head
      pq.poll();
      return pq.poll().move;
    }
  }

  /**
   * Indicate if a pit at the given index is an inner pit
   * 
   * @param index of the pit
   * @return true if the pit is inner
   */
  public static boolean isInnerPit(int index) {
    return index >= 16;
  }

  /**
   * Get the difference between the player's cost and the oppenent's. Bigger the
   * difference, better for the player because the player pays less cost than
   * than opponent does.
   * 
   * @param cloned_board_state
   * @param player_id
   * @param opponent_id
   * @return Difference of player's and opponent's cost.
   */
  public static double getCost(HusBoardState cloned_board_state, int player_id,
      int opponent_id) {
    // Get the contents of the pits so we can use it to make decisions.
    int[][] pits = cloned_board_state.getPits();

    // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
    int[] my_pits = pits[player_id];
    int[] op_pits = pits[opponent_id];

    double fCost = getfCost(cloned_board_state, player_id, opponent_id);
    // cloned_board_state.move(cloned_board_state.getRandomMove());
    // System.out.println(cloned_board_state.getTurnPlayer());

    return (getCost(my_pits) - getCost(op_pits)) + fCost;
  }

  private static double getCost(int[] myPits) {
    int totalSeeds = 0;
    int capturableSeeds = 0;
    int frozenPits = 0; // Pits with 0 or 1 seed
    for (int i = 0; i < myPits.length; i++) {
      totalSeeds += myPits[i];
      if (myPits[i] <= 1) frozenPits++;
      if (i >= 16 && myPits[i] > 0)
      // Inner seeds + outer seeds
        capturableSeeds += myPits[i] + myPits[NEIGHBORS[i]];
    }
    return (double) frozenPits + (capturableSeeds / (double) totalSeeds);
  }

  private static double getfCost(HusBoardState cloned_board_state,
      int player_id, int opponent_id) {
    if (cloned_board_state.gameOver()) return 0;
    HusBoardState future = null;
    // Maximum cost difference in one turn ahead between the player and the
    // opponent
    double futureMax = 0;
    double avg = 0;
    for (HusMove hm : cloned_board_state.getLegalMoves()) {
      future = (HusBoardState) cloned_board_state.clone();
      if (future.move(hm)) {
        int[][] fpits = future.getPits();
        double c = getCost(fpits[player_id]) - getCost(fpits[opponent_id]);
        avg += c;
        if (c > futureMax) {
          futureMax = c;
        }
      }
    }
    avg /= cloned_board_state.getLegalMoves().size();
    return (0.1 * avg + 0.9 * futureMax);
  }

  public static class PQsort implements Comparator<MoveAndCost> {

    @Override
    public int compare(MoveAndCost o1, MoveAndCost o2) {
      return (int) Math.round(o1.cost - o2.cost);
    }

  }
}
