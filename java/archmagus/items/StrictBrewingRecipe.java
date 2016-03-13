package archmagus.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipe;

/**
 * Brewing recipe that respects the NBTTagCompound of the input.
 */
public class StrictBrewingRecipe extends BrewingRecipe
{
	public StrictBrewingRecipe(ItemStack input, ItemStack ingredient, ItemStack output)
	{
		super(input, ingredient, output);
	}
	
	@Override
	public boolean isInput(ItemStack stack)
    {
        return super.isInput(stack) && ((!this.input.hasTagCompound() && !stack.hasTagCompound()) ||
        		(this.input.hasTagCompound() && this.input.getTagCompound().equals(stack.getTagCompound())));
    }
}
