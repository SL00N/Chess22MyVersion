package GameBase.Chess.Figures;

import GameBase.Chess.ChessFigure;
import GameBase.Base.Coordinate;

public class King extends ChessFigure {

    public King(boolean colorIsWhite, Coordinate coordinateFrom) {
        super(colorIsWhite, colorIsWhite ? '\u2654' : '\u265a', coordinateFrom);
    }

    @Override
    public boolean canMove(Coordinate coordinateTo) {
        if (Math.abs(coordinateTo.getX() - this.from.getX()) == 1 &&
                Math.abs(coordinateTo.getY() - this.from.getY()) == 1) return true;
        else return false;
    }

}
