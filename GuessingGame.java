import java.util.Scanner;
import java.util.Random;

public class GuessingGame {

    static final int MAX_ATTEMPTS_EASY   = 15;
    static final int MAX_ATTEMPTS_MEDIUM = 10;
    static final int MAX_ATTEMPTS_HARD   =  7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("================================");
        System.out.println("   Number Guessing Game");
        System.out.println("================================");

        boolean playAgain = true;

        while (playAgain) {
            int[] settings = chooseDifficulty(scanner);
            int range      = settings[0];
            int maxAttempts = settings[1];

            playRound(scanner, range, maxAttempts);

            System.out.print("\nPlay again? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            playAgain = response.equals("yes") || response.equals("y");
        }

        System.out.println("\nThanks for playing! Goodbye.");
        scanner.close();
    }

    static int[] chooseDifficulty(Scanner scanner) {
        System.out.println("\nChoose difficulty:");
        System.out.println("  1. Easy   (1–50,  15 attempts)");
        System.out.println("  2. Medium (1–100, 10 attempts)");
        System.out.println("  3. Hard   (1–200,  7 attempts)");
        System.out.print("Enter choice (1/2/3): ");

        int choice = readInt(scanner, 1, 3);

        switch (choice) {
            case 1: return new int[]{50,  MAX_ATTEMPTS_EASY};
            case 3: return new int[]{200, MAX_ATTEMPTS_HARD};
            default: return new int[]{100, MAX_ATTEMPTS_MEDIUM};
        }
    }

    static void playRound(Scanner scanner, int range, int maxAttempts) {
        Random random      = new Random();
        int secretNumber   = random.nextInt(range) + 1;
        int attemptsLeft   = maxAttempts;

        System.out.println("\nI'm thinking of a number between 1 and " + range + ".");
        System.out.println("You have " + maxAttempts + " attempts. Good luck!\n");

        while (attemptsLeft > 0) {
            System.out.print("Guess (" + attemptsLeft + " left): ");
            int guess = readInt(scanner, 1, range);
            attemptsLeft--;

            if (guess < secretNumber) {
                System.out.println("Too low!" + hint(secretNumber, guess, attemptsLeft));
            } else if (guess > secretNumber) {
                System.out.println("Too high!" + hint(secretNumber, guess, attemptsLeft));
            } else {
                int used = maxAttempts - attemptsLeft;
                System.out.println("\nCorrect! You found " + secretNumber
                    + " in " + used + " attempt" + (used == 1 ? "" : "s") + "!");
                printRating(used, maxAttempts);
                return;
            }
        }

        System.out.println("\nOut of attempts! The number was: " + secretNumber);
    }

    static String hint(int secret, int guess, int attemptsLeft) {
        if (attemptsLeft == 0) return "";
        int diff = Math.abs(secret - guess);
        if (diff <= 5)  return " (very close!)";
        if (diff <= 15) return " (getting warmer)";
        return "";
    }

    static void printRating(int used, int max) {
        double ratio = (double) used / max;
        if (ratio <= 0.3)      System.out.println("Rating: Excellent!");
        else if (ratio <= 0.6) System.out.println("Rating: Good job!");
        else                   System.out.println("Rating: Keep practising!");
    }

    /** Keeps asking until the user enters a valid integer in [min, max]. */
    static int readInt(Scanner scanner, int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                if (value >= min && value <= max) return value;
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } else {
                System.out.print("Invalid input. Enter a number: ");
                scanner.next();
            }
        }
    }
}