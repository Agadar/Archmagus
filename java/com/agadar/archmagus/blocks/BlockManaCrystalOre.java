package com.agadar.archmagus.blocks;

import java.util.Random;
import com.agadar.archmagus.Archmagus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManaCrystalOre extends Block 
{
	public final String Name = "mana_crystal_ore";
	
	private Random rand = new Random();
	
	public BlockManaCrystalOre() 
	{
		super(Material.rock);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypePiston);
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
		this.setLightLevel(0.625F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(this, Name);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		return Archmagus.mana_crystal;
    }
	
	@Override
	public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
    {
        if (p_149679_1_ > 0 && Item.getItemFromBlock(this) != Archmagus.mana_crystal)
        {
            int j = p_149679_2_.nextInt(p_149679_1_ + 2) - 1;

            if (j < 0)
	            j = 0;

            return (j + 1);
        }
        else
        	return 1;
    }
	
    @Override
    public int getExpDrop(net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        if (Archmagus.mana_crystal != Item.getItemFromBlock(this))
        	return MathHelper.getRandomIntegerInRange(rand, 2, 5);
        
        return 0;
    }
}
