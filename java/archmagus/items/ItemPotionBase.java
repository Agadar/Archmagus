package archmagus.items;

import java.util.ArrayList;
import java.util.List;

import archmagus.Archmagus;
import archmagus.potion.ModPotions;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The ItemPotion base for our mod. Inherits from Item as opposed to ItemPotion due to problems
 * with brewing recipes when we inherit from ItemPotion. Hence, most methods delegate functionality
 * to the ItemPotion instance in vanilla's Items.
 * @author marti
 *
 */
public class ItemPotionBase extends Item 
{
	public final String Name = "potion_base";
	
	public ItemPotionBase()
	{
		super();
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
		this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
        GameRegistry.registerItem(this, Name);
	}
	
	public List<PotionEffect> getEffects(ItemStack stack)
    {
    	return Items.potionitem.getEffects(stack);
    }

    public List<PotionEffect> getEffects(int meta)
    {
    	return Items.potionitem.getEffects(meta);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
    	return Items.potionitem.onItemUseFinish(stack, worldIn, playerIn);
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
    	return Items.potionitem.getMaxItemUseDuration(stack); 
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
    	return Items.potionitem.getItemUseAction(stack);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	return Items.potionitem.onItemRightClick(itemStackIn, worldIn, playerIn);
    }

    /**
     * returns wether or not a potion is a throwable splash potion based on damage value
     */
    public static boolean isSplash(int meta)
    {
    	return ItemPotion.isSplash(meta);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromDamage(int meta)
    {
        return Items.potionitem.getColorFromDamage(meta);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
    	return Items.potionitem.getItemStackDisplayName(stack);
    }

    @SideOnly(Side.CLIENT)
    public boolean isEffectInstant(int meta)
    {
    	return Items.potionitem.isEffectInstant(meta);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
    	Items.potionitem.addInformation(stack, playerIn, tooltip, advanced);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
    	return Items.potionitem.hasEffect(stack);
    }

	@SuppressWarnings("unchecked")
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, @SuppressWarnings("rawtypes") List par3List)
    {
		/** Potion of Mana. */
		par3List.add(getManaStack(false, 0));
		/** Potion of Mana (Amplified). */
		par3List.add(getManaStack(false, 1));
		/** Splash Potion of Mana. */
		par3List.add(getManaStack(true, 0));
		/** Splash Potion of Mana (Amplified). */
		par3List.add(getManaStack(true, 1));
		
		/** Potion of Mana Regeneration. */
		par3List.add(getManaRegenStack(false, 0, false));
		/** Potion of Mana Regeneration (Amplified). */
		par3List.add(getManaRegenStack(false, 1, false));
		/** Potion of Mana Regeneration (Extended). */
		par3List.add(getManaRegenStack(false, 0, true));
		/** Splash Potion of Mana Regeneration. */
		par3List.add(getManaRegenStack(true, 0, false));
		/** Splash Potion of Mana Regeneration (Amplified). */
		par3List.add(getManaRegenStack(true, 1, false));
		/** Splash Potion of Mana Regeneration (Extended). */
		par3List.add(getManaRegenStack(true, 0, true));
    }
	
	/** Returns an item stack of a Mana potion. */
	public static ItemStack getManaStack(boolean splash, int amplification)
	{
		ItemStack itemStack = new ItemStack(Archmagus.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));		
		setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Mana Regeneration potion. */
	public static ItemStack getManaRegenStack(boolean splash, int amplification, boolean extended)
	{
		ItemStack itemStack = new ItemStack(Archmagus.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.manaRegen.id, (int)(900 * getDurationModifier(splash, amplification, extended)), amplification));		
		setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Returns an item stack of a Restoration potion. */
	public static ItemStack getRestorationStack(boolean splash, int amplification)
	{
		ItemStack itemStack = new ItemStack(Archmagus.itemPotionBase, 1, splash ? 16384 : 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.mana.id, 1, amplification));	
		effects.add(new PotionEffect(Potion.heal.id, 1, amplification));
		setEffects(itemStack, effects);
		return itemStack;
	}
	
	/** Translates the given List of PotionEffects to an NBTTagList and adds it to the given ItemStack's NBTTagCompound. */
    public static void setEffects(ItemStack par1ItemStack, List<PotionEffect> par2Effects)
    {
    	par1ItemStack.setTagCompound(new NBTTagCompound());
    	par1ItemStack.getTagCompound().setTag("CustomPotionEffects", new NBTTagList());
    	NBTTagList effectsTagList = par1ItemStack.getTagCompound().getTagList("CustomPotionEffects", 10);
    	
    	for (PotionEffect effect : par2Effects)
    	{
    		NBTTagCompound effectTag = new NBTTagCompound();
    		effect.writeCustomPotionEffectToNBT(effectTag);
    		effectsTagList.appendTag(effectTag);
    	}
    }
    
    /** Calculates the duration modifier for a potion effect according to the given parameters. */
	public static float getDurationModifier(boolean splash, int amplification, boolean extended)
	{
		float modifier = splash ? 0.75F : 1.0F;		
		for (int i = 0; i < amplification; i++)
			modifier /= 2;
		return extended ? modifier * 8 / 3 : modifier;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
		return 16777215;
    }
}
