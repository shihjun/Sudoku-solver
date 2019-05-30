import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Comparator;
// import java.util.Collections;

/**
 * Sudoku
 */
public class Sudoku {
  static void displayBoard(String boardString) {
    String[] boardRows = { "", "", "", "", "", "", "", "", "" };
    char[] boardArray = boardString.toCharArray();
    for (int index = 0; index < boardString.length(); index++) {
      boardRows[index / 9] = boardRows[index / 9] + " " + boardArray[index];
      if (index % 9 == 2 || index % 9 == 5) {
        boardRows[index / 9] = boardRows[index / 9] + " |";
      }
    }
    System.out.println("-------------------------");
    for (int index = 0; index < 9; index++) {
      System.out.println("|" + boardRows[index] + " |");
      if (index == 2 || index == 5) {
        System.out.println("-------------------------");
      }
    }
    System.out.println("-------------------------");
  }

  static ArrayList<Integer> checkEmptyCell(String boardString) {
    ArrayList<Integer> emptyCell = new ArrayList<>();
    char[] boardArray = boardString.toCharArray();
    for (int i = 0; i < boardString.length(); i++) {
      if (boardArray[i] == '0') {
        emptyCell.add(i);
      }
    }
    return emptyCell;
  }

  static String fillRemainingNum(String boardString) {
    // char[] boardArray = boardString.toCharArray();
    ArrayList<Integer> emptyCell = new ArrayList<>(checkEmptyCell(boardString));

    String newBoardString = "";

    if (emptyCell.size() == 0) {
      newBoardString = boardString;
    }

    // for (int index = 0; index < newBoardArray.length(); index++) {
    // if (newBoardArray[index] != '0') {
    // newBoardString = fillinConfirmNum(newBoardString);
    // }
    // }

    // char[] boardArray = boardString.toCharArray();
    // ArrayList<Integer> emptyCell = new
    // ArrayList<>(checkEmptyCell(newBoardString));
    // ArrayList<ArrayList<Integer>> possibleNums = new
    // ArrayList<>(checkPossibleNum(newBoardString));

    displayBoard(newBoardString);

    return newBoardString;
  }

  static String fillinConfirmNum(String boardString) {
    String newBoardString = boardString;
    boolean hvOnePossibleNum = true;
    int counter = 1;

    while (hvOnePossibleNum) {
      hvOnePossibleNum = !hvOnePossibleNum;

      char[] boardArray = newBoardString.toCharArray();
      ArrayList<Integer> emptyCell = new ArrayList<>(checkEmptyCell(newBoardString));
      ArrayList<ArrayList<Integer>> possibleNums = new ArrayList<>(checkPossibleNum(newBoardString));

      if (emptyCell.size() != 0) {
        for (int i = 0; i < emptyCell.size(); i++) {
          ArrayList<Integer> numOfPossible = possibleNums.get(i);
          if (numOfPossible.size() == 1) {
            int firstNum = numOfPossible.get(0);
            char num = (char) (firstNum + '0');
            boardArray[emptyCell.get(i)] = num;
            newBoardString = "";
            hvOnePossibleNum = true;
          }
        }
        for (int i = 0; i < boardString.length(); i++) {
          String newNum = Character.toString(boardArray[i]);
          newBoardString = newBoardString + newNum;
        }
      }
      System.out.println(counter);
      counter += 1;
      displayBoard(newBoardString);
    }
    return newBoardString;
  }

  static ArrayList<ArrayList<Integer>> checkPossibleNum(String boardString) {
    Integer[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    ArrayList<Integer> emptyCell = new ArrayList<>(checkEmptyCell(boardString));
    ArrayList<ArrayList<Integer>> possibleCellNums = new ArrayList<ArrayList<Integer>>();

    ArrayList<ArrayList<Integer>> possibleRowsNums = checkRow(boardString);
    ArrayList<ArrayList<Integer>> possibleColsNums = checkColumn(boardString);
    ArrayList<ArrayList<Integer>> possibleSubgridsNums = checkSubgrid(boardString);

    for (int i = 0; i < emptyCell.size(); i++) {
      int row = emptyCell.get(i) / 9;
      int col = emptyCell.get(i) % 9;
      int grid = 0;

      if (row < 3) {
        if (col < 3) {
          grid = 0;
        } else if (col > 2 && col < 6) {
          grid = 1;
        } else
          grid = 2;
      } else if (row > 2 && row < 6) {
        if (col < 3) {
          grid = 3;
        } else if (col > 2 && col < 6) {
          grid = 4;
        } else
          grid = 5;
      } else if (row > 5) {
        if (col < 3) {
          grid = 6;
        } else if (col > 2 && col < 6) {
          grid = 7;
        } else
          grid = 8;
      }

      // System.out.println(i + " " + grid);

      ArrayList<Integer> tempPossibleNums = new ArrayList<>(Arrays.asList(x));

      for (int j = 1; j < 10; j++) {
        int num = j;
        ArrayList<Integer> possibleRowNums = possibleRowsNums.get(row);
        ArrayList<Integer> possibleColNums = possibleColsNums.get(col);
        ArrayList<Integer> possibleSubgridNums = possibleSubgridsNums.get(grid);
        if (!possibleRowNums.contains(num) || !possibleColNums.contains(num) || !possibleSubgridNums.contains(num)) {
          tempPossibleNums.remove(tempPossibleNums.indexOf(num));
        }
      }
      possibleCellNums.add(tempPossibleNums);

      // System.out.println("possible num: row-" + row + " col-" + col + " :" +
      // possibleCellNums.get(i));
    }
    return possibleCellNums;
  }

  static ArrayList<ArrayList<Integer>> checkRow(String boardString) {
    Integer[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    char[] boardArray = boardString.toCharArray();
    ArrayList<ArrayList<Integer>> possibleRowNum = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < 9; i++) {
      ArrayList<Integer> tempPossibleNum = new ArrayList<>(Arrays.asList(x));
      for (int index = i * 9; index < i * 9 + 9; index++) {
        if (boardArray[index] != '0') {
          int cellNum = Character.getNumericValue(boardArray[index]);
          tempPossibleNum.remove(tempPossibleNum.indexOf(cellNum));
        }
      }
      possibleRowNum.add(tempPossibleNum);
    }
    // for (int i = 0; i < possibleRowNum.size(); i++) {
    // System.out.println("row " + (i + 1) + " : " + possibleRowNum.get(i));
    // }
    return possibleRowNum;
  }

  static ArrayList<ArrayList<Integer>> checkColumn(String boardString) {
    Integer[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    char[] boardArray = boardString.toCharArray();
    ArrayList<ArrayList<Integer>> possibleColNum = new ArrayList<ArrayList<Integer>>();

    for (int i = 0; i < 9; i++) {
      ArrayList<Integer> tempPossibleNum = new ArrayList<>(Arrays.asList(x));
      for (int index = i; index < i + 9 * 9; index += 9) {
        if (boardArray[index] != '0') {
          int cellNum = Character.getNumericValue(boardArray[index]);
          tempPossibleNum.remove(tempPossibleNum.indexOf(cellNum));
        }
      }
      possibleColNum.add(tempPossibleNum);
    }
    // for (int i = 0; i < possibleColNum.size(); i++) {
    // System.out.println("col " + (i + 1) + " : " + possibleColNum.get(i));
    // }
    return possibleColNum;
  }

  static ArrayList<ArrayList<Integer>> checkSubgrid(String boardString) {
    Integer[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    char[] boardArray = boardString.toCharArray();
    ArrayList<ArrayList<Integer>> possibleSubgridNum = new ArrayList<ArrayList<Integer>>();
    for (int gridRow = 0; gridRow < boardString.length(); gridRow += 27) {
      for (int gridCol = gridRow; gridCol < gridRow + 9; gridCol += 3) {
        ArrayList<Integer> tempPossibleNum = new ArrayList<>(Arrays.asList(x));
        for (int rows = gridCol; rows < gridCol + 3; rows++) {
          for (int columns = rows; columns < rows + 9 * 3; columns += 9) {
            if (boardArray[columns] != '0') {
              int cellNum = Character.getNumericValue(boardArray[columns]);
              tempPossibleNum.remove(tempPossibleNum.indexOf(cellNum));
            }
          }
        }
        possibleSubgridNum.add(tempPossibleNum);
      }
    }
    // for (int i = 0; i < possibleSubgridNum.size(); i++) {
    // System.out.println("Subgrid " + (i + 1) + " : " + possibleSubgridNum.get(i));
    // }
    return possibleSubgridNum;
  }

  static String solve(String boardString) {
    displayBoard(boardString);
    boardString = fillinConfirmNum(boardString);
    // boardString = fillRemainingNum(boardString);

    return boardString;
  }

  public static void main(String[] args) {
    // Easy Mode
    String solution = solve("105802000090076405200400819019007306762083090000061050007600030430020501600308900");
    System.out
        .println(solution.equals("145892673893176425276435819519247386762583194384961752957614238438729561621358947"));

    // // Hard Mode
    // String solution =
    // solve("400000805030000000000700000020000060000080400000010000000603070500200000104000000");
    // System.out
    // .println(solution.equals("417369825632158947958724316825437169791586432346912758289643571573291684164875293"));
  }
}