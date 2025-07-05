import java.util.*;

public class TicTacToe {

    static final int size = 3;
    static final char empty = ' ';
    static final char playerMark = 'X';
    static final char computerMark = 'O';

    static final char[][] board = new char[size][size];
    static final Scanner s = new Scanner(System.in);
    static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Tic-Tac-Toe");
        int rounds = askRounds();

        int playerWins = 0, computerWins = 0;
        for (int r = 1; r <= rounds; r++) {
            System.out.println("\nRound " + r + " of " + rounds);
            resetBoard();
            char winner = playRound();

            switch (winner) {
                case playerMark -> {
                    playerWins++; System.out.println("You win this round!\n");
                }
                case computerMark -> {
                    computerWins++; System.out.println("Computer wins this round!\n");
                }
                default -> System.out.println("Round is a tie.\n");
            }
        }

        if (rounds > 1) showSeriesResult(playerWins, computerWins);
    }


    public static int askRounds() {
        while (true) {
            System.out.print("Choose game mode: (1) One round  (2) Best of 3 rounds : ");
            String in = s.nextLine().trim();
            if (in.equals("1")) return 1;
            if (in.equals("2")) return 3;
            System.out.println("Invalid choice—enter 1 or 2.");
        }
    }

    public static char playRound() {
        while (true) {
            printBoard();
            playerMove();
            if (isWinner(playerMark)) { printBoard(); return playerMark; }
            if (isFull()) { printBoard(); return empty; }

            computerMove();
            if (isWinner(computerMark)) { printBoard(); return computerMark; }
            if (isFull()) { printBoard(); return empty; }
        }
    }

    public static void showSeriesResult(int player, int comp) {
        System.out.printf("Final tally\nYou: %d   Computer: %d%n", player, comp);
        if (player > comp) System.out.println("Congratulations you are the overall winner!");
        else if (comp > player) System.out.println("Computer wins the series.");
        else System.out.println("Overall result is a tie.");
    }


    public static void resetBoard() {
        for (int i = 0; i < size; i++) Arrays.fill(board[i], empty);
    }

    public static void printBoard() {
        System.out.println("-------------");
        int cellNum = 1;
        for (int i = 0; i < size; i++) {
            System.out.print("| ");
            for (int j = 0; j < size; j++) {
                char display = (board[i][j] == empty) ? Character.forDigit(cellNum, 10) : board[i][j];
                System.out.print(display + " | ");
                cellNum++;
            }
            System.out.println();
            System.out.println("-------------");
        }
    }


    public static void playerMove() {
        while (true) {
            System.out.print("Enter your move (row & column 1-3): ");
            if (!s.hasNextInt()) { invalidInput(); continue; }
            int row = s.nextInt() - 1;
            if (!s.hasNextInt()) { invalidInput(); continue; }
            int col = s.nextInt() - 1; s.nextLine();

            if (inBounds(row, col) && board[row][col] == empty) { board[row][col] = playerMark; return; }
            System.out.println("Position taken or out of range, try again.");
        }
    }

    public static void computerMove() {
        int row, col;
        do {
            row = random.nextInt(size);
            col = random.nextInt(size);
        } while (board[row][col] != empty);
        board[row][col] = computerMark;
        System.out.printf("Computer played (%d,%d).%n", row + 1, col + 1);
    }


    public static boolean isWinner(char mark) {
        for (int i = 0; i < size; i++) {
            if (all(mark, board[i][0], board[i][1], board[i][2]))
                return true;
            if (all(mark, board[0][i], board[1][i], board[2][i]))
                return true;
        }
        return all(mark, board[0][0], board[1][1], board[2][2]) || all(mark, board[0][2], board[1][1], board[2][0]);
    }

    public static boolean isFull() {
        for (char[] row : board)
            for (char c : row)
                if (c == empty) return false;
        return true;
    }


    public static boolean all(char mark, char a, char b, char c) {
        return a == mark && b == mark && c == mark;
    }

    public static boolean inBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    public static void invalidInput() {
        System.out.println("Please enter two numbers between 1 and 3.");
        s.nextLine();
    }
}
