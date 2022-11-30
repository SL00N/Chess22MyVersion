package GameBase.Chess;

import GameBase.Base.Coordinate;

public class ChessGame {
    private final ChessBoard board;
    public static ChessGame instance;

    private ChessGame() {
        board = ChessBoard.getInstance();
    }

    public static ChessGame getInstance() {
        if (instance == null) instance = new ChessGame();
        return instance;
    }

    public void newGame() {
        board.newField();
        this.showBoard();
    }

    public boolean makeStep(Coordinate[] coor) {
        boolean flag = true;
        if (board.canMove(coor[0], coor[1])) {
            board.move(coor[0], coor[1]);
            this.showBoard();
        } else {
            flag = false;
        }
        return flag;
    }

    public void showBoard() {
        System.out.println(board);
    }

}