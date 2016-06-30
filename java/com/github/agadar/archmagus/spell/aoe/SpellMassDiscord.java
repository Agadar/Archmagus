package com.github.agadar.archmagus.spell.aoe;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * The spell that causes mass discord.
 */
public class SpellMassDiscord extends SpellAoE
{
	@Override
	public int getManaCost()
	{
		return 8;
	}

	@Override
	public short getCooldown()
	{
		return 1200;
	}

	@Override
	public String getName()
	{
		return "spell.mass_discord";
	}

	@Override
	public String getModelResourceLocationString()
	{
		return super.getModelResourceLocationString() + "wither_skeleton_book";
	}

	@SuppressWarnings({ })
	@Override
	protected void affectEntity(World par1World, EntityLivingBase par2EntityLivingBase)
	{
		// Only apply to EntityMobs, EntitySlimes, and EntityGhasts.
//		if (!(par2EntityLivingBase instanceof EntityMob || par2EntityLivingBase instanceof EntitySlime || par2EntityLivingBase instanceof EntityGhast))
//			return;
//
// 		// Allow the entity to target ANY other entity.
// 		((EntityLiving)par2EntityLivingBase).targetTasks.addTask(0, new EntityAINearestAttackableTarget((EntityCreature)par2EntityLivingBase, EntityLivingBase.class, true));
// 		
// 		// Reset attack targets.
// 		((EntityLiving)par2EntityLivingBase).setAttackTarget(null);
// 		par2EntityLivingBase.setRevengeTarget(null);
// 		
// 		// Set new attack tasks depending on creature type.
// 		// Ghast: #AIFireballAttack.
// 		if (par2EntityLivingBase instanceof EntityGhast)
// 			par2EntityLivingBase.tasks.addTask(7, new EntityGhast.AIFireballAttack(this));
// 		
// 		((EntityCreature)par2EntityLivingBase).tasks.addTask(1, new EntityAIAttackOnCollide((EntityCreature)par2EntityLivingBase, EntityLivingBase.class, 1.0D, false));
	}
}
