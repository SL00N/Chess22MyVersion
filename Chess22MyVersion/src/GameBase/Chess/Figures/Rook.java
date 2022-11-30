package GameBase.Chess.Figures;

import GameBase.Chess.ChessBoard;
import GameBase.Chess.ChessFigure;
import GameBase.Base.Coordinate;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Rook extends ChessFigure {
    public Rook(boolean colorIsWhite, Coordinate coordinateFrom) {
        super(colorIsWhite, colorIsWhite ? '\u2656' : '\u265c', coordinateFrom);
    }

    @Override
    public boolean canMove(Coordinate coordinateTo) {
        boolean isSmooth = this.from.getY() == coordinateTo.getY() ||
                this.from.getX() == coordinateTo.getX();
        return isSmooth;
    }

    @Override
    public Coordinate[] makeRoad(Coordinate to) {
        Coordinate[] road;
        if (this.from.getY() == to.getY()) {
            int DIFFERENCE = Math.max(this.from.getX(), to.getX()) - Math.min(this.from.getX(), to.getX()) - 1;
            int MIN = Math.min(this.from.getX(), to.getX()) + 1;
            int Y = to.getY();
            road = new Coordinate[DIFFERENCE];

            for (int i = 0; i < DIFFERENCE; i++) {
                road[i] = new Coordinate(i + MIN, Y);
            }
        } else {
            int DIFFERENCE = Math.max(this.from.getY(), to.getY()) - Math.min(this.from.getY(), to.getY()) - 1;
            int MIN = Math.min(this.from.getY(), to.getY()) + 1;
            int X = to.getX();
            road = new Coordinate[DIFFERENCE];

            for (int i = 0; i < DIFFERENCE; i++) {
                road[i] = new Coordinate(X, i + MIN);
            }
        }
        System.out.println(Arrays.toString(road));
        return road;
    }
}
