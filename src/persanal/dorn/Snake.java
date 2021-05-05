package persanal.dorn;

import javax.swing.*;
import java.awt.*;

public class Snake {
    private final int[] CoordsX;
    private final int[] CoordsY;
    private final char[] Direction;
    private final boolean[] turn;
    private int Length;

    Snake(int Length, int maxLength, char HeadDirection, int Size){
        CoordsX = new int[maxLength];
        CoordsY = new int[maxLength];
        Direction = new char[maxLength];
        turn = new boolean[maxLength];
        this.Length = Length;
        Direction[0] = HeadDirection;
        turn[0] = false;
        CoordsX[0] = Size;
        CoordsY[0] = Size;
    }

    public int getCoordsX(int i) {
        return CoordsX[i];
    }

    public void setCoordsX(int i, int value) {
        CoordsX[i] = value;
    }

    public int getCoordsY(int i) {
        return CoordsY[i];
    }

    public void setCoordsY(int i, int value) {
        CoordsY[i] = value;
    }

    public char getDirection(int i){
        return Direction[i];
    }

    public void setDirection(int i, char value){
        Direction[i] = value;
    }

    public int getLength(){
        return Length;
    }

    public boolean getTurn(int i){
        return turn[i];
    }

    public void setTurn(int i, boolean value){
        turn[i] = value;
    }

    public void addLength(){
        Length++;
    }

    private boolean turnRD (int i){
        return  ((Direction[i + 1] == 'D') && (Direction[i - 1] == 'L')) ||
                ((Direction[i - 1] == 'U') && (Direction[i + 1] == 'R')) ||
                ((Direction[i + 1] == 'U') && (Direction[i - 1] == 'U') && (Direction[i] == 'R')) ||
                ((Direction[i + 1] == 'L') && (Direction[i - 1] == 'L') && (Direction[i] == 'D')) ||
                ((Direction[i + 1] == 'R') && (Direction[i - 1] == 'L') && (Direction[i] == 'D')) ||
                ((Direction[i + 1] == 'D') && (Direction[i - 1] == 'U') && (Direction[i] == 'R'));
    }

    private boolean turnRU (int i){
        return  ((Direction[i + 1] == 'R') && (Direction[i - 1] == 'D')) ||
                ((Direction[i - 1] == 'L') && (Direction[i + 1] == 'U')) ||
                ((Direction[i + 1] == 'L') && (Direction[i - 1] == 'L') && (Direction[i] == 'U')) ||
                ((Direction[i + 1] == 'D') && (Direction[i - 1] == 'D') && (Direction[i] == 'R')) ||
                ((Direction[i + 1] == 'R') && (Direction[i - 1] == 'L') && (Direction[i] == 'U')) ||
                ((Direction[i + 1] == 'U') && (Direction[i - 1] == 'D') && (Direction[i] == 'R'));

    }

    private boolean turnLD (int i){
        return  ((Direction[i + 1] == 'D') && (Direction[i - 1] == 'R')) ||
                ((Direction[i - 1] == 'U') && (Direction[i + 1] == 'L')) ||
                ((Direction[i + 1] == 'R') && (Direction[i - 1] == 'R') && (Direction[i] == 'D')) ||
                ((Direction[i + 1] == 'L') && (Direction[i - 1] == 'R') && (Direction[i] == 'D')) ||
                ((Direction[i + 1] == 'U') && (Direction[i - 1] == 'U') && (Direction[i] == 'L')) ||
                ((Direction[i + 1] == 'D') && (Direction[i - 1] == 'U') && (Direction[i] == 'L'));
    }

    private boolean turnLU (int i){
        return  ((Direction[i + 1] == 'U') && (Direction[i - 1] == 'R')) ||
                ((Direction[i - 1] == 'D') && (Direction[i + 1] == 'L')) ||
                ((Direction[i + 1] == 'R') && (Direction[i - 1] == 'R') && (Direction[i] == 'U')) ||
                ((Direction[i + 1] == 'D') && (Direction[i - 1] == 'D') && (Direction[i] == 'L')) ||
                ((Direction[i + 1] == 'L') && (Direction[i - 1] == 'R') && (Direction[i] == 'U')) ||
                ((Direction[i + 1] == 'U') && (Direction[i - 1] == 'D') && (Direction[i] == 'L'));
    }

    public int getTurnDirection(int i){
        if (turnLU(i))
            return 0;
        if (turnRU(i))
            return 1;
        if (turnRD(i))
            return 2;
        if (turnLD(i))
            return 3;
        return -1;
    }

    public void checkFalseTurn (){
        if (Direction[0] == Direction[1])
            turn[1] = false;
    }
}
