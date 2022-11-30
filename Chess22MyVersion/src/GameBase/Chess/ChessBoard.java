package GameBase.Chess;

import GameBase.Base.Board;
import GameBase.Base.Coordinate;
import GameBase.Base.Movable;
import GameBase.Chess.Figures.*;

import java.util.Arrays;

public class ChessBoard extends Board {
    private static final int chessFieldSize;

    private static Coordinate whiteKing;

    private static Coordinate blackKing;

    static {
        chessFieldSize = 8;
    }

    private static ChessBoard instance;


    private ChessBoard() {
        this.field = new ChessFigure[chessFieldSize][chessFieldSize];
        for (Movable[] cf : field) Arrays.fill(cf, null);
        this.whiteKing = new Coordinate(0, 4);
        this.blackKing = new Coordinate(4, 7);

    }

    public static ChessBoard getInstance() {
        if (instance == null) instance = new ChessBoard();
        return instance;
    }

    private void resetField() {
        for (Movable[] cf : field) Arrays.fill(cf, null);
    }

    public void newField() {
        this.resetField();
        // white
        field[0][0] = new Rook(true, new Coordinate(0, 0));
        field[0][1] = new Knight(true, new Coordinate(1, 0));
        field[0][2] = new Bishop(true, new Coordinate(2, 0));
        field[0][3] = new Queen(true, new Coordinate(3, 0));
        field[0][4] = new King(true, new Coordinate(4, 0));
        field[0][5] = new Bishop(true, new Coordinate(5, 0));
        field[0][6] = new Knight(true, new Coordinate(6, 0));
        field[0][7] = new Rook(true, new Coordinate(7, 0));
        for (int i = 0; i < chessFieldSize; i++)
            field[1][i] = new Pawn(true, new Coordinate(i, 1));
        //black
        field[7][0] = new Rook(false, new Coordinate(0, 7));
        field[7][1] = new Knight(false, new Coordinate(1, 7));
        field[7][2] = new Bishop(false, new Coordinate(2, 7));
        field[7][3] = new Queen(false, new Coordinate(3, 7));
        field[7][4] = new King(false, new Coordinate(4, 7));
        field[7][5] = new Bishop(false, new Coordinate(5, 7));
        field[7][6] = new Knight(false, new Coordinate(6, 7));
        field[7][7] = new Rook(false, new Coordinate(7, 7));
        for (int i = 0; i < chessFieldSize; i++)
            field[6][i] = new Pawn(false, new Coordinate(i, 6));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" |A|B|C|D|E|F|G|H\n").append("-----------------\n");
        for (int i = 0; i < chessFieldSize; i++) {
            sb.append(8 - i);
            for (int j = 0; j < chessFieldSize; j++)
                sb.append("|").append(
                        field[chessFieldSize - 1 - i][j] != null ? field[chessFieldSize - 1 - i][j] : " "
                );
            sb.append("\n-----------------\n");
        }
        sb.append(" |A|B|C|D|E|F|G|H\n").append("-----------------\n");
        return sb.toString();
    }

    public boolean canMove(Coordinate from, Coordinate to) {
        if (field[from.getY()][from.getX()] == null) return false; // ||(checkDanger(blackKing,  false) || checkDanger(whiteKing, true))
        //else if (((ChessFigure)field[from.getY()][from.getX()]).isColorIsWhite() != Main.get)
        else {
            ChessFigure tempFrom = (ChessFigure) field[from.getY()][from.getX()];
            ChessFigure tempTo = (ChessFigure) field[to.getY()][to.getX()];
            System.out.println("from = " + from);
            System.out.println("Ffrom = " + tempFrom);
            switch (tempFrom.getClass().getSimpleName()) {
                case "Bishop":
                    if (tempFrom.canMove(to)) {
                        if (Math.abs(from.getX() - to.getX()) == 1) return checkLastFigure(to, tempFrom.isColorIsWhite());
                        else return checkObstacles(tempFrom.makeRoad(to)) && checkLastFigure(to, tempFrom.isColorIsWhite());
                    }
                case "King":
                    System.out.println(checkLastFigure(to, tempFrom.isColorIsWhite()) + " " + tempFrom.canMove(to));
                    if (tempFrom.canMove(to)) return checkLastFigure(to, tempFrom.isColorIsWhite()); // checkDanger(to, tempFrom.isColorIsWhite()) &&
                    else return false;
                case "Queen":
                case "Rook":
                    if(tempFrom.canMove(to)) {
                        if (Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()) == 1) {
                            return checkLastFigure(to, tempFrom.isColorIsWhite());
                        }
                        else {return(checkObstacles(tempFrom.makeRoad(to))) &&
                            checkLastFigure(to, tempFrom.isColorIsWhite());}
                    }
                    else {return false;}
                case "Knight":
                    if (tempFrom.canMove(to))
                        return checkLastFigure(to, tempFrom.isColorIsWhite());
                    else return false;
                case "Pawn":
                    if (tempFrom.canMove(to))
                        if (tempTo == null) return from.getX() == to.getX();
                        else return tempFrom.isColorIsWhite() != tempTo.isColorIsWhite() && from.getX() != to.getX();
                    else return false;
                default:
                    return false;
            }
        }
    }

    public void move(Coordinate from, Coordinate to) {
        ((ChessFigure) field[from.getY()][from.getX()]).moveTo(to);
        field[to.getY()][to.getX()] = field[from.getY()][from.getX()];
        field[from.getY()][from.getX()] = null;
    }

    private boolean checkObstacles(Coordinate[] road) {
        boolean flagObstacle = true;
        for (int i = 0; i < road.length; i++) {
            int x = road[i].getX(), y = road[i].getY();
            if (field[y][x] != null) {
                flagObstacle = false;
                break;
            }
        }
        return flagObstacle;
    }

    private boolean checkLastFigure(Coordinate to, boolean colorIsWhite) {
        boolean flag = true;
        ChessFigure toFigure = (ChessFigure) field[to.getY()][to.getX()];
        if (toFigure != null && toFigure.isColorIsWhite() == colorIsWhite) {flag = false;}
        return flag;
    }

    // TODO make checkDanger and king
    public boolean checkDanger(Coordinate to, boolean colorIsWhite) {
        boolean flag = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessFigure figure = (ChessFigure) field[i][j];
                if (figure != null && figure.isColorIsWhite() != colorIsWhite) {
                    // if (ChessBoard.canMove(figure.getFrom(), colorIsWhite ? whiteKing : blackKing)) flag = false;
                    break;
                }
            }
        }
        return flag;
    }
}
