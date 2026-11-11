/**
 * Defines the command words available in the
 * Super Text Adventure game.
 *
 * Each command word has a corresponding string
 * entered by the player.
 *
 * Supported commands include go, quit, help,
 * look, back, take, and drop.
 *
 * Originally written by Michael Kölling
 * and David J. Barnes.
 * Updated by Garry Ovide.
 *
 * @author Michael Kölling and David J. Barnes
 * @author Garry Ovide
 * @version 2026.11.06
 */

public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    
    GO("go"), QUIT("quit"), HELP("help"), LOOK("look"), BACK("back"), TAKE("take"),
    DROP("drop"), ITEMS("items"), DRINK("drink"), UNKNOWN("?");

    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
