package markodojkic.warships;

import java.util.*;

public class globalVariables {
    Scanner inputScanner;
    boolean inGame;
    String currentUserInput;
    HashMap<String, SmallShip> sShips;
    HashMap<String, BigShip> bShips;
    HashMap<String, CommandShip> cShips;
    HashMap<String, SmallCargo> sCargos;
    HashMap<String, BigCargo> bCargos;
    ArrayList<String> rangList;

    public globalVariables() {
        this.inputScanner = new Scanner(System.in);
        this.inGame = false;
        this.currentUserInput = null;
        this.sShips = new HashMap<>();
        this.bShips = new HashMap<>();
        this.cShips = new HashMap<>();
        this.sCargos = new HashMap<>();
        this.bCargos = new HashMap<>();
        this.rangList = new ArrayList<>();
    }
}