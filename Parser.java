import java.util.Scanner;

/**
 * Reads and interprets player commands in the
 * Super Text Adventure game.
 *
 * The parser reads input from the terminal
 * and converts it into Command objects.
 *
 * Each command consists of a command word
 * and an optional second word.
 *
 * The parser checks whether the command word
 * is recognized by the game.
 *
 * Originally written by Michael Kölling
 * and David J. Barnes.
 * Updated by Garry Ovide.
 *
 * @author Michael Kölling and David J. Barnes
 * @author Garry Ovide
 * @version 2026.11.06
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
