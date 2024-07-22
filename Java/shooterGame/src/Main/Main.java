package Main;

import Start.*;
import Game.*;
import Inventory.*;

import java.awt.*;

public class Main {
    /* to do 
     * start/end screen
     * //! new color vs Color.
     * //! bullet pierce kills instantly
     * enemies drop fast spinning objects
     * black hole spins faster the more spinning objects you put in
     * shield power up
     * something cool with setbackground clear
     *  //! fix the c
     */

    public static GameFrame gameFrame;
    public static InventoryFrame inventoryFrame;

    public static final double G = 6.6743;

    public static boolean darkMode = false;
    public static Color playerColor = Color.BLACK, bulletColor = Color.BLACK, bombColor = Color.BLACK, holeColor = Color.BLACK;

    public static void main(String[] args) {
        new StartFrame();
    }
}