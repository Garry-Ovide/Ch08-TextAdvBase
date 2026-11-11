import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Represents a room in the Super Text Adventure game.
 *
 * Each room has a description, exits to neighboring
 * rooms, and a collection of items.
 *
 * Each item has a description and a weight.
 * Players can take items from rooms and drop
 * carried items into rooms.
 *
 * Originally written by Michael Kölling
 * and David J. Barnes.
 * Updated by Garry Ovide.
 *
 * @author Garry Ovide
 * @version 2026.11.06
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }
    
    /**
     * Place an item in this room.
     *
     * @param item The item to add to the room.
     */
    public void addItem(Item item)
    {
          items.add(item);
    }
    
    
    /**
     * Remove an item from this room by matching its
     * description with the player's search word.
     *
     * @param description The item name entered by the player.
     * @return The removed item, or null if no item matches.
     */
    public Item removeItem(String description)
    {
        Iterator<Item> iterator = items.iterator();
        
        while (iterator.hasNext()) {
            Item item = iterator.next();
            
            if (item.getDescription().toLowerCase()
            .contains(description.toLowerCase())) {
                iterator.remove();
                return item;
            }
        }

        return null;
    }

    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    
    /**
     * Return the room description, exits, and all items
     * contained in the room.
     *
     * @return The full description of the room,
     *         including exits and items.
     */
    public String getLongDescription()
    {
        String roomInfo = "You are " + description + ".\n";
    roomInfo += getExitString();
    
    if (!items.isEmpty()) {
        roomInfo += "\nItems in this room:";

        for (Item item : items) {
            roomInfo += "\n- " + item.getItemInfo();
        }
    }
        return roomInfo;
    }


    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

