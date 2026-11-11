import java.util.ArrayList;


/**
 * Represents a player in the Super Text Adventure game.
 * The player has a name, a current room, and an inventory
 * that can hold any number of items.
 *
 * Originally based on the World of Zuul project
 * by Michael Kölling and David J. Barnes.
 * Updated by Garry Ovide.
 *
 * @author Garry Ovide
 * @version 2026.11.06
 */

public class Player
{
    private String name;
    private Room currentRoom;
    private ArrayList<Item> carriedItems;
    private int maximumWeight;

    /**
     * Create a player with a name and starting room.
     * The player's inventory is initially empty.
     * The maximum carrying weight is 10 units.
     *
     * @param name The player's name.
     * @param startingRoom The player's starting room.
     */
    public Player(String name, Room startingRoom)
    {
        this.name = name;
        currentRoom = startingRoom;
        carriedItems = new ArrayList<>();
        maximumWeight = 10;
    }

    /**
     * Return the player's name.
     *
     * @return The player's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the player's current room.
     *
     * @return The current room.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Move the player to another room.
     *
     * @param room The room to enter.
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }
    
    /**
     * Add an item to the player's inventory if
     * the maximum carrying weight is not exceeded.
     *
     * @param item The item the player wants to carry.
     * @return true if the item was added successfully,
     * false if the item is null or too heavy.
     */
    public boolean takeItem(Item item)
    {
        if (item == null) {
            return false;
        }
        
        if (getCurrentWeight() + item.getWeight() > maximumWeight) {
            System.out.println("You cannot carry this item.");
            System.out.println("Maximum weight: " + maximumWeight);
            System.out.println("Current weight: " + getCurrentWeight());
            return false;
        }

        carriedItems.add(item);
        return true;
    }

    /**
     * Remove a specific item from the player's inventory.
     * The item is identified by its description.
     *
     * @param description The name of the item to drop.
     * @return The removed item, or null if no matching
     *         item is found.
     */
    public Item dropItem(String description)
    {
        for (int i = 0; i < carriedItems.size(); i++) {
            Item item = carriedItems.get(i);
            
            if (item.getDescription().toLowerCase()
                .contains(description.toLowerCase())) {
                    carriedItems.remove(i);
                    return item;
                }
            }
            return null;
    }

    /**
     * Return all items currently carried by the player.
     *
     * @return An ArrayList containing the player's items.
     */
    public ArrayList<Item> getCarriedItems()
    {
        return new ArrayList<>(carriedItems);
    }
    
    
    /**
     * Calculate the total weight of all items
     * currently carried by the player.
     *
     * @return The total weight of carried items.
     */
    public int getCurrentWeight()
    {
        int totalWeight = 0;
        
        for (Item item : carriedItems) {
            totalWeight += item.getWeight();
        }

        return totalWeight;
    }

    /**
     * Increase the maximum weight the player can carry.
     *
     * @param extraWeight The additional carrying capacity.
     */
    public void increaseMaximumWeight(int extraWeight)
    {
        maximumWeight += extraWeight;
    }
}
