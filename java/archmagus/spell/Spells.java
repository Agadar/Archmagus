package archmagus.spell;

import archmagus.entity.EntityRisenHorse;
import archmagus.entity.EntityRisenSkeleton;
import archmagus.entity.EntityRisenWitherSkeleton;
import archmagus.entity.EntityRisenZombie;
import archmagus.entity.EntityRisenZombiePigman;
import archmagus.entity.EntitySummonedCaveSpider;
import archmagus.entity.EntitySummonedSpider;
import archmagus.entity.EntitySummonedWitch;
import archmagus.entity.EntitySummonedWolf;
import archmagus.potion.ModPotions;
import archmagus.spell.aoe.SpellBlazeStorm;
import archmagus.spell.aoe.SpellLightningStorm;
import archmagus.spell.buff.SpellBuff;
import archmagus.spell.buff.SpellPacifism;
import archmagus.spell.buff.SpellShield;
import archmagus.spell.summon.SpellSummon;
import archmagus.spell.summon.SpellSummonMount;
import archmagus.spell.targeted.SpellBlazeFire;
import archmagus.spell.targeted.SpellGhastFire;
import archmagus.spell.targeted.SpellLightning;
import archmagus.spell.targeted.SpellTeleport;
import archmagus.spell.targeted.SpellWitherBlast;

/** Manages all Spells. */
public final class Spells 
{
	/** The list of all spells. */
	public final static Spell[] spellList = new Spell[256];
	/** Integer used for generating unique indexes for spells. */
	private static int nextIndex = 0;
	/** All individual spells of Archmagus. */
	public final static Spell blazefire = new SpellBlazeFire();
	public final static Spell ghastfire = new SpellGhastFire();
	public final static Spell witherblast = new SpellWitherBlast();
	public final static Spell lightning = new SpellLightning();
	public final static Spell summon_wolf = new SpellSummon("wolf", EntitySummonedWolf.class);
	public final static Spell raise_skeleton = new SpellSummon("skeleton", EntityRisenSkeleton.class);
	public final static Spell raise_wither_skeleton = new SpellSummon("wither_skeleton", EntityRisenWitherSkeleton.class);
	public final static Spell raise_zombie = new SpellSummon("zombie", EntityRisenZombie.class);
	public final static Spell raise_zombie_pigman = new SpellSummon("zombie_pigman", EntityRisenZombiePigman.class);
	public final static Spell summon_witch = new SpellSummon("witch", EntitySummonedWitch.class);
	public final static Spell summon_spider = new SpellSummon("spider", EntitySummonedSpider.class);
	public final static Spell summon_cave_spider = new SpellSummon("cave_spider", EntitySummonedCaveSpider.class);
	public final static Spell teleport = new SpellTeleport();
	public final static Spell respawn = new SpellRespawn();
	public final static Spell fireShield = new SpellShield("fire", ModPotions.fireShield);
	public final static Spell earthShield = new SpellShield("earth", ModPotions.earthShield);
	public final static Spell waterShield = new SpellShield("water", ModPotions.waterShield);
	public final static Spell stormShield = new SpellShield("storm", ModPotions.stormShield);
	public final static Spell frostShield = new SpellShield("frost", ModPotions.frostShield);
	public final static Spell blazestorm = new SpellBlazeStorm();
	public final static Spell lightningstorm = new SpellLightningStorm();
	public final static Spell raise_zombie_horse = new SpellSummonMount("zombie_horse", EntityRisenHorse.class, 3);
	public final static Spell raise_skeleton_horse = new SpellSummonMount("skeleton_horse", EntityRisenHorse.class, 4);
	public final static Spell slowFall = new SpellBuff("slowfall", ModPotions.slowFall);
	public final static Spell pacifism = new SpellPacifism();
	
	/** Registers a new spell. */
	public final static int registerSpell(Spell par1Spell)
	{
		if (spellList[nextIndex] != null) 
			throw new IllegalArgumentException("Tried to register a spell Id that is already in use!");
        else 
        	spellList[nextIndex] = par1Spell;
		
		return nextIndex++;
	}
	
	/**
	 * Simple getter that returns the next unique index for spellList.
	 *
	 * @return
	 */
	public final static int getNextIndex()
	{
		return nextIndex;
	}
}
