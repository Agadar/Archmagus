package com.agadar.archmagus.entity;

import com.agadar.archmagus.Archmagus;

import net.minecraftforge.fml.common.registry.EntityRegistry;

/** Responsible for registering this mod's entities and their renderers. */
public class ModEntities 
{
	/** Registers this mod's entities and their renderers. */
	public static void registerModEntities()
	{
		/** Register the entities */
		int id = 0;
		EntityRegistry.registerModEntity(EntitySummonedWolf.class, "summoned_wolf", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntityRisenSkeleton.class, "risen_skeleton", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntityRisenWitherSkeleton.class, "risen_wither_skeleton", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntityRisenZombie.class, "risen_zombie", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntityRisenZombiePigman.class, "risen_zombie_pigman", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntitySummonedWitch.class, "summoned_witch", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntitySummonedSpider.class, "summoned_spider", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntitySummonedCaveSpider.class, "summoned_cave_spider", id++, Archmagus.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntityRisenHorse.class, "risen_horse", id++, Archmagus.instance, 80, 3, false);
	}
}
