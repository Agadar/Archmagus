package com.agadar.archmagus.items;

import java.util.List;

import com.agadar.archmagus.Archmagus;
import com.agadar.archmagus.network.ManaProperties;
import com.agadar.archmagus.network.MaxManaMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The Mana Apple that permanently increases a player's maximum mana by 2. */
public class ItemAppleMana extends ItemFood 
{
	public final String Name = "apple_mana";
	
	public ItemAppleMana()
    {
        super(4, 1.2F, false);
        this.setAlwaysEdible();
        this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
        GameRegistry.registerItem(this, Name);
    }
	
	/** Makes the item tooltip text a light blue color. */
    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.RARE;
    }
    
	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	par3List.add("Increases Maximum Mana +2");
    }
    
    @Override
    protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        if (!world.isRemote)
        {
        	ManaProperties props = ManaProperties.get(entityPlayer);
    		int maxMana = props.getMaxMana();
    		
    		if (maxMana <= 18)
    		{
    			maxMana += 2;
	    		props.setMaxMana(maxMana);
	    		Archmagus.networkWrapper.sendTo(new MaxManaMessage(maxMana), (EntityPlayerMP) entityPlayer);
	    		entityPlayer.addChatMessage(new ChatComponentText("Your maximum Mana has increased by 2!"));
    		}
        }
    }
}
