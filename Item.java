
/**
 * Represents an item in the Super Text Adventure game.
 *
 * Each item has a description and a weight.
 * Items can be collected by the player, carried
 * in an inventory, and dropped into rooms.
 *
 * Created by Garry Ovide for the Super Text Adventure
 * project, based on the World of Zuul game.
 *
 * @author Garry Ovide
 * @version 2026.11.06
 */
public class Item
{
    private String description;
    private int weight;

    /**
     * Create an item with a description and weight.
     *
     * @param description The description of the item.
     * @param weight The weight of the item.
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }

    /**
     * Return the item's description.
     *
     * @return The description of the item.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return the item's weight.
     *
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Return information about the item.
     *
     * @return The item's description and weight.
     */
    public String getItemInfo()
    {
        return description + " (weight: " + weight + ")";
    }
}
