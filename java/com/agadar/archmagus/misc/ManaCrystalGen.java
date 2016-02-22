package com.agadar.archmagus.misc;

import java.util.Random;

import com.agadar.archmagus.itemblock.ModItemsBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

/** Responsible for generating mana crystal ores in the Nether. */
public class ManaCrystalGen implements IWorldGenerator 
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimensionId())
		{
		case -1:
			this.generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			this.generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}
	
	/** Generates blocks in the Nether. Currently only used for mana crystal ores. */
	private void generateNether(World world, Random random, int x, int z)
	{
		this.generateOre(ModItemsBlocks.mana_crystal_ore, world, random, x, z, 16, 16, 3 + random.nextInt(6), 3, 1, 127, Blocks.netherrack);
	}

	/** Generates blocks in the Overworld. Currently unused. */
	private void generateSurface(World world, Random random, int x, int z) { }
	
	/** Generates blocks in the End. Currently unused. */
	private void generateEnd(World world, Random random, int x, int z) { }

	/** Generates an ore vein. */
	public void generateOre(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY, Block target)
	{     
		for(int x = 0; x < chancesToSpawn; x++)
		{
			int posX = blockXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(maxY - minY);
			int posZ = blockZPos + random.nextInt(maxZ);
			(new WorldGenMinable(block, maxVeinSize, target)).generate(world, random, posX, posY, posZ);
		}
	}
}
