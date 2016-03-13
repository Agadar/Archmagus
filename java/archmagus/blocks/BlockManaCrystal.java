package archmagus.blocks;

import archmagus.Archmagus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManaCrystal extends Block 
{
	public final String Name = "mana_crystal_block";
	
	public BlockManaCrystal() 
	{
		super(Material.iron);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundTypePiston);
		this.setUnlocalizedName(Archmagus.MODID + "_" + Name);
		this.setLightLevel(0.625F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(this, Name);
	}
}
