import java.util.LinkedList;
import java.util.PriorityQueue;

public class ChessKnight {
    //shortest path from bottom right to top left on a custom rectangular chess board
    //can be easily extended to provide the shortest path to from/to any coordinates on the board
    final static Move TARGET = new Move(0, 0, Integer.MAX_VALUE);

    public static int solution(int[][] chessBoard) {
        // if lists are empty exit early
        if (chessBoard.length < 1 || chessBoard[0].length < 1) {
            return -1;
        }

        // we start from bottom right
        int lastY = chessBoard.length - 1;
        int lastX = chessBoard[0].length - 1;

        //if unreachable exit early
        if (chessBoard[lastY][lastX] == 1 || chessBoard[0][0] == 1)
            return -1;

        // if the custom chess board is 1x1
        if (lastY == 0 && lastX == 0)
            return 0;

        // breadth first search where we discover all positions x moves away
        // before we move to x+1 moves away until we reach the first square on the board
        chessBoard[lastY][lastX] = 1;
        PriorityQueue<Move> moves = new PriorityQueue<>();
        moves.addAll(possibleMoves(new Move(lastX, lastY, 0), chessBoard));
        while (!moves.isEmpty()) {
            Move currentMove = moves.poll();
            if (currentMove.X == TARGET.X && currentMove.Y == TARGET.Y) {
                return currentMove.moveCount;
            }
            moves.addAll(possibleMoves(currentMove, chessBoard));
        }
        return -1;
    }

    // scan function that takes as input the last move and a board and returns a list of possible legal moves
    private static LinkedList<Move> possibleMoves(Move lastMove, int[][] chessBoard) {
        LinkedList<Move> list = new LinkedList<>();
        int yLimit = chessBoard.length;
        int xLimit = yLimit > 0 ? chessBoard[0].length : 0;

        if (lastMove.X >= xLimit || lastMove.Y >= yLimit || lastMove.X < 0 || lastMove.Y < 0)
            return list;

        //two up + one left
        int x = lastMove.X - 1;
        int y = lastMove.Y - 2;
        if (y >= 0 && x >= 0 && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //two up + one right
        x = lastMove.X + 1;
        y = lastMove.Y - 2;
        if (y >= 0 && x < xLimit && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //one up + two left
        x = lastMove.X - 2;
        y = lastMove.Y - 1;
        if (y >= 0 && x >= 0 && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //one up + two right
        x = lastMove.X + 2;
        y = lastMove.Y - 1;
        if (y >= 0 && x < xLimit && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //one down + two left
        x = lastMove.X - 2;
        y = lastMove.Y + 1;
        if (y < yLimit && x >= 0 && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //one down + two right
        x = lastMove.X + 2;
        y = lastMove.Y + 1;
        if (y < yLimit && x < xLimit && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //two down + one left
        x = lastMove.X - 1;
        y = lastMove.Y + 2;
        if (y < yLimit && x >= 0 && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        //two down + one right
        x = lastMove.X + 1;
        y = lastMove.Y + 2;
        if (y < yLimit && x < xLimit && chessBoard[y][x] == 0) {
            list.add(new Move(x, y, lastMove.moveCount + 1));
            chessBoard[y][x] = 1;
        }

        return list;
    }

    private static class Move implements Comparable<Move> {
        int moveCount;
        int X;
        int Y;

        public Move(int X, int Y, int move) {
            this.moveCount = move;
            this.X = X;
            this.Y = Y;
        }

        @Override
        public int compareTo(Move other) {
            if (this.moveCount == other.moveCount) {
                int thisDistanceToTarget = Math.abs(this.X - TARGET.X) + Math.abs(this.Y - TARGET.Y);
                int otherDistanceToTarget = Math.abs(other.X - TARGET.X) + Math.abs(other.Y - TARGET.Y);
                return Integer.compare(thisDistanceToTarget, otherDistanceToTarget);
            }
            return Integer.compare(this.moveCount, other.moveCount);
        }
    }
}