import java.util.Scanner;


/** Responsible for running and displaying the connect game
 */
class GamePlayer {

    public static void main(String[] args) {
        // Create Connect 4 game with 6x7 board
        Game game = new Game(4, 6, 7);
        Scanner userInput = new Scanner(System.in);
        System.out.println("Beginning the game!");

        // Put however many players you want into this array.
        char[] players = {'b', 'r'};

        int activePlayer = 0;

        while (game.isPlaying()) {
            System.out.println(game);
            System.out.printf("Player %d's move:\n", activePlayer + 1);

            int move = userInput.nextInt();

            // If player inputs an invalid move, game will remain on player's turn
            if (!game.pickColumn(players[activePlayer], move)) {
                System.out.println("Not a valid move. Try again.");
                continue;
            }

            // Cycle through all players and return to Player 1 when at end of cycle
            if (activePlayer == players.length - 1) activePlayer = 0;
            else activePlayer++;
        }
        System.out.println("Winner is: " + game.getWinner());
    }
}
