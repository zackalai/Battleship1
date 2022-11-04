package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public enum Player {
        P1, P2
    }

    final int numberOfRow = 10;
    final int numberOfCol = 10;

    List<Character> rowLetters = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J');

    final int numberOfShips = 5;

    final int aircraftCarrierCells = 5;
    final int battleshipCells = 4;
    final int submarineCells = 3;
    final int cruiserCells = 3;
    final int destroyerCells = 2;

    List<String[]> shipsPositionP1 = new ArrayList<>();
    List<String[]> shipsPositionP2 = new ArrayList<>();

    char[][] mapP1 = new char[numberOfRow][numberOfCol];
    char[][] secondMapP1 = new char[numberOfRow][numberOfCol];

    char[][] mapP2 = new char[numberOfRow][numberOfCol];
    char[][] secondMapP2 = new char[numberOfRow][numberOfCol];

    public static void main(String[] args) {
        Main m = new Main();
        m.getInput();
        m.gameStart();
    }

    public void getInput() {

        filledMap();
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        printBattleMap(Player.P1);
        for (int i = 1; i <= numberOfShips; i++) {
            positionShips(i, Player.P1);
        }

        System.out.println();
        enterToChangePlayer();

        System.out.println("Player 2, place your ships to the game field");
        System.out.println();
        printBattleMap(Player.P2);
        for (int i = 1; i <= numberOfShips; i++) {
            positionShips(i, Player.P2);
        }

        System.out.println();
        enterToChangePlayer();
    }

    private void filledMap() {
        for (char[] row : mapP1) {
            Arrays.fill(row, '~');
        }

        for (char[] row : secondMapP1) {
            Arrays.fill(row, '~');
        }

        for (char[] row : mapP2) {
            Arrays.fill(row, '~');
        }

        for (char[] row : secondMapP2) {
            Arrays.fill(row, '~');
        }
    }

    public void printBattleMap(Player p) {
//        System.out.println();

        System.out.print(" ");
        for (int i = 1; i <= numberOfCol; i++) {
            System.out.print(" " + i);
        }

        System.out.println();
        for (int i = 0; i < numberOfRow; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < numberOfCol; j++) {

                switch (p) {
                    case P1:
                        System.out.print(" " + mapP1[i][j]);
                        break;
                    case P2:
                        System.out.print(" " + mapP2[i][j]);
                        break;
                }

            }
            System.out.println();
        }
    }

    private void gameStart() {



        Boolean[] isShipSankP1 = {false, false, false, false, false};

        Boolean[] isShipSankP2 = {false, false, false, false, false};

        Integer counterShipSankP1 = 0;
        Integer counterShipSankP2 = 0;

        while (true) {

            for (int i = 0; i < numberOfShips; i++) {
                if (isShipSankP2[i] == true) {
                    counterShipSankP2++;
                }
            }
//            System.out.println("P2 " + counterShipSankP2);
            checkAndPrintPlayerInput(Player.P1, isShipSankP2, counterShipSankP2);
            enterToChangePlayer();

            for (int i = 0; i < numberOfShips; i++) {
                if (isShipSankP1[i] == true) {
                    counterShipSankP1++;
                }
            }

//            System.out.println("P1 " + counterShipSankP1);
            checkAndPrintPlayerInput(Player.P2, isShipSankP1, counterShipSankP1);
            enterToChangePlayer();
            counterShipSankP1 = 0;
            counterShipSankP2 = 0;
        }

    }

    private void enterToChangePlayer() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner enter = new Scanner(System.in);
        String input = enter.nextLine();
    }

    private void checkAndPrintPlayerInput(Player p, Boolean[] isShipsSank, Integer counterShipSankPam) {
        String player = "";
        List<String []> shipsPosition = null;
        char[][] map =  new char[numberOfRow][numberOfCol];
        char[][] secondMap =  new char[numberOfRow][numberOfCol];
        Boolean[] isShipSank = isShipsSank;
        long counterShipSank = counterShipSankPam;

        if (p == Player.P1) {
            player = "Player 1";
            shipsPosition = shipsPositionP2;
            map = mapP2;
            secondMap = secondMapP2;

        } else if (p == Player.P2) {
            player = "Player 2";
            shipsPosition = shipsPositionP1;
            map = mapP1;
            secondMap = secondMapP1;

        }

        System.out.println();
        printDoubleMap(p);
        System.out.println();

        System.out.println(player + ", it's your turn:\n");

        Scanner scanner = new Scanner(System.in);
        String shotInput = scanner.nextLine().trim();
        System.out.println();
        char shotRow = shotInput.charAt(0);
        int shotCol = Integer.parseInt(shotInput.substring(1));

        String hitMessage;

        String[] ship0Position = shipsPosition.get(0);
        String[] ship1Position = shipsPosition.get(1);
        String[] ship2Position = shipsPosition.get(2);
        String[] ship3Position = shipsPosition.get(3);
        String[] ship4Position = shipsPosition.get(4);


            // check out of bound input
        while (!rowLetters.contains(shotRow) || !(shotCol >= 1 && shotCol <= numberOfCol)) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            shotInput = scanner.nextLine().trim();
            shotRow = shotInput.charAt(0);
            shotCol = Integer.parseInt(shotInput.substring(1));
        }

        // check if hit or missed
        if (map[rowLetters.indexOf(shotRow)][shotCol - 1] != 'O' && map[rowLetters.indexOf(shotRow)][shotCol - 1] != 'X') {
            map[rowLetters.indexOf(shotRow)][shotCol - 1] = 'M';
            secondMap[rowLetters.indexOf(shotRow)][shotCol - 1] = 'M';
            System.out.println("You missed!");
        } else {
            map[rowLetters.indexOf(shotRow)][shotCol - 1] = 'X';
            secondMap[rowLetters.indexOf(shotRow)][shotCol - 1] = 'X';


            if (!isShipSank[0] && isShipSank(ship0Position)) {
                counterShipSank++;
                if (counterShipSank == numberOfShips) {
                    hitMessage = "You sank the last ship. You won. Congratulations!\n";
                } else {
                    hitMessage = "You sank a ship! Specify a new target:\n";
                }
                isShipSank[0] = true;
            } else if (!isShipSank[1] && isShipSank(ship1Position)) {
                counterShipSank++;
                if (counterShipSank == numberOfShips) {
                    hitMessage = "You sank the last ship. You won. Congratulations!\n";
                } else {
                    hitMessage = "You sank a ship! Specify a new target:\n";
                }
                isShipSank[1] = true;
            } else if (!isShipSank[2] && isShipSank(ship2Position)) {
                counterShipSank++;
                if (counterShipSank == numberOfShips) {
                    hitMessage = "You sank the last ship. You won. Congratulations!\n";
                } else {
                    hitMessage = "You sank a ship! Specify a new target:\n";
                }
                isShipSank[2] = true;

            } else if (!isShipSank[3] && isShipSank(ship3Position)) {
                counterShipSank++;
                if (counterShipSank == numberOfShips) {
                    hitMessage = "You sank the last ship. You won. Congratulations!\n";
                } else {
                    hitMessage = "You sank a ship! Specify a new target:\n";
                }
                isShipSank[3] = true;
            } else if (!isShipSank[4] && isShipSank(ship4Position)) {
                counterShipSank++;
                if (counterShipSank == numberOfShips) {
                    hitMessage = "You sank the last ship. You won. Congratulations!\n";
                } else {
                    hitMessage = "You sank a ship! Specify a new target:\n";
                }
                isShipSank[4] = true;
            } else {
                hitMessage = "You hit a ship!";
            }

            System.out.println(hitMessage);
        }

        if (counterShipSank == numberOfShips) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        }
        System.out.println(counterShipSank);
    }

    private boolean isShipSank(String[] shipNumber) {
        boolean partOfShip = true;

        char firstRow = shipNumber[0].charAt(0);
        int firstCol = Integer.parseInt(shipNumber[0].substring(1));
        char secondRow = shipNumber[1].charAt(0);
        int secondCol = Integer.parseInt(shipNumber[1].substring(1));

        if (firstRow == secondRow) {
            for (int j = firstCol; j <= secondCol; j++) {
                if (secondMapP1[rowLetters.indexOf(firstRow)][j - 1] == 'X') {
                    partOfShip &= true;
                } else {
                    partOfShip &= false;
                }
            }
        } else {
            for (int j = rowLetters.indexOf(firstRow); j <= rowLetters.indexOf(secondRow); j++) {
                if (secondMapP1[j][firstCol - 1] == 'X') {
                    partOfShip &= true;
                } else {
                    partOfShip &= false;
                }
            }

        }
        return partOfShip;
    }

    private void printDoubleMap(Player p) {
        switch (p) {
            case P1:
                printSecondMap(Player.P2);
                System.out.println("---------------------");
                printBattleMap(Player.P1);
                break;
            case P2:
                printSecondMap(Player.P1);
                System.out.println("---------------------");
                printBattleMap(Player.P2);
                break;
        }
    }

    private void printSecondMap(Player p) {
//        System.out.println();

        System.out.print(" ");
        for (int i = 1; i <= numberOfCol; i++) {
            System.out.print(" " + i);
        }

        System.out.println();

        for (int i = 0; i < numberOfRow; i++) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < numberOfCol; j++) {

                switch (p) {
                    case P1:
                        System.out.print(" " + secondMapP1[i][j]);
                        break;
                    case P2:
                        System.out.print(" " + secondMapP2[i][j]);
                        break;
                }
            }
            System.out.println();
        }

    }

    private void positionShips(int n, Player p) {
        switch (n) {
            case 1:
                System.out.println();
                System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");

                getInputForShips("Aircraft Carrier", aircraftCarrierCells, p);
                break;
            case 2:
                System.out.println();
                System.out.println("Enter the coordinates of the Battleship (4 cells):");

                getInputForShips("Battleship", battleshipCells, p);
                break;
            case 3:
                System.out.println();
                System.out.println("Enter the coordinates of the Submarine (3 cells):");

                getInputForShips("Submarine", submarineCells, p);
                break;
            case 4:
                System.out.println();
                System.out.println("Enter the coordinates of the Cruiser (3 cells):");

                getInputForShips("Cruiser", cruiserCells, p);
                break;
            case 5:
                System.out.println();
                System.out.println("Enter the coordinates of the Destroyer (2 cells):");

                getInputForShips("Destroyer", destroyerCells, p);
                break;
            default:
                System.out.println("Wrong Ship");

        }
    }

    private void getInputForShips(String typeOfShip, int lengthOfShip, Player p) {
        System.out.println();

        char[][] map = null;
        List<String[]> shipsPosition = null;

        if (p == Player.P1) {
            map = mapP1;
            shipsPosition = shipsPositionP1;
        } else if (p == Player.P2) {
            map = mapP2;
            shipsPosition = shipsPositionP2;
        }

        Scanner scanner = new Scanner(System.in);

        String[] input = scanner.nextLine().trim().split("\\s+");
        System.out.println();

        char firstInputRow = input[0].charAt(0);
        int firstInputCol = Integer.parseInt(input[0].substring(1));
        char secondInputRow = input[1].charAt(0);
        int secondInputCol = Integer.parseInt(input[1].substring(1));

        if (firstInputCol == secondInputCol){

            char temp;

            if (rowLetters.indexOf(firstInputRow) > rowLetters.indexOf(secondInputRow)) {
                temp = firstInputRow;
                firstInputRow = secondInputRow;
                secondInputRow = temp;
            }
        }

        while (true) {

            if (!checkInputNotOverShipCells(firstInputRow, firstInputCol, secondInputRow, secondInputCol, lengthOfShip) && !isWrongShipLocation(firstInputRow, firstInputCol, secondInputRow, secondInputCol)) {
//                System.out.println();
                System.out.printf("Error! Wrong length of the %s! Try again:\n", typeOfShip);
                System.out.println();
            }

            if (checkIfShipPlacedCloseToAnother(firstInputRow, firstInputCol, secondInputRow, secondInputCol, p)) {
//                System.out.println();
                System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.println();
            }

            if (isWrongShipLocation(firstInputRow, firstInputCol, secondInputRow, secondInputCol)) {
//                System.out.println();
                System.out.println("Error! Wrong ship location! Try again:");
                System.out.println();
            }

            if (checkIfCellsAvailable(firstInputRow, firstInputCol, secondInputRow, secondInputCol, p) && checkInputNotOverShipCells(firstInputRow, firstInputCol, secondInputRow, secondInputCol, lengthOfShip) && !checkIfShipPlacedCloseToAnother(firstInputRow, firstInputCol, secondInputRow, secondInputCol, p)) {

                if (firstInputRow == secondInputRow) {
                    int temp;
                    if (firstInputCol > secondInputCol) {
                        temp = firstInputCol;
                        firstInputCol = secondInputCol;
                        secondInputCol = temp;
                    }
                    for (int i = firstInputCol; i <= secondInputCol; i++) {
                        map[rowLetters.indexOf(input[0].charAt(0))][i - 1] = 'O';
                    }
                    shipsPosition.add(new String[]{String.valueOf(firstInputRow) + firstInputCol, String.valueOf(secondInputRow) + secondInputCol});
                } else {
                    char temp;
                    if (rowLetters.indexOf(firstInputRow) > rowLetters.indexOf(secondInputRow)) {
                        temp = firstInputRow;
                        firstInputRow = secondInputRow;
                        secondInputRow = temp;
                    }

                    for (int i = rowLetters.indexOf(firstInputRow); i <= rowLetters.indexOf(secondInputRow); i++) {
                        map[i][firstInputCol - 1] = 'O';
                    }
                    shipsPosition.add(new String[]{String.valueOf(firstInputRow) + firstInputCol, String.valueOf(secondInputRow) + secondInputCol});
                }
                printBattleMap(p);
                break;
            }
            input = scanner.nextLine().trim().split("\\s+");
            System.out.println();

            firstInputRow = input[0].charAt(0);
            firstInputCol = Integer.parseInt(input[0].substring(1));

            secondInputRow = input[1].charAt(0);
            secondInputCol = Integer.parseInt(input[1].substring(1));
        }


    }

    private boolean isWrongShipLocation(char firstInputRow, int firstInputCol, char secondInputRow, int secondInputCol) {
        if (firstInputRow != secondInputRow && firstInputCol != secondInputCol) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkIfCellsAvailable(char firstInputRow, int firstInputCol, char secondInputRow, int secondInputCol, Player p) {
        char[][] map = null;

        if (p == Player.P1) {
            map = mapP1;
        } else if (p == Player.P2) {
            map = mapP2;
        }

       if (firstInputRow == secondInputCol && firstInputCol != secondInputCol) {
           for (int i = firstInputCol - 1; i <= secondInputCol - 1; i++) {
               if (map[rowLetters.indexOf(firstInputRow)][i] == 'O') {
                   return false;
               }
           }

       } else {
           for (int i = rowLetters.indexOf(firstInputRow); i <= rowLetters.indexOf(secondInputRow); i++) {
               if (map[i][firstInputCol - 1] == 'O') {
                   return false;
               }
           }
       }
       return true;
    }

    private boolean checkInputNotOverShipCells(char firstInputRow, int firstInputCol, char secondInputRow, int secondInputCol, int lengthOfShips) {

       int inputLength;

       if (firstInputRow == secondInputRow && firstInputCol != secondInputCol) {
           inputLength = Math.abs(firstInputCol - secondInputCol) + 1;
       } else if (firstInputRow != secondInputRow && firstInputCol == secondInputCol) {
           inputLength = Math.abs(rowLetters.indexOf(secondInputRow) - rowLetters.indexOf(firstInputRow)) + 1;
       } else {
           inputLength = 0; // Unknown case
       }

       if (inputLength == lengthOfShips) {
           return true;
       } else {
           return false;
       }
    }

    private boolean checkIfShipPlacedCloseToAnother(char firstInputRow, int firstInputCol, char secondInputRow, int secondInputCol, Player p) {
        char[][] map = null;

        if (p == Player.P1) {
            map = mapP1;
        } else if (p == Player.P2) {
            map = mapP2;
        }

        boolean above = false;
        boolean below = false;
        boolean left = false;
        boolean right = false;

        if (firstInputRow == secondInputRow && firstInputCol != secondInputCol) {
            if (firstInputRow != 'A') {
                for (int i = firstInputCol - 1; i <= secondInputCol - 1; i++) {
                    if (map[rowLetters.indexOf(firstInputRow) - 1][i] == 'O') {
                        above = true;
                    }
                }
            }

            if (firstInputRow != 'J') {
                for (int i = firstInputCol - 1; i <= secondInputCol - 1; i++) {
                    if (map[rowLetters.indexOf(firstInputRow) + 1][i] == 'O') {
                        below = true;
                    }
                }
            }

            if (firstInputCol != 1) {
                if (map[rowLetters.indexOf(firstInputRow)][firstInputCol - 2] == 'O') {
                    left = true;
                }
            }

            if (secondInputCol != numberOfCol) {
                if (map[rowLetters.indexOf(firstInputRow)][secondInputCol] == 'O') {
                    right = true;
                }
            }

        } else {

            if (firstInputRow != 'A') {
                if (map[rowLetters.indexOf(firstInputRow) - 1][firstInputCol] == 'O') {
                    above = true;
                }
            }

            if (secondInputRow != 'J') {
                if (map[rowLetters.indexOf(secondInputRow) + 1][firstInputCol] == 'O') {
                    below = true;
                }
            }

            if (firstInputCol != 1) {
                for (int i = rowLetters.indexOf(firstInputRow); i <= rowLetters.indexOf(secondInputRow); i++) {

                    if (map[i][firstInputCol - 2] == 'O') {
                        left = true;
                    }
                }
            }

            if (secondInputCol != numberOfCol) {
                for (int i = rowLetters.indexOf(firstInputRow); i <= rowLetters.indexOf(secondInputRow); i++) {
                    if (map[i][secondInputCol] == 'O') {
                        right = true;
                    }
                }
            }

        }

        return above || below || left || right;

    }
}
