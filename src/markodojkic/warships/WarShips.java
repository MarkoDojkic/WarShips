package markodojkic.warships;

import java.util.*;

/**
 * @author Марко Дојкић
 * Project started 11/2019
 */

@SuppressWarnings("ALL")
public class WarShips {

    private static globalVariables gVars;
    private static boolean autoPopulate;
    private static String currentPlayerColor = "";

    public static void mainMenu() {
        System.out.print("\u001B[35m");
        System.out.println("\nYou`re currently in MainMenu - WarShips by Marko Dojkić");
        System.out.print("Type 1 and hit enter to start new game or -1 to quit: ");
        gVars.currentUserInput = gVars.inputScanner.nextLine();

        if (!gVars.currentUserInput.matches("^1$")) {
            System.out.print("\u001B[0m"); //revert to original color
            System.out.println("Thanks for playing WarShips by Marko Dojkić, hope to see you again soon.");
            System.exit(0);
        } else {
            System.out.print("\u001B[36m"); //cyan coloring when creating ships
            System.out.println("\n!!!New game started! You can exit at any time by typing -1!!!");
            gVars.inGame = true;
            autoPopulate = false;
            gVars.sShips.clear();
            gVars.bCargos.clear();
            gVars.bShips.clear();
            gVars.sCargos.clear();
            gVars.cShips.clear();
            gVars.rangList.clear();
            main(null);
        }
    }

