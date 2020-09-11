package markodojkic.warships;

import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Марко Дојкић
 */

public class WarShips {

    public static globalVariables gVars = new globalVariables();
    public static boolean autoPopulate = false;

    public static void mainMenu() {
        System.out.println("\nYou`re currently in MainMenu - WarShips by Marko Dojkić");
        System.out.print("Type 1 and hit enter to start new game: ");
        gVars.currentUserInput = gVars.inputScanner.nextLine();
        if (!gVars.currentUserInput.matches("^1$")) {
            System.exit(0);
        } else {
            System.out.println("\nNew game started! You can exit game at any time by typing -1! - WarShips by Marko Dojkić");
            gVars.inGame = true;
            gVars.commandShips = 0;
            gVars.smallShips = 0;
            gVars.bigShips = 0;
            gVars.smallCargos = 0;
            gVars.bigCargos = 0;
            gVars.sShips.clear();
            gVars.bCargos.clear();
            gVars.bShips.clear();
            gVars.sCargos.clear();
            gVars.cShips.clear();
        }
    }

    public static void createObjects(int number, String className) {
        for (int i = 0; i < number; i++) {

            if (autoPopulate) //for ship automatic creation
                gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * 19));
            else {
                System.out.print("Enter the speed of " + (i + 1) + ". " + className + " (integer between 1 and " + Integer.MAX_VALUE + "): ");
                checkUserInput(1, Integer.MAX_VALUE, -1);
            }

            switch (className) {
                case "SmallShip":
                    SmallShip newSShip = new SmallShip(Integer.parseInt(gVars.currentUserInput));
                    gVars.sShips.put("@" + System.identityHashCode(newSShip), newSShip);
                    break;
                case "BigShip":
                    BigShip newBShip = new BigShip(Integer.parseInt(gVars.currentUserInput));
                    gVars.bShips.put("@" + System.identityHashCode(newBShip), newBShip);
                    break;
                case "CommandShip":
                    CommandShip newCShip = new CommandShip(Integer.parseInt(gVars.currentUserInput));
                    gVars.cShips.put("@" + System.identityHashCode(newCShip), newCShip);
                    break;
                case "SmallCargo":
                    SmallCargo newSCargo = new SmallCargo(Integer.parseInt(gVars.currentUserInput));
                    gVars.sCargos.put("@" + System.identityHashCode(newSCargo), newSCargo);
                    break;
                case "BigCargo":
                    BigCargo newBCargo = new BigCargo(Integer.parseInt(gVars.currentUserInput));
                    gVars.bCargos.put("@" + System.identityHashCode(newBCargo), newBCargo);
                    break;
            }
        }
    }

    public static void checkUserInput(int downLimit, int upperLimit, int exception) {
        if(autoPopulate)
            gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * (upperLimit - downLimit) + downLimit));
        else {
            gVars.currentUserInput = gVars.inputScanner.nextLine().trim();
            if(gVars.currentUserInput.isEmpty()) gVars.currentUserInput = "-2";
            while (Integer.parseInt(gVars.currentUserInput) < downLimit || Integer.parseInt(gVars.currentUserInput) > upperLimit || Integer.parseInt(gVars.currentUserInput) == exception) {
                if (gVars.currentUserInput.matches("^-1")) {
                    gVars.inGame = false;
                    gVars.currentUserInput = null;
                    mainMenu();
                    break;
                } else {
                    System.out.print("Please enter integer between " + downLimit + " and " + (upperLimit == 0 ? Integer.MAX_VALUE : upperLimit) + " (" + (exception == -1 ? "input -1 to quit)" : "except " + exception + " or input -1 to quit)") + ": ");
                    gVars.currentUserInput = gVars.inputScanner.nextLine();
                }
            }
        }
    }

    public static boolean checkCargo(CommandShip cargoCS) {
        return cargoCS.getFleet().stream().anyMatch((checkingShip) -> ((checkingShip.getClass().getTypeName().endsWith("BigCargo") && gVars.bCargos.containsKey("@" + System.identityHashCode(checkingShip))) || (checkingShip.getClass().getTypeName().endsWith("SmallCargo") && gVars.sCargos.containsKey("@" + System.identityHashCode(checkingShip)))));
    }

    public static void commitAttack(BattleShip atkShip, CommandShip defShip) {
        //def ship fleet to individual ships

        if (defShip.getFleet().isEmpty()) { //attack commandship itself
            if (atkShip.getSpeed() < defShip.getSpeed()) {
                System.out.println("Attack failed, your ship were too slow!");
            } else {
                int oldHealth = defShip.getHealth();
                for (int i = 0; i <= (atkShip.getSpeed() - defShip.getSpeed()); i++) {
                    atkShip.Attack(defShip);
                }
                System.out.println("Attack successfull - Dealt damage: " + (oldHealth - defShip.getHealth()));
                if (defShip.getHealth() <= 0) {
                    System.out.println(System.identityHashCode(defShip) + " is destroyed! Player eliminated!");
                    gVars.rangList.add("ID: " + System.identityHashCode(defShip));
                    gVars.commandShips--;
                    gVars.cShips.remove("@" + System.identityHashCode(defShip));
                    gVars.cShipsArray.remove(defShip);
                }
            }
        }
        else if (checkCargo(defShip)) { //firstly attack cargo ships
            System.out.println("Your opponent has some cargo ships, they need to be destroyed first!");
            gVars.availableShipsToAttack.clear();
            defShip.getFleet().stream().filter((cS) -> (cS.getClass().getTypeName().endsWith("SmallCargo") || cS.getClass().getTypeName().endsWith("BigCargo"))).peek((cS) -> gVars.availableShipsToAttack.add(cS)).forEachOrdered((cS) -> System.out.println(cS.toString().split("@")[1] + " (Health: " + cS.getHealth() + ")"));
            if (gVars.availableShipsToAttack.size() == 1) {
                Cargo cShip = (Cargo) gVars.availableShipsToAttack.get(0);

                if (atkShip.getSpeed() < cShip.getSpeed()) {
                    System.out.println("Attack failed, your ship were too slow!");
                } else {

                    int oldHealth = cShip.getHealth();

                    for (int i = 0; i <= (atkShip.getSpeed() - cShip.getSpeed()); i++) {
                        atkShip.Attack(cShip);
                    }
                    System.out.println("Attack successfull - Dealt damage: " + (oldHealth - cShip.getHealth()));
                    if (cShip.getHealth() <= 0) {
                        try {
                            Thread.sleep(cShip.getCapacity() * 1000); //attack is waiting to finish based on capacity
                            System.out.println(cShip.getClass().getSimpleName()+ " is destroyed!");
                            defShip.getFleet().remove(cShip);
                            switch (cShip.toString().substring(0, cShip.toString().length() - 1)) {
                                case "SmallCargo":
                                    gVars.sCargos.remove("@" + System.identityHashCode(cShip));
                                    gVars.smallCargos--;
                                    break;
                                case "BigCargo":
                                    gVars.bCargos.remove("@" + System.identityHashCode(cShip));
                                    gVars.bigCargos--;
                                    break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            else {
                System.out.print("Enter number of cargo ship [0-" + (gVars.availableShipsToAttack.size() - 1) + "]: ");
                checkUserInput(0, gVars.availableShipsToAttack.size() - 1, -1);
                Cargo cShip = (Cargo) gVars.availableShipsToAttack.get(Integer.parseInt(gVars.currentUserInput));
                if (atkShip.getSpeed() < cShip.getSpeed()) {
                    System.out.println("Attack failed, your ship were too slow!");
                } else {

                    int oldHealth = cShip.getHealth();

                    for (int i = 0; i <= (atkShip.getSpeed() - cShip.getSpeed()); i++) {
                        atkShip.Attack(cShip);
                    }
                    System.out.println("Attack successfull - Dealt damage: " + (oldHealth - cShip.getHealth()));
                    if (cShip.getHealth() <= 0) {
                        try {
                            Thread.sleep(cShip.getCapacity() * 1000);
                            System.out.println(cShip.getClass().getSimpleName()+ " is destroyed!");
                            defShip.getFleet().remove(cShip);
                            switch (cShip.toString().substring(0, cShip.toString().length() - 1)) {
                                case "SmallCargo":
                                    gVars.sCargos.remove("@" + System.identityHashCode(cShip));
                                    gVars.smallCargos--;
                                    break;
                                case "BigCargo":
                                    gVars.bCargos.remove("@" + System.identityHashCode(cShip));
                                    gVars.bigCargos--;
                                    break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else { //cargo ships destroyed - attack battleships
            System.out.println("Your opponent has some battle ships, they need to be destroyed first!");
            gVars.availableShipsToAttack.clear();
            defShip.getFleet().stream().filter((bS) -> (bS.getClass().getTypeName().endsWith("SmallShip") || bS.getClass().getTypeName().endsWith("BigShip"))).peek((bS) -> gVars.availableShipsToAttack.add(bS)).forEachOrdered((bS) -> System.out.println(bS.toString().split("@")[1]  + " (Health: " + bS.getHealth() + ")"));
            if (gVars.availableShipsToAttack.size() == 1) {
                BattleShip bShip = (BattleShip) gVars.availableShipsToAttack.get(0);

                if (atkShip.getSpeed() < bShip.getSpeed()) {
                    System.out.println("Attack failed, your ship were too slow!");
                } else {

                    int oldHealth = bShip.getHealth();
                    for (int i = 0; i <= (atkShip.getSpeed() - bShip.getSpeed()); i++) {
                        atkShip.Attack(bShip);
                    }
                    System.out.println("Attack successfull - Dealt damage: " + (oldHealth - bShip.getHealth()));
                    if (bShip.getHealth() <= 0) {
                        defShip.getFleet().remove(bShip);
                        System.out.println(bShip.getClass().getSimpleName()+ " is destroyed!");
                        switch (bShip.toString().substring(0, bShip.toString().length() - 1)) {
                            case "SmallShip":
                                gVars.sShips.remove("@" + System.identityHashCode(bShip));
                                gVars.smallShips--;
                                break;
                            case "BigShip":
                                gVars.bShips.remove("@" + System.identityHashCode(bShip));
                                gVars.bigShips--;
                                break;
                        }
                    }
                }

            } else {
                System.out.print("Enter number of battle ship [0-" + (gVars.availableShipsToAttack.size() - 1) + "]: ");
                checkUserInput(0, gVars.availableShipsToAttack.size() - 1, -1);
                BattleShip bShip = (BattleShip) gVars.availableShipsToAttack.get(Integer.parseInt(gVars.currentUserInput));
                if (atkShip.getSpeed() < bShip.getSpeed()) {
                    System.out.println("Attack failed, your ship were too slow!");
                } else {

                    int oldHealth = bShip.getHealth();
                    for (int i = 0; i <= (atkShip.getSpeed() - bShip.getSpeed()); i++) {
                        atkShip.Attack(bShip);
                    }
                    System.out.println("Attack successfull - Dealt damage: " + (oldHealth - bShip.getHealth()));
                    if (bShip.getHealth() <= 0) {
                        defShip.getFleet().remove(bShip);
                        System.out.println(bShip.getClass().getSimpleName()+ " is destroyed!");
                        switch (bShip.toString().substring(0, bShip.toString().length() - 1)) {
                            case "SmallShip":
                                gVars.sShips.remove("@" + System.identityHashCode(bShip));
                                gVars.smallShips--;
                                break;
                            case "BigShip":
                                gVars.bShips.remove("@" + System.identityHashCode(bShip));
                                gVars.bigShips--;
                                break;
                        }
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("Welcome to battle ship game!");

        mainMenu();

        while (gVars.inGame) {
            System.out.println("\nLet`s firstly create command ships!");
            System.out.print("Enter number of command ships in game (a.k.a players) [2+]: ");
            checkUserInput(2, Integer.MAX_VALUE, -1);
            gVars.commandShips = Integer.parseInt(gVars.currentUserInput);
            System.out.print("If you want to create ships automatically type 1 and hit enter: ");
            gVars.currentUserInput = gVars.inputScanner.nextLine();
            if (gVars.currentUserInput.matches("^1$")) autoPopulate = true;

            gVars.currentUserInput = null;
            createObjects(gVars.commandShips, "CommandShip");

            for (HashMap.Entry<String, CommandShip> currentCShip : gVars.cShips.entrySet()) {
                System.out.println("\nNow let`s create small battle ships for " + currentCShip.getValue().toString() + ". command ship!");
                System.out.print("Enter number of small battle ships [1-15]: ");
                System.out.print(autoPopulate);
                checkUserInput(1, 15, -1);
                gVars.smallShips = Integer.parseInt(gVars.currentUserInput);
                createObjects(gVars.smallShips, "SmallShip");
                for (HashMap.Entry<String, SmallShip> fShip : gVars.sShips.entrySet()) {
                    currentCShip.getValue().assignToFleet(fShip.getValue());
                }
                System.out.println("\nNow let`s create big battle ships for " + currentCShip.getValue().toString() + ". command ship!");
                System.out.print("Enter number of big battle ships [1-5]: ");
                checkUserInput(1, 5, -1);
                gVars.bigShips = Integer.parseInt(gVars.currentUserInput);
                createObjects(gVars.bigShips, "BigShip");
                for (HashMap.Entry<String, BigShip> fShip : gVars.bShips.entrySet()) {
                    currentCShip.getValue().assignToFleet(fShip.getValue());
                }
                System.out.println("\nNow let`s create small cargo ships for " + currentCShip.getValue().toString() + ". command ship!");
                System.out.print("Enter number of small cargo ships [1-10]: ");
                checkUserInput(1, 10, -1);
                gVars.smallCargos = Integer.parseInt(gVars.currentUserInput);
                createObjects(gVars.smallCargos, "SmallCargo");
                for (HashMap.Entry<String, SmallCargo> fShip : gVars.sCargos.entrySet()) {
                    currentCShip.getValue().assignToFleet(fShip.getValue());
                }
                System.out.println("\nNow let`s create big cargo ships for " + currentCShip.getValue().toString() + ". command ship!");
                System.out.print("Enter number of big cargo ships [1-3]: ");
                checkUserInput(1, 3, -1);
                gVars.bigCargos = Integer.parseInt(gVars.currentUserInput);
                createObjects(gVars.bigCargos, "BigCargo");
                for (HashMap.Entry<String, BigCargo> fShip : gVars.bCargos.entrySet()) {
                    currentCShip.getValue().assignToFleet(fShip.getValue());
                }
            }
            autoPopulate = false;
            System.out.println("\nPlayers in game:");

            for (HashMap.Entry<String, CommandShip> currentCShip : gVars.cShips.entrySet()) {
                System.out.println(currentCShip.getValue().toString());
                currentCShip.getValue().getFleet().sort(Comparator.comparingInt(Ship::getSpeed));
            }

            gVars.cShipsArray.addAll(gVars.cShips.values());
            gVars.cShipsArray.sort(Comparator.comparingInt(CommandShip::getSpeed).reversed());

            while (gVars.commandShips != 1) {
                for (CommandShip userCommandShip : gVars.cShipsArray) {
                    for (Ship attackingShip : userCommandShip.getFleet()) {
                        if (!attackingShip.getClass().getName().endsWith("Cargo")) { //cargo ships cannot attack
                            System.out.println("---Player with ID: " + System.identityHashCode(attackingShip) + " is attacking!---");
                            for (CommandShip otherCommandShip : gVars.cShipsArray) {
                                if (userCommandShip != otherCommandShip) {
                                    System.out.println(System.identityHashCode(otherCommandShip) + " (Remaining ships in fleet: " + otherCommandShip.getFleet().size() + "): " + gVars.cShipsArray.indexOf(otherCommandShip));
                                }
                            }

                            int id;

                            if(gVars.commandShips == 2) id = 0; //only two players (attack other ship automatically)

                            else {
                                System.out.print("Enter number of ship to attack: ");
                                checkUserInput(0, gVars.commandShips - 1, gVars.cShipsArray.indexOf(userCommandShip));
                                id = Integer.parseInt(gVars.currentUserInput);
                            }
                            CommandShip defendingShip = gVars.cShips.get("@" + System.identityHashCode(gVars.cShipsArray.get(id)));
                            System.out.println("List of  " + defendingShip.toString() + " ships:");
                            commitAttack((BattleShip) attackingShip, defendingShip);
                        }
                    }
                }
            }

            //GAME OVER SHOW RANG LIST
            gVars.rangList.add(gVars.cShipsArray.get(0).toString());
            System.out.println("\n GAME OVER:\nResults:");
            int userRank = 1;
            for (String user : gVars.rangList) {
                System.out.println(userRank + ". " + user);
                userRank++;
            }
            System.out.print("\n<Press enter to return to main menu>: ");
            gVars.inputScanner.nextLine();
            mainMenu();

            //TODO: FIX System.identityHashCode (ITS NOT UNIQUE!)
        }
    }
}