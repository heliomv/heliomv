/**
 * A class to represent the Game of Craps 
 * @author Helio Musselwhite-Veitch - script derived from Dr. John Nelson's "GameOfCraps"
 * @version 1 Feb 2022
 */
import java.util.Scanner;
import java.util.Random;
public class MyGameOfCraps {
    // create random number generator (once only) for use in method rollDice
    private static final Random rng = new Random(); 

    /*
     * constants for the strings for won/lost  
     * (include unicode characters for happy/sad faces see: https://unicode-table.com/en/)
     */ 
    private static final String WON_STR = "Congratulations 😀, you won this game";
    private static final String LOST_STR = "Hard luck 😳, you lost this game";

    // enumerated type with enumerators that represent the game status
    private enum Status { WON, LOST, CONTINUE };

    /* 
     * enumerated type for names of rolls with special names replacing basic values
     * 2 is SNAKE EYES, 3 is TREY, 11 is YO_LEVEN, 12 is BOX_CARS
     */ 
    private enum RollName { ZERO_NA,  
        ONE_NA, SNAKE_EYES, TREY, FOUR, FIVE,     SIX,
        SEVEN,  EIGHT,      NINE, TEN,  YO_LEVEN, BOX_CARS };

    /**
     * Play selected number of game of craps
     * print details then ask question on how many games you'd like to play
     */
    public static void main( String[] args ) {     
        Scanner keyboard = new Scanner(System.in);

        System.out.println("My Game of Craps, Author: Helio Musselwhite-Veitch 21311137");

        // Counter controlled iteration
        System.out.print("How many games would you like to play? ");
        int numberOfGames = keyboard.nextInt();

        for (int game=1; game<=numberOfGames; ++game ) {
            System.out.printf("\n### Game %d ###\n", game);
            playGame();
        }

        // Sentinel controlled iteration, play while user inputs "Y"
        System.out.println("\nNow lets play again while you enter Y or y");
        boolean isPlayAgain;

        do {
            System.out.print("\nDo you wish to play another game [y/N]: ");
            isPlayAgain = keyboard.next().toUpperCase().equals("Y");
            if ( isPlayAgain) {
                System.out.printf("\n###Game %d ###\n", ++numberOfGames);
                playGame();
            }
        } while ( isPlayAgain );

        System.out.printf("You played %d games. Bye!\n", numberOfGames ); 
    }

    private static boolean playGame()  {
        Status gameStatus = Status.CONTINUE;   // game will CONTINUE until WON or LOST
        int point = 0;                         // point if not won or lost on first roll  

        int rollDice = rollAndDisplayDice(2);  // roll two dice and sum the faces    

        RollName rollName = RollName.values()[rollDice]; // Map dice sum to RollName enumerator
        // RollName.values() returns an array of the enumerators of RollName.
        // RollName.values()[roll] maps roll to the corresponding RollName enumerator.

        // Based on the rollName enumerator value, update the game status if a won or lost value.
        // If neither then the gameStatus remains Status.CONTINUE and the point is stored
        switch ( rollName ) {        
            case SEVEN: 
            case YO_LEVEN:          
                gameStatus = Status.WON;    // win if 7 or 11 on first roll
                break;

            case SNAKE_EYES: 
            case TREY: 
            case BOX_CARS: 
                gameStatus = Status.LOST;   // lose with 2, 3 or 12 on first roll
                break;

            default:         
                point = rollDice;           // neither won or lost, so store the point
                System.out.printf( "The Point is %s (%d). Roll %d before 7 to win\n", 
                    rollName.name(), point, point );
                break;                      // this break is optional
        } 

        /*
         * Continue playing until player either throws point (won) or seven (lost)
         */
        while ( gameStatus == Status.CONTINUE ) { 
            rollDice = rollAndDisplayDice(2);

            if ( rollDice == point )             // status becomes win by making point
                gameStatus = Status.WON;
            else if ( rollDice == 7 )            // status lose by rolling 7 before point
                gameStatus = Status.LOST;
        } 

        /*
         * Now if here, the player must have lost or won, 
         *   hence display won or lost details using the WON_STR or LOST_STR
         */
        System.out.println(gameStatus == Status.WON ? WON_STR : LOST_STR);
        return (gameStatus == Status.WON);
    } 

    /**
     * Roll requested number of dice and store in an array with sum
     * @param numberDice    the number of dice to role
     * @return an array where [0] element is the sum followed by individual rolls
     */
    private static int[] rollDice(int numberDice) {
        int[] diceRoll = new int[numberDice+1];

        for (int i=1; i < diceRoll.length; i++) {
            diceRoll[i] = rng.nextInt( 6 ) + 1; 
            diceRoll[0] += diceRoll[i]; 
        }

        return diceRoll; // return sum of dice
    } // end method rollDice

    /**
     * Display sum of dice rolls and the dice values
     * @param diceArray  array with total in first element and then individual dice values
     */
    private static void displayDice(int[] diceArray) {
        System.out.printf("Rolled %2d  Dice (", diceArray[0]);
        for (int i=1; i < diceArray.length; i++) {
            System.out.printf("%s %d", (i>1) ? "," : "", diceArray[i]);
        }
        System.out.println( " )" );
    } // end method displayDice

    /**
     * Roll requested number of dice and display rolls
     * @param numberDice    the number of dice to role
     * @return sum of dice rolled
     */
    private static int rollAndDisplayDice(int numberDice) {
        int[] rolledDice = rollDice(numberDice);
        displayDice(rolledDice);
        return rolledDice[0];    
    } // end method rollAndDisplayDice

} // end class MyGameOfCraps