    public static void createShips(int number, String className, CommandShip targetCShip) {
        for (int i = 0; i < number; i++) {
            String id;
            switch (className) {
                case "SmallShip":
                    id = "SmallShip #" + (gVars.sShips.size()+1);
                    if (autoPopulate) //for ship automatic creation
                        gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * 149 + 1));
                    else {
                        System.out.print("Enter the speed of " + (i + 1) + ". " + className + " (integer between 1 and 20): ");
                        checkUserInput(1, 150, -1);
                    }
                    gVars.sShips.put(id, new SmallShip(Integer.parseInt(gVars.currentUserInput)));
                    targetCShip.getsBShips().add(id);
                    break;
                case "BigShip":
                    id = "BigShip #" + (gVars.bShips.size()+1);
                    if (autoPopulate) //for ship automatic creation
                        gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * 349 + 1));
                    else {
                        System.out.print("Enter the speed of " + (i + 1) + ". " + className + " (integer between 1 and 20): ");
                        checkUserInput(1, 350, -1);
                    }
                    gVars.bShips.put(id, new BigShip(Integer.parseInt(gVars.currentUserInput)));
                    targetCShip.getbBShips().add(id);
                    break;
                case "CommandShip":
                    if (autoPopulate) //for ship automatic creation
                        gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * 199 + 1));
                    else {
                        System.out.print("Enter the speed of " + (i + 1) + ". " + className + " (integer between 1 and 20): ");
                        checkUserInput(1, 200, -1);
                    }
                    gVars.cShips.put("Player #" + (i + 1), new CommandShip(Integer.parseInt(gVars.currentUserInput)));
                    break;
                case "SmallCargo":
                    id = "SmallCargo #" + (gVars.sCargos.size()+1);
                    if (autoPopulate) //for ship automatic creation
                        gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * 99 + 1));
                    else {
                        System.out.print("Enter the speed of " + (i + 1) + ". " + className + " (integer between 1 and 20): ");
                        checkUserInput(1, 100, -1);
                    }
                    gVars.sCargos.put(id, new SmallCargo(Integer.parseInt(gVars.currentUserInput)));
                    targetCShip.getsCargos().add(id);
                    break;
                case "BigCargo":
                    id = "BigCargo #" + (gVars.bCargos.size()+1);
                    if (autoPopulate) //for ship automatic creation
                        gVars.currentUserInput = Integer.toString((int) Math.ceil(Math.random() * 199 + 1));
                    else {
                        System.out.print("Enter the speed of " + (i + 1) + ". " + className + " (integer between 1 and 20): ");
                        checkUserInput(1, 200, -1);
                    }
                    gVars.bCargos.put(id, new BigCargo(Integer.parseInt(gVars.currentUserInput)));
                    targetCShip.getbCargos().add(id);
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
                    String including = downLimit == -1 ? "INCLUDING!" : "";
                    System.out.print("Please enter integer between " + downLimit
                            + " and " + (upperLimit == 0 ? Integer.MAX_VALUE : upperLimit)
                            + including + " (" + (exception == -1 ? "input -1 to quit)" : "except "
                            + exception + " or input -1 to quit)") + ": ");
                    System.out.println();
                    gVars.currentUserInput = gVars.inputScanner.nextLine();
                }
            }
        }
    }

    public static void commitAttack(BattleShip atkShip, String defShip_id) {
        CommandShip defShip = gVars.cShips.get(defShip_id);
        String defendingShip_id = null;
        if(defShip.getsCargos().size() > 0){

            System.out.println("**Your opponent has some small cargo ships, they need to be destroyed first!**");
            if(defShip.getsCargos().size() == 1) defendingShip_id = defShip.getsCargos().get(0);
            else {
                if (!autoPopulate) System.out.print("Enter number of small cargo ship [0-" + (defShip.getsCargos().size()-1) + "]: ");
                checkUserInput(0,defShip.getsCargos().size()-1,-1);
                defendingShip_id = defShip.getsCargos().get(Integer.parseInt(gVars.currentUserInput));
            }

            if (atkShip.getSpeed() < gVars.sCargos.get(defendingShip_id).getSpeed()) System.out.println("---Attack failed, your ship were too slow!---");
            else {

                int oldHealth = gVars.sCargos.get(defendingShip_id).getHealth();

                for (int i = 0; i <= (atkShip.getSpeed() - gVars.sCargos.get(defendingShip_id).getSpeed()); i++) {
                    atkShip.Attack(gVars.sCargos.get(defendingShip_id));
                }
                System.out.println("+++Attack successfull - Dealt damage: " + (oldHealth - gVars.sCargos.get(defendingShip_id).getHealth()) + "+++");
                if (gVars.sCargos.get(defendingShip_id).getHealth() <= 0) {
                    try {
                        if(!autoPopulate) Thread.sleep(gVars.sCargos.get(defendingShip_id).getCapacity()); //destroying after time based on capacity
                        if(defShip.getsCargos().size() == 1) System.out.println("***" + defShip.getsCargos().get(0) + " is destroyed!***");
                        else System.out.println("***" + defShip.getsCargos().get(Integer.parseInt(gVars.currentUserInput)) + " is destroyed!***");
                        defShip.getsCargos().remove(defendingShip_id);
                        gVars.sCargos.remove(defendingShip_id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(defShip.getbCargos().size() > 0){
            System.out.println("**Your opponent has some big cargo ships, they need to be destroyed first!**");
            if(defShip.getbCargos().size() == 1) defendingShip_id = defShip.getbCargos().get(0);
            else {
                if (!autoPopulate) System.out.print("Enter number of big cargo ship [0-" + (defShip.getbCargos().size()-1) + "]: ");
                checkUserInput(0,defShip.getbCargos().size()-1,-1);
                defendingShip_id = defShip.getbCargos().get(Integer.parseInt(gVars.currentUserInput));
            }

            if (atkShip.getSpeed() < gVars.bCargos.get(defendingShip_id).getSpeed()) System.out.println("---Attack failed, your ship were too slow!---");
            else {

                int oldHealth = gVars.bCargos.get(defendingShip_id).getHealth();

                for (int i = 0; i <= (atkShip.getSpeed() - gVars.bCargos.get(defendingShip_id).getSpeed()); i++) {
                    atkShip.Attack(gVars.bCargos.get(defendingShip_id));
                }
                System.out.println("+++Attack successfull - Dealt damage: " + (oldHealth - gVars.bCargos.get(defendingShip_id).getHealth()) + "+++");
                if (gVars.bCargos.get(defendingShip_id).getHealth() <= 0) {
                    try {
                        if(!autoPopulate) Thread.sleep(gVars.bCargos.get(defendingShip_id).getCapacity()); //destroying after time based on capacity
                        if(defShip.getbCargos().size() == 1) System.out.println("***" + defShip.getbCargos().get(0) + " is destroyed!***");
                        else System.out.println("***" + defShip.getbCargos().get(Integer.parseInt(gVars.currentUserInput)) + " is destroyed!***");
                        defShip.getbCargos().remove(defendingShip_id);
                        gVars.bCargos.remove(defendingShip_id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if(defShip.getsBShips().size() > 0){
            System.out.println("Your opponent has some small battle ships, they need to be destroyed first!");
            if(defShip.getsBShips().size() == 1) defendingShip_id = defShip.getsBShips().get(0);
            else {
                if (!autoPopulate) System.out.print("Enter number of small battle ship [0-" + (defShip.getsBShips().size()-1) + "]: ");
                checkUserInput(0,defShip.getsBShips().size()-1,-1);
                defendingShip_id = defShip.getsBShips().get(Integer.parseInt(gVars.currentUserInput));
            }

            if (atkShip.getSpeed() < gVars.sShips.get(defendingShip_id).getSpeed()) System.out.println("---Attack failed, your ship were too slow!---");
            else {

                int oldHealth = gVars.sShips.get(defendingShip_id).getHealth();

                for (int i = 0; i <= (atkShip.getSpeed() - gVars.sShips.get(defendingShip_id).getSpeed()); i++) {
                    atkShip.Attack(gVars.sShips.get(defendingShip_id));
                }
                System.out.println("+++Attack successfull - Dealt damage: " + (oldHealth - gVars.sShips.get(defendingShip_id).getHealth()) + "+++");
                if (gVars.sShips.get(defendingShip_id).getHealth() <= 0) {
                    if(defShip.getsBShips().size() == 1) System.out.println("***" + defShip.getsBShips().get(0) + " is destroyed!***");
                    else System.out.println("***" + defShip.getsBShips().get(Integer.parseInt(gVars.currentUserInput)) + " is destroyed!***");
                    defShip.getsBShips().remove(defendingShip_id);
                    gVars.sShips.remove(defendingShip_id);
                }
            }
        }
        else if(defShip.getbBShips().size() > 0){
            System.out.println("Your opponent has some big battle ships, they need to be destroyed first!");
            if(defShip.getbBShips().size() == 1) defendingShip_id = defShip.getbBShips().get(0);
            else {
                if (!autoPopulate) System.out.print("Enter number of big battle ship [0-" + (defShip.getsBShips().size()-1) + "]: ");
                checkUserInput(0,defShip.getbBShips().size()-1,-1);
                defendingShip_id = defShip.getbBShips().get(Integer.parseInt(gVars.currentUserInput));
            }

            if (atkShip.getSpeed() < gVars.bShips.get(defendingShip_id).getSpeed()) System.out.println("---Attack failed, your ship were too slow!---");
            else {

                int oldHealth = gVars.bShips.get(defendingShip_id).getHealth();

                for (int i = 0; i <= (atkShip.getSpeed() - gVars.bShips.get(defendingShip_id).getSpeed()); i++) {
                    atkShip.Attack(gVars.bShips.get(defendingShip_id));
                }
                System.out.println("+++Attack successfull - Dealt damage: " + (oldHealth - gVars.bShips.get(defendingShip_id).getHealth()) + "+++");
                if (gVars.bShips.get(defendingShip_id).getHealth() <= 0) {
                    if(defShip.getbBShips().size() == 1) System.out.println("***" + defShip.getbBShips().get(0) + " is destroyed!***");
                    else System.out.println("***" + defShip.getbBShips().get(Integer.parseInt(gVars.currentUserInput)) + " is destroyed!***");
                    defShip.getbBShips().remove(defendingShip_id);
                    gVars.bShips.remove(defendingShip_id);
                }
            }
        }
        else {
            if (atkShip.getSpeed() < defShip.getSpeed()) {
                System.out.println("---Attack failed, your ship were too slow!---");
            } else {
                int oldHealth = defShip.getHealth();
                for (int i = 0; i <= (atkShip.getSpeed() - defShip.getSpeed()); i++) {
                    atkShip.Attack(defShip);
                }
                System.out.println("+++Attack successfull - Dealt damage: " + (oldHealth - defShip.getHealth()) + "+++");
                if (defShip.getHealth() <= 0) {
                    System.out.println("***"+ defShip_id + " 's ship is destroyed! Player eliminated!***");
                    gVars.rangList.add(defShip_id + " { " + defShip.toString() + " }");
                    gVars.cShips.remove(defShip_id);
                }
            }
        }
    }

    public static void printOtherPlayers(String current) {
        for(String p_id : gVars.cShips.keySet()){
            if(current!=p_id) System.out.println(p_id + ":" + gVars.cShips.get(p_id).toString());
        }
    }

    public static void checkIfCommandShipExists(String current){

        gVars.currentUserInput = null;
        Object[] avaiableShips = gVars.cShips.keySet().toArray();

        if(avaiableShips.length==2) { //if there is only two players - choose automatically other
            gVars.currentUserInput = avaiableShips[0].toString().equals(current)
                    ? avaiableShips[1].toString() : avaiableShips[0].toString();
            return;
        }
        else if(avaiableShips.length == 1) { // no ships
            gVars.currentUserInput = null;
            return;
        }

        if (!autoPopulate) printOtherPlayers(current);
        if (!autoPopulate) System.out.print("Enter number of ship to attack[0-" + (avaiableShips.length-1) + "]: ");
        checkUserInput(-1,avaiableShips.length-1, -1);
        gVars.currentUserInput = avaiableShips[Integer.parseInt(gVars.currentUserInput)].toString();
        if(!gVars.cShips.containsKey(gVars.currentUserInput) || current.equals(gVars.currentUserInput)){
            if (!autoPopulate) System.out.print("Invalid command ship id. Enter again: ");
            checkIfCommandShipExists(current);
        }
    }

    public static void main(String[] args) {

        if(gVars == null) gVars = new globalVariables();

        if(!gVars.inGame) {
            System.out.print("\u001B[35m"); //purple color when not in game
            System.out.println("Welcome to WarShips by Marko Dojkić!");
            mainMenu();
        }

        while (gVars.inGame) {
            System.out.println("\nLet`s firstly create command ships!");
            System.out.print("Enter number of command ships in game (a.k.a players) [2+]: ");
            checkUserInput(2, Integer.MAX_VALUE, -1);
            int players = Integer.parseInt(gVars.currentUserInput);

            System.out.print("If you want to create ships automatically type 1 and hit enter: ");
            gVars.currentUserInput = gVars.inputScanner.nextLine();
            if (gVars.currentUserInput.matches("^1$")) autoPopulate = true;

            gVars.currentUserInput = null;
            createShips(players, "CommandShip", null);

            for (HashMap.Entry<String, CommandShip> currentCShip : gVars.cShips.entrySet()) {
                if(!autoPopulate) System.out.println("\nNow let`s create small battle ships for " + currentCShip.getValue().toString() + ". command ship!");
                if(!autoPopulate) System.out.print("Enter number of small battle ships [1-15]: ");
                checkUserInput(1, 15, -1);
                createShips(Integer.parseInt(gVars.currentUserInput), "SmallShip", currentCShip.getValue());

                if(!autoPopulate) System.out.println("\nNow let`s create big battle ships for " + currentCShip.getValue().toString() + ". command ship!");
                if(!autoPopulate) System.out.print("Enter number of big battle ships [1-5]: ");
                checkUserInput(1, 5, -1);
                createShips(Integer.parseInt(gVars.currentUserInput), "BigShip",currentCShip.getValue());

                if(!autoPopulate) System.out.println("\nNow let`s create small cargo ships for " + currentCShip.getValue().toString() + ". command ship!");
                if(!autoPopulate) System.out.print("Enter number of small cargo ships [1-10]: ");
                checkUserInput(1, 10, -1);
                createShips(Integer.parseInt(gVars.currentUserInput), "SmallCargo",currentCShip.getValue());

                if(!autoPopulate) System.out.println("\nNow let`s create big cargo ships for " + currentCShip.getValue().toString() + ". command ship!");
                if(!autoPopulate) System.out.print("Enter number of big cargo ships [1-3]: ");
                checkUserInput(1, 3, -1);
                createShips(Integer.parseInt(gVars.currentUserInput), "BigCargo", currentCShip.getValue());
            }

            System.out.print("If you wanna computer to play game by itself input 0 (otherwise click enter): ");
            gVars.currentUserInput = gVars.inputScanner.nextLine();
            if(!gVars.currentUserInput.matches("^0")) autoPopulate = false;

            System.out.println("\n--Players in game:--");
            printOtherPlayers(null);
            Set<String> player_keys = new HashSet<>(); //*
            player_keys.addAll(gVars.cShips.keySet());

            while (gVars.cShips.size() > 1) {
                for (String userCommandShip : player_keys) {
                    //Coloring for different player when they play:

                    switch (currentPlayerColor){ //white, red, yellow, green
                        case "\u001B[37m": currentPlayerColor = "\u001B[31m"; break;
                        case "\u001B[31m": currentPlayerColor = "\u001B[33m"; break;
                        case "\u001B[33m": currentPlayerColor = "\u001B[32m"; break;
                        default: currentPlayerColor = "\u001B[37m"; break;
                    }

                    System.out.print(currentPlayerColor);

                    if(gVars.cShips.size() == 1) break;
                    //*next four lines are to create copy of arrays to avoid java.util.ConcurrentModificationException
                    if(!gVars.cShips.containsKey(userCommandShip)) continue;
                    ArrayList<String> smallBattleShips_copy = new ArrayList<>(), bigBattleShips_copy = new ArrayList<>();
                    smallBattleShips_copy.addAll(gVars.cShips.get(userCommandShip).getsBShips());
                    bigBattleShips_copy.addAll(gVars.cShips.get(userCommandShip).getbBShips());

                    System.out.println("*****" + userCommandShip + " is attacking!*****");

                    for (String userSmallBattleShip : smallBattleShips_copy) {
                        if(!gVars.cShips.get(userCommandShip).getsBShips().contains(userSmallBattleShip)) continue; //here we are checking if it is somehow destroyed
                        if(gVars.cShips.size() != 1) {
                            System.out.println("Attacking with " + userSmallBattleShip);
                            checkIfCommandShipExists(userCommandShip);
                            commitAttack(gVars.sShips.get(userSmallBattleShip), gVars.currentUserInput);
                        }
                    }

                    for(String userBigBattleShip : bigBattleShips_copy){
                        if(!gVars.cShips.get(userCommandShip).getbBShips().contains(userBigBattleShip)) continue; //here we are checking if it is somehow destroyed
                        if(gVars.cShips.size() != 1) {
                            System.out.println("Attacking with " + userBigBattleShip);
                            checkIfCommandShipExists(userCommandShip);
                            commitAttack(gVars.bShips.get(userBigBattleShip), gVars.currentUserInput);
                        }
                    }

                    checkIfCommandShipExists(userCommandShip);
                    System.out.println("Attacking with CommandShip");
                    if(gVars.currentUserInput == null) break;
                    commitAttack(gVars.cShips.get(userCommandShip), gVars.currentUserInput);
                }
            }

            //GAME OVER SHOW RANG LIST
            System.out.print("\u001B[34m"); //blue color for rank list
            gVars.rangList.add(gVars.cShips.keySet().toArray()[0].toString() + " { " + gVars.cShips.get(gVars.cShips.keySet().toArray()[0]).toString() + " }");
            Collections.reverse(gVars.rangList); //last in is 1. one
            System.out.println("\n*****GAME OVER:*****\n-Results:-");
            int userRank = 1;
            for (String user : gVars.rangList) {
                System.out.println(userRank + ". " + user);
                userRank++;
            }
            mainMenu();
        }
    }
}