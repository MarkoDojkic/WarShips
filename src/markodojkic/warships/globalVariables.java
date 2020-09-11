package markodojkic.warships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class globalVariables {
    Scanner inputScanner;
    boolean inGame;
    int commandShips, smallShips, bigShips, smallCargos, bigCargos;
    String currentUserInput;
    HashMap<String, SmallShip> sShips;
    HashMap<String, BigShip> bShips;
    HashMap<String, CommandShip> cShips;
    HashMap<String, SmallCargo> sCargos;
    HashMap<String, BigCargo> bCargos;
    ArrayList<CommandShip> cShipsArray;
    ArrayList<Ship> availableShipsToAttack;
    ArrayList<String> rangList;

    public globalVariables() {
        this.inputScanner = new Scanner(System.in);
        this.inGame = false;
        this.commandShips = 0;
        this.smallShips = 0;
        this.bigShips = 0;
        this.smallCargos = 0;
        this.bigCargos = 0;
        this.currentUserInput = null;
        this.sShips = new HashMap<>();
        this.bShips = new HashMap<>();
        this.cShips = new HashMap<>();
        this.sCargos = new HashMap<>();
        this.bCargos = new HashMap<>();
        this.cShipsArray = new ArrayList<>();
        this.availableShipsToAttack = new ArrayList<>();
        this.rangList = new ArrayList<>();
    }
}