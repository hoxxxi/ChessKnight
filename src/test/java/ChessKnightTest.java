import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessKnightTest {

    /**
     * Simple chess board:
     * 000
     * 000
     * 000
     * 000
     */
    @Test
    public void testEmptyChessBoard() {
        int[][] A = new int[4][3];
        assertEquals(3, ChessKnight.solution(A));
    }

    /**
     * Restricted chess board:
     * 000
     * 001
     * 100
     * 000
     */
    @Test
    public void testRestrictedChessBoard() {
        int[][] A = new int[4][3];
        A[1][2] = 1;
        A[2][0] = 1;
        assertEquals(7, ChessKnight.solution(A));
    }
}