import java.util.Stack;


/**
 * The Game class controls the Super Text Adventure.
 *
 * The player explores eight university locations,
 * collects and drops items, moves between rooms,
 * and can return to previously visited rooms.
 *
 * Originally written by Michael Kolling and
 * David J. Barnes.
 * Updated by Garry Ovide.
 *
 * @author Garry Ovide
 * @version 2026.11.06
 */
public class Game
{
    private Parser parser;
    private Player player;
    private Stack<Room> roomHistory;
    
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all rooms, define their exits, and place items
     * in the rooms. Set the starting room for the player.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, bookstore, lounge, cafeteria;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        bookstore = new Room("in the university bookstore");
        lounge = new Room("in the first-floor lounge");
        cafeteria = new Room("in the second-floor cafeteria");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        
        cafeteria.addItem(new Item("a lunch tray", 2));
        cafeteria.addItem(new Item("a sandwich", 1));
        cafeteria.addItem(new Item("an energy drink", 1));

        
        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        /**
         * Connect the bookstore to the administration office.
         */
        office.setExit("east", bookstore);
        bookstore.setExit("west", office);

        // Connect the basement office to the first-floor lounge.
        office.setExit("up", lounge);
        lounge.setExit("down", office);

        // Connect the first-floor lounge to the second-floor cafeteria.
        lounge.setExit("up", cafeteria);
        cafeteria.setExit("down", lounge);
        
        // Add multiple items to the rooms.
        outside.addItem(new Item("a campus map", 1));
        outside.addItem(new Item("a flashlight", 2));
        
        // Add items to the bookstore.
        bookstore.addItem(new Item("a bookstore book", 2));
        bookstore.addItem(new Item("a bookstore gift card", 1));
        
        // Add items to the first-floor lounge.
        lounge.addItem(new Item("a newspaper", 1));
        lounge.addItem(new Item("a magazine", 1));

        theater.addItem(new Item("a lecture notebook", 2));
        theater.addItem(new Item("a textbook", 3));

        pub.addItem(new Item("a coffee mug", 1));
        pub.addItem(new Item("a water bottle", 2));

        lab.addItem(new Item("a laptop computer", 5));
        lab.addItem(new Item("a keyboard", 2));
        lab.addItem(new Item("a USB drive", 1));

        office.addItem(new Item("an office key", 1));
        office.addItem(new Item("a folder", 2));

        // Create the player and set the starting room.
        player = new Player("Phoenix", outside);

        // Create an empty stack to remember visited rooms.
        roomHistory = new Stack<>();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * Display the welcome message, game instructions,
     * and the player's starting room description.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("====================================");
        System.out.println("     SUPER TEXT ADVENTURE");
        System.out.println("====================================");
        System.out.println();
        System.out.println("Welcome to the University Adventure!");
        System.out.println("Explore eight university locations");
        System.out.println("across multiple floors.");
        System.out.println();
        System.out.println("Collect items, explore rooms,");
        System.out.println("and discover your surroundings.");
        System.out.println();
        System.out.println("Type 'help' to see available commands.");
        System.out.println();

        System.out.println(
        player.getCurrentRoom().getLongDescription());
    }

    /**
     * Process a command entered by the player.
     *
     * @param command The command to process.
     * @return true if the player wants to quit,
     *         false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        
        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case LOOK:
                System.out.println(player.getCurrentRoom().getLongDescription());
                break;

            case BACK:
                backRoom();
                break;

            case TAKE:
                takeItem(command);
                break;

            case DROP:
                dropItem(command);
                break;
                
                
            case ITEMS:
                printItems();
                break;

            case DRINK:
                drinkEnergy(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }

        return wantToQuit;
    }
          
    /**
     * Display instructions for playing the game.
     * Explain movement, item collection, and the
     * available commands.
     */
    private void printHelp()
    {
        System.out.println("You are exploring the university.");
        System.out.println("Visit rooms and collect items.");
        System.out.println();

        System.out.println("Available commands:");
        parser.showCommands();

        System.out.println();
        System.out.println("How to play:");
        System.out.println("go north  - Move north");
        System.out.println("go south  - Move south");
        System.out.println("go east   - Move east");
        System.out.println("go west   - Move west");
        System.out.println("go up     - Go upstairs");
        System.out.println("go down   - Go downstairs");
        System.out.println("look      - Examine the current room");
        System.out.println("back      - Return to the previous room");
        System.out.println("take book - Pick up a book");
        System.out.println("drop book - Drop a book");
        System.out.println("quit      - Exit the game");
    }

    /**
     * Move the player in the specified direction.
     * If an exit exists, save the current room in
     * the history stack before moving the player.
     *
     * @param command The command containing the direction.
     */
    private void goRoom(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Get the player's current room.
        Room currentRoom = player.getCurrentRoom();

        // Find the next room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // Save the current room in the history stack.
            roomHistory.push(currentRoom);

            // Move the player to the next room.
            player.setCurrentRoom(nextRoom);

            // Display the new room description.
            System.out.println(
                player.getCurrentRoom().getLongDescription());
        }
    }

    /**
     * Move the player back through previously visited rooms.
     * Each back command removes the most recent room
     * from the history stack and updates the player's location.
     */
    private void backRoom()
    {
        if (roomHistory.empty()) {
            System.out.println("You cannot go back any further.");
        }
        else {
            // Retrieve the most recently visited room.
            Room previousRoom = roomHistory.pop();

            // Move the player to the previous room.
            player.setCurrentRoom(previousRoom);

            // Display the room description.
            System.out.println(
                player.getCurrentRoom().getLongDescription()
            );
        }
    }
   
    /**
     * Allow the player to take an item from the current room.
     * The item is collected only if the player has enough
     * remaining carrying capacity.
     *
     * @param command The command containing the item name.
     */
    private void takeItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        
        String itemName = command.getSecondWord();

        // Remove the requested item from the room.
        Item item = player.getCurrentRoom().removeItem(itemName);

        if (item == null) {
            System.out.println("That item is not in this room.");
        }
        else {
            // Check whether the player can carry the item.
            if (player.takeItem(item)) {
                System.out.println("You picked up "
                + item.getDescription());
            }
            else {
                // Return the item to the room if it is too heavy.
                player.getCurrentRoom().addItem(item);
            }
        }
    }

    /**
     * Allow the player to drop a specific item.
     * The dropped item is placed in the current room.
     *
     * @param command The command containing the item name.
     */
    private void dropItem(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();

        // Remove the selected item from the inventory.
        Item item = player.dropItem(itemName);

        if (item == null) {
            System.out.println("You are not carrying that item.");
        }
        else {
            // Place the dropped item in the current room.
            player.getCurrentRoom().addItem(item);

            System.out.println("You dropped "
                           + item.getDescription());
        }
    }
    
    /**
     * Display all items carried by the player
     * and their total weight.
     */
    private void printItems()
    {
        System.out.println("Items you are carrying:");
        
        if (player.getCarriedItems().isEmpty()) {
            System.out.println("You are not carrying any items.");
        }
        else {
            for (Item item : player.getCarriedItems()) {
                System.out.println(item.getItemInfo());
            }
        }
        System.out.println("Total weight: " + player.getCurrentWeight());
    }
    
    /**
     * Allow the player to drink an energy drink.
     * Drinking it increases the maximum carrying weight.
     *
     * @param command The command containing the drink name.
     */
    private void drinkEnergy(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("Drink what?");
            return;
        }

        if (!command.getSecondWord().equalsIgnoreCase("energy")) {
            System.out.println("You cannot drink that.");
            return;
        }

        Item drink = player.dropItem("energy drink");

        if (drink == null) {
            System.out.println("You are not carrying an energy drink.");
        }
        else {
            player.increaseMaximumWeight(5);
            System.out.println("You drank the energy drink!");
            System.out.println("Maximum carrying weight increased to 15.");
        }
    }
  
    /**
     * Determine whether the player wants to quit the game.
     * The quit command must not contain a second word.
     *
     * @param command The quit command entered by the player.
     * @return true if the player wants to quit,
     *         false otherwise.
     */

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Start the Super Text Adventure game.
     * This method allows the game to run outside BlueJ.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }

}
