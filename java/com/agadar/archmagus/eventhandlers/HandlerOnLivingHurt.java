package com.agadar.archmagus.eventhandlers;

import com.agadar.archmagus.potions.ModPotions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/** For applying magical shield effects. */
public class HandlerOnLivingHurt 
{
	@SubscribeEvent
	public void onLivingHurt(LivingAttackEvent event)
	{
		if (event.entityLiving.worldObj.isRemote)
		{
			return;
		}

		Entity attacker = event.source.getEntity();

		if (attacker != null)
		{
			/** Apply projectile immunity. */
			if (event.entityLiving.isPotionActive(ModPotions.projectileImmunity) && event.source.isProjectile())
			{
				event.setCanceled(true);
			}
			
			/** Apply magical shield effects. */
			if (event.entityLiving.isPotionActive(ModPotions.fireShield) && attacker instanceof EntityLivingBase)
			{
				attacker.setFire(4);				
				attacker.attackEntityFrom(DamageSource.onFire, 1);
			}
			else if (event.entityLiving.isPotionActive(ModPotions.earthenShield))
			{
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80));
				
				/** TODO: Implement the knockback immunity. */
			}
			else if (event.entityLiving.isPotionActive(ModPotions.waterShield))
			{
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 80));
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 80));
			}
			else if (event.entityLiving.isPotionActive(ModPotions.aetherShield))
			{
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 80));
				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.projectileImmunity.getId(), 80));
			}
			else if (event.entityLiving.isPotionActive(ModPotions.frostArmor) && attacker instanceof EntityLivingBase)
			{
				((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 80));
				((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.weakness.getId(), 80));
			}
		}
	}
}
