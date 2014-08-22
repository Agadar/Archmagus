package com.agadar.archmagus.spell;

import com.agadar.archmagus.entity.EntityRisenSkeleton;
import com.agadar.archmagus.entity.EntityRisenWitherSkeleton;
import com.agadar.archmagus.entity.EntityRisenZombie;
import com.agadar.archmagus.entity.EntityRisenZombiePigman;
import com.agadar.archmagus.entity.EntitySummonedCaveSpider;
import com.agadar.archmagus.entity.EntitySummonedHorse;
import com.agadar.archmagus.entity.EntitySummonedSpider;
import com.agadar.archmagus.entity.EntitySummonedWitch;
import com.agadar.archmagus.entity.EntitySummonedWolf;
import com.agadar.archmagus.spell.aoe.SpellBlazeStorm;
import com.agadar.archmagus.spell.aoe.SpellLightningStorm;
import com.agadar.archmagus.spell.shield.SpellEarthShield;
import com.agadar.archmagus.spell.shield.SpellFireShield;
import com.agadar.archmagus.spell.shield.SpellFrostShield;
import com.agadar.archmagus.spell.shield.SpellStormShield;
import com.agadar.archmagus.spell.shield.SpellWaterShield;
import com.agadar.archmagus.spell.summon.SpellSummon;
import com.agadar.archmagus.spell.summon.SpellSummonMount;
import com.agadar.archmagus.spell.targeted.SpellBlazeFire;
import com.agadar.archmagus.spell.targeted.SpellGhastFire;
import com.agadar.archmagus.spell.targeted.SpellTeleport;
import com.agadar.archmagus.spell.targeted.SpellWitherBlast;

/** Manages all Spells. */
public class Spells 
{
	/** The list of all spells. */
	public final static Spell[] spellList = new Spell[256];
	/** All individual spells. */
	public final static Spell blazefire = new SpellBlazeFire(1);
	public final static Spell ghastfire = new SpellGhastFire(2);
	public final static Spell witherblast = new SpellWitherBlast(3);
	public final static Spell summon_wolf = new SpellSummon(4, "wolf", EntitySummonedWolf.class);
	public final static Spell raise_skeleton = new SpellSummon(5, "skeleton", EntityRisenSkeleton.class);
	public final static Spell raise_wither_skeleton = new SpellSummon(6, "wither_skeleton", EntityRisenWitherSkeleton.class);
	public final static Spell raise_zombie = new SpellSummon(7, "zombie", EntityRisenZombie.class);
	public final static Spell raise_zombie_pigman = new SpellSummon(8, "zombie_pigman", EntityRisenZombiePigman.class);
	public final static Spell summon_witch = new SpellSummon(9, "witch", EntitySummonedWitch.class);
	public final static Spell summon_spider = new SpellSummon(10, "spider", EntitySummonedSpider.class);
	public final static Spell summon_cave_spider = new SpellSummon(11, "cave_spider", EntitySummonedCaveSpider.class);
	public final static Spell teleport = new SpellTeleport(12);
	public final static Spell respawn = new SpellRespawn(13);
	public final static Spell fireShield = new SpellFireShield(14);
	public final static Spell earthShield = new SpellEarthShield(15);
	public final static Spell waterShield = new SpellWaterShield(16);
	public final static Spell stormShield = new SpellStormShield(17);
	public final static Spell frostShield = new SpellFrostShield(18);
	public final static Spell blazestorm = new SpellBlazeStorm(19);
	public final static Spell lightningstorm = new SpellLightningStorm(20);
	public final static Spell raise_zombie_horse = new SpellSummonMount(21, "zombie_horse", EntitySummonedHorse.class, 3);
	public final static Spell raise_skeleton_horse = new SpellSummonMount(22, "skeleton_horse", EntitySummonedHorse.class, 4);
	
	/** Registers a new spell at the given id. */
	public static void registerSpell(Spell par1Spell, int par2effectId)
	{
		if (spellList[par2effectId] != null) 
			throw new IllegalArgumentException("Duplicate spell id!");
        else 
        	spellList[par2effectId] = par1Spell;
	}
}
