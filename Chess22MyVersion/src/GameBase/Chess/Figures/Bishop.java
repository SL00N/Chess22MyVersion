package GameBase.Chess.Figures;

import GameBase.Chess.ChessFigure;
import GameBase.Base.Coordinate;

public class Bishop extends ChessFigure {
    public Bishop(boolean colorIsWhite, Coordinate coordinateFrom) {
        super(colorIsWhite, colorIsWhite ? '\u2657' : '\u265d', coordinateFrom);
    }

    @Override
    public boolean canMove(Coordinate coordinateTo) {
        if (Math.abs(this.from.getX() - coordinateTo.getX()) !=
                Math.abs(this.from.getY() - coordinateTo.getY())) return false;
        else return true;
    }

    @Override
    public Coordinate[] makeRoad(Coordinate to) {
        int MAX_X = Math.max(this.from.getX(), to.getX());
        int MAX_Y = Math.max(this.from.getY(), to.getY());
        int MIN_X = Math.min(this.from.getX(), to.getX());
        int MIN_Y = Math.min(this.from.getY(), to.getY());
        Coordinate[] road = new Coordinate[MAX_X - MIN_X - 1];
        for (int i = 0; i < MAX_X - MIN_Y - 1; i++) {
            road[i] = new Coordinate(MIN_X + i + 1, MIN_Y + i + 1);
        }
        return road;
    }
}
