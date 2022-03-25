import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessKnightTest {
    @Test
    public void testBidOrder() {
        int[][] A = new int[4][3];
        assertEquals(3, ChessKnight.solution(A));
        A[2][0] = 1;
        A[1][2] = 1;
        assertEquals(7, ChessKnight.solution(A));
    }
}
