import java.util.LinkedList;
import java.util.PriorityQueue;

public class ChessKnight {
    //shortest path from top left on a board to bottom right
    //can be easily extended to provide shortest path to from/to any coordinates on the board
    public static int solution(int[][] A) {
        // if lists are empty exit early
        if (A.length < 1 || A[0].length < 1) {
            return -1;
        }

        int lastY = A.length - 1;
        int lastX = A[0].length - 1;
        //if unreachable exit early
        if (A[lastY][lastX] == 1)
            return -1;

        if (lastY == 0 && lastX == 0)
            return 0;

        PriorityQueue<Path> paths = new PriorityQueue<>();
        LinkedList<Move> possibleMoves = possibleMoves(new Move(lastX, lastY), copyMatrix(A));
        for (Move move : possibleMoves) {
            Path path = new Path(copyMatrix(A));
            path.list.add(move);
            paths.offer(path);
        }
        while (!paths.isEmpty()) {
            Path currentPath = paths.poll();
            Move lastMove = currentPath.list.getLast();
            if (lastMove.X == 0 && lastMove.Y == 0) {
                return currentPath.depth;
            }
            currentPath.depth++;
            possibleMoves = possibleMoves(lastMove, currentPath.matrix);
            for (Move m : possibleMoves) {
                Path newPath = Path.copyOf(currentPath);
                newPath.list.add(m);
                paths.offer(newPath);
            }
        }
        return -1;
    }

    private static LinkedList<Move> possibleMoves(Move lastMove, int[][] a) {
        LinkedList<Move> list = new LinkedList<Move>();
        int yLimit = a.length;
        int xLimit = yLimit > 0 ? a[0].length : 0;

        if (lastMove.X >= xLimit || lastMove.Y >= yLimit || lastMove.X < 0 || lastMove.Y < 0)
            return list;

        a[lastMove.Y][lastMove.X] = 1;

        //two up + one left
        int x = lastMove.X - 1;
        int y = lastMove.Y - 2;
        if (y >= 0 && x >= 0 && a[y][x] == 0)
            list.add(new Move(x, y));

        //two up + one right
        x = lastMove.X + 1;
        y = lastMove.Y - 2;
        if (y >= 0 && x < xLimit && a[y][x] == 0)
            list.add(new Move(x, y));

        //one up + two left
        x = lastMove.X - 2;
        y = lastMove.Y - 1;
        if (y >= 0 && x >= 0 && a[y][x] == 0)
            list.add(new Move(x, y));

        //one up + two right
        x = lastMove.X + 2;
        y = lastMove.Y - 1;
        if (y >= 0 && x < xLimit && a[y][x] == 0)
            list.add(new Move(x, y));

        //one down + two left
        x = lastMove.X - 2;
        y = lastMove.Y + 1;
        if (y < yLimit && x >= 0 && a[y][x] == 0)
            list.add(new Move(x, y));

        //one down + two right
        x = lastMove.X + 2;
        y = lastMove.Y + 1;
        if (y < yLimit && x < xLimit && a[y][x] == 0)
            list.add(new Move(x, y));

        //two down + one left
        x = lastMove.X - 1;
        y = lastMove.Y + 2;
        if (y < yLimit && x >= 0 && a[y][x] == 0)
            list.add(new Move(x, y));

        //two down + one right
        x = lastMove.X + 1;
        y = lastMove.Y + 2;
        if (y < yLimit && x < xLimit && a[y][x] == 0)
            list.add(new Move(x, y));

        return list;
    }

    public static int[][] copyMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            newMatrix[i] = matrix[i].clone();
        return newMatrix;
    }

    private static class Path implements Comparable<Path> {
        LinkedList<Move> list;
        int[][] matrix;
        int depth;

        public Path(int[][] matrix) {
            this.list = new LinkedList<>();
            this.matrix = matrix;
            this.depth = 1;
        }

        public static Path copyOf(Path path) {
            Path newPath = new Path(copyMatrix(path.matrix));
            newPath.depth = path.depth;
            for (Move move : path.list)
                newPath.list.add(new Move(move.X, move.Y));
            return newPath;
        }

        @Override
        public int compareTo(Path o) {
            return Integer.compare(this.depth, o.depth);
        }
    }

    private static class Move {
        int X;
        int Y;

        public Move(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }
    }
}