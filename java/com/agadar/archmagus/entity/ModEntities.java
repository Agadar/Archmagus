package com.agadar.archmagus.entity;

/** Responsible for registering this mod's entities and their renderers. */
public class ModEntities 
{
	/** Registers this mod's entities and their renderers. */
	public static void registerModEntities()
	{
		/** Register the entities */
		EntityRegistry.registerGlobalEntityID(EntitySummonedWolf.class, "summoned_wolf", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityRisenSkeleton.class, "risen_skeleton", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityRisenWitherSkeleton.class, "risen_wither_skeleton", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityRisenZombie.class, "risen_zombie", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityRisenZombiePigman.class, "risen_zombie_pigman", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntitySummonedWitch.class, "summoned_witch", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntitySummonedSpider.class, "summoned_spider", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntitySummonedCaveSpider.class, "summoned_cave_spider", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerGlobalEntityID(EntityRisenHorse.class, "risen_horse", EntityRegistry.findGlobalUniqueEntityId());
	}
}
