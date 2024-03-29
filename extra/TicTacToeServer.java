import java.net.*;
import java.io.*;

public class TicTacToeServer {

    static int A1, A2, A3, B1, B2, B3, C1, C2, C3;

    static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out;
    static BufferedReader in;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(4321);
        serverSocket.setSoTimeout(500000);
        Socket playerSocket;

        System.out.println("Expecting connection...");

        try {
            playerSocket = serverSocket.accept();
            System.out.println("Player connected.");
            out = new PrintWriter(playerSocket.getOutputStream(), true);

            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

            String prompt1 =
                    "Welcome to Tic-Tac-Toe. Player 1 please enter your first move (enter A1~C3): ";
            String prompt2 = "Player 2 please enter your first move: ";
            String player1Move = "";
            String player2Move = "";
            boolean gameIsWon = false;

            for (int i = 1; i <= 9; i++) {

                // Player 1

                player1Move = getPlayer1Move(prompt1);
                updateBoard(player1Move, 1);
                displayBoard();
                if (isGameWon()) {
                    System.out.println("Player 1 won!");
                    out.println("You lose :-(");
                    out.flush();
                    gameIsWon = true;
                    break;
                }
                prompt1 = "Player 1 please enter your next move: ";

                // Player 2

                if (i < 9) {
                    player2Move = getPlayer2Move(prompt2);
                    updateBoard(player2Move, 2);
                    displayBoard();
                    if (isGameWon()) {
                        System.out.println("Player 2 won!");
                        out.println("You won!");
                        out.flush();
                        gameIsWon = true;
                        break;
                    }
                    prompt2 = "Player 2 please enter your next move: ";
                    i++;
                }
            }

            if (!gameIsWon) {
                System.out.println("It's a draw!");
                out.println("It's a draw!");
                out.flush();
            }

        } catch (java.net.SocketTimeoutException e) {
            System.out.println("Timed out.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Server connection terminated.");
        serverSocket.close();
    }

    // following are the classes related to TicTacToe game

    public static String getPlayer1Move(String prompt) {
        try {
            String play;
            System.out.print(prompt);
            do {
                play = stdIn.readLine();
                if (!isValidPlay(play)) {
                    System.out.println("That is not a valid play.");
                }
            } while (!isValidPlay(play));
            return play;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // play is valid when the input matches one of the grid,
    // and the grid is currently empty

    public static boolean isValidPlay(String play) {
        if (play.equalsIgnoreCase("A1") & A1 == 0)
            return true;
        if (play.equalsIgnoreCase("A2") & A2 == 0)
            return true;
        if (play.equalsIgnoreCase("A3") & A3 == 0)
            return true;
        if (play.equalsIgnoreCase("B1") & B1 == 0)
            return true;
        if (play.equalsIgnoreCase("B2") & B2 == 0)
            return true;
        if (play.equalsIgnoreCase("B3") & B3 == 0)
            return true;
        if (play.equalsIgnoreCase("C1") & C1 == 0)
            return true;
        if (play.equalsIgnoreCase("C2") & C2 == 0)
            return true;
        if (play.equalsIgnoreCase("C3") & C3 == 0)
            return true;
        return false;
    }

    public static void updateBoard(String play, int player) {
        if (play.equalsIgnoreCase("A1"))
            A1 = player;
        if (play.equalsIgnoreCase("A2"))
            A2 = player;
        if (play.equalsIgnoreCase("A3"))
            A3 = player;
        if (play.equalsIgnoreCase("B1"))
            B1 = player;
        if (play.equalsIgnoreCase("B2"))
            B2 = player;
        if (play.equalsIgnoreCase("B3"))
            B3 = player;
        if (play.equalsIgnoreCase("C1"))
            C1 = player;
        if (play.equalsIgnoreCase("C2"))
            C2 = player;
        if (play.equalsIgnoreCase("C3"))
            C3 = player;
    }

    public static void displayBoard() {
        String line = "";
        System.out.println();
        line = " " + getXO(A1) + " | " + getXO(A2) + " | " + getXO(A3);
        System.out.println(line);
        System.out.println("-----------");
        line = " " + getXO(B1) + " | " + getXO(B2) + " | " + getXO(B3);
        System.out.println(line);
        System.out.println("-----------");
        line = " " + getXO(C1) + " | " + getXO(C2) + " | " + getXO(C3);
        System.out.println(line);
        System.out.println();
    }

    public static String getXO(int square) {
        if (square == 1)
            return "X";
        if (square == 2)
            return "O";
        return " ";
    }

    public static String getPlayer2Move(String prompt) {
        try {
            String play;
            System.out.print("Wait for player 2 to play.");
            out.println(prompt);
            out.flush();
            do {
                play = in.readLine();
                if (!isValidPlay(play)) {
                    out.println("That is not a valid play.");
                    out.flush();
                }
            } while (!isValidPlay(play));
            return play;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean isGameWon() {
        if (isRowWon(A1, A2, A3))
            return true;
        if (isRowWon(B1, B2, B3))
            return true;
        if (isRowWon(C1, C2, C3))
            return true;
        if (isRowWon(A1, B1, C1))
            return true;
        if (isRowWon(A2, B2, C2))
            return true;
        if (isRowWon(A3, B3, C3))
            return true;
        if (isRowWon(A1, B2, C3))
            return true;
        if (isRowWon(A3, B2, C1))
            return true;
        return false;
    }

    public static boolean isRowWon(int a, int b, int c) {
        return ((a == b) & (a == c) & (a != 0));
    }
}
