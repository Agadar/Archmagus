package com.agadar.archmagus.eventhandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** For applying magical shield effects and the potion effects they apply. */
public class HandlerOnLivingAttack 
{
//	@SubscribeEvent
//	public void onLivingAttack(LivingAttackEvent event)
//	{
//		if (event.entityLiving.worldObj.isRemote)
//		{
//			return;
//		}
//
//		Entity attacker = event.source.getEntity();
//
//		if (attacker != null)
//		{
//			/** Apply projectile immunity. */
//			if (event.entityLiving.isPotionActive(ModPotions.projectileImmunity) && event.source.isProjectile())
//			{
//				event.setCanceled(true);
//			}
//			
//			/** Apply knockback immunity. */
//			if (event.entityLiving.isPotionActive(ModPotions.knockbackImmunity))
//			{
//				event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0F);
//			}
//			else
//			{
//				event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(0F);
//			}
//			
//			/** Apply magical shield effects. */
//			if (event.entityLiving.isPotionActive(ModPotions.fireShield) && attacker instanceof EntityLivingBase)
//			{
//				int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.fireShield).getAmplifier() + 1;			
//				attacker.setFire(4 * amplifier);				
//				attacker.attackEntityFrom(DamageSource.onFire, amplifier);
//			}
//			else if (event.entityLiving.isPotionActive(ModPotions.earthShield))
//			{
//				int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.earthShield).getAmplifier();
//				event.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80, amplifier));
//				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.knockbackImmunity.getId(), 80));
//			}
//			else if (event.entityLiving.isPotionActive(ModPotions.waterShield))
//			{
//				int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.waterShield).getAmplifier();
//				event.entityLiving.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 80, amplifier));
//				event.entityLiving.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 160));
//			}
//			else if (event.entityLiving.isPotionActive(ModPotions.stormShield))
//			{
//				int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.stormShield).getAmplifier();
//				event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 80, amplifier));
//				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.projectileImmunity.getId(), 80));
//			}
//			else if (event.entityLiving.isPotionActive(ModPotions.frostShield) && attacker instanceof EntityLivingBase)
//			{
//				int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.frostShield).getAmplifier();
//				((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 80, amplifier));
//				((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.weakness.getId(), 80, amplifier * 2));
//			}
//		}
//	}
}
