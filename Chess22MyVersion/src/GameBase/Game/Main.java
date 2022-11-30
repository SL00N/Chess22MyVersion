package GameBase.Game;

import GameBase.Base.Coordinate;
import GameBase.Chess.ChessGame;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static private ChessGame chessGame;
    static private boolean isEndGame;
    static private int stepCounter;
    static private Coordinate[] coor;

    static private boolean whiteOrBlack;

    public boolean getWhiteOrBlack() {return whiteOrBlack;}

    static {
        isEndGame = false;
        stepCounter = 0;
        coor = new Coordinate[2];
        whiteOrBlack = true;
    }

    public static void main(String[] args) {
        newGame();
        while (!isEndGame) {
            showMessage();
            boolean[] answers = checkAnswer(readAnswer());
            if (answers[0] && answers[1]) makeStep();
        }
    }

    static private void newGame() {
        chessGame = ChessGame.getInstance();
        chessGame.newGame();
    }

    static private void showMessage() {
        System.out.println("Press \"exit\" to stop game!");
        System.out.println("Or input your next step, " + ((whiteOrBlack) ? "mr.White" : "mr.Black"));
        System.out.println("In type by example: 'A1-A2'");
        System.out.println("Or in type by example: 'A1A2'");
        whiteOrBlack = (++stepCounter) % 2 == 1;
    }

    static private String readAnswer() {
        return (new Scanner(System.in)).nextLine().toLowerCase();
    }

    static private boolean[] checkAnswer(String st) {
        boolean flagEnd = true;
        boolean flagCorrect = true;
        System.out.println("st = " + st);
        if (st.equals("exit")) {
            endGame();
            flagEnd = false;
        } else {
            if (!isInputCorrectStep(st)) flagCorrect = false;
            showStepMessage(st);
        }
        boolean[]flags = new boolean[] {flagEnd, flagCorrect};
        return flags;
    }

    static private void makeStep() {
        if (!chessGame.makeStep(coor)) showError();
        else chessGame.makeStep(coor);
    }

    static private void endGame() {
        System.out.println("Thank you for game! Bye!!! Bye!!!");
        isEndGame = true;
    }

    static private boolean isInputCorrectStep(String st) {
        boolean flagCorrect = true;
        coor = new Coordinate[2];
        char[] data = st.toCharArray();
        System.out.println(Arrays.toString(data));
        if (!(data.length == 4 || data.length == 5)) {
            flagCorrect = false;
        } else {
            int t = 0;
            for (int i = 0; i < 2; i++) {
                if ((data[i * 2 + t] >= 'a' && data[i * 2 + t] <= 'h')
                        && (data[i * 2 + 1 + t] >= '1' && data[i * 2 + 1 + t] <= '8'))
                    coor[i] = new Coordinate(data[i * 2 + t] - 'a', data[i * 2 + 1 + t] - '1');
                else {
                    flagCorrect = false;
                    break;
                }
                if (data.length == 5) t++;
            }
        }
        if (!flagCorrect) {
            System.err.println("Incorrect input data");
            stepCounter--;
        }
        return flagCorrect;
    }

    static private void showStepMessage(String st) {
        //TODO something
        System.out.println((((stepCounter) % 2 == 1) ? "Mr.White" : "Mr.Black") + ", your turn is: " + st);
        System.out.println("And coordinate on field are:" + Arrays.toString(coor));
    }
    private static void showError() {
        System.err.println("Figure can't go to select coordinates");
        System.err.println("Please, try again with new coordinates\n");
        stepCounter--;
    }
}