package archmagus.eventhandler;

import archmagus.potion.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** For handling buff effects and the potion effects they apply. */
public class HandlerBuffEvents
{
	/**
	 * Called when an entity attacks another entity.
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event)
	{
		if (event.entityLiving.worldObj.isRemote)
			return;

		Entity attacker = event.source.getEntity();

		if (attacker == null)
			return;

		// If the attacker has Pacifism, remove it.
		if (attacker instanceof EntityLivingBase && ((EntityLivingBase)attacker).isPotionActive(ModPotions.pacifism))
			((EntityLivingBase)attacker).removePotionEffect(ModPotions.pacifism.getId());
		
		// If entity has projectile immunity and is struck by a projectile, ignore the damage.
		if (event.entityLiving.isPotionActive(ModPotions.projectileImmunity) && event.source.isProjectile())
			event.setCanceled(true);
		
		// If entity has knockback immunity, give him full knockback resistance. Otherwise, take it away.
		if (event.entityLiving.isPotionActive(ModPotions.knockbackImmunity))
			event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0F);		
		else
			event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.knockbackResistance).setBaseValue(0F);

		// If fire shield is active, damage and set fire to attacker. 
		// Damage = level
		// Fire duration = level * 3
		if (event.entityLiving.isPotionActive(ModPotions.fireShield) && attacker instanceof EntityLivingBase)
		{
			int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.fireShield).getAmplifier() + 1;
			attacker.setFire(3 * amplifier);
			attacker.attackEntityFrom(DamageSource.onFire, amplifier);
		} 
		// If earth shield is active, give the defender resistance and knockback immunity.
		// Knockback immunity duration is always 4 seconds.
		// Resistance duration is always 4 seconds. 1/5 less damage per hit taken per level.
		else if (event.entityLiving.isPotionActive(ModPotions.earthShield))
		{
			int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.earthShield).getAmplifier();
			event.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 80, amplifier));
			event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.knockbackImmunity.getId(), 80));
		} 
		// If water shield is active, give the defender regeneration and fire resistance.
		// Regeneration duration is always 4 seconds. 1 health per 25/level ticks regenerated.
		// Fire resistance duration is always 4 seconds. 
		else if (event.entityLiving.isPotionActive(ModPotions.waterShield))
		{
			int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.waterShield).getAmplifier();
			event.entityLiving.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 80, amplifier));
			event.entityLiving.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 80));
		}
		// If storm shield is active, give the defender increased movement speed and projectile immunity.
		// Movement speed duration is always 4 seconds. 1/5 greater speed per level.
		// Projectile immunity duration is always 4 seconds.
		else if (event.entityLiving.isPotionActive(ModPotions.stormShield))
		{
			int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.stormShield).getAmplifier();
			event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 80, amplifier));
			event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.projectileImmunity.getId(), 80));
		} 
		// If frost shield is active, cause slowness and weakness to the attacker.
		// Slowness duration is always 4 seconds. 15% slower movement speed per level.
		// Weakness duration is always 4 seconds. Decreases damage dealt by 0.5 per level.
		else if (event.entityLiving.isPotionActive(ModPotions.frostShield) && attacker instanceof EntityLivingBase)
		{
			int amplifier = event.entityLiving.getActivePotionEffect(ModPotions.frostShield).getAmplifier();
			((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 80, amplifier));
			((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.weakness.getId(), 80, amplifier));
		}
	}
	
	/**
	 * For adjusting the player's falling speed when under the influence of Slow Fall.
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void OnPlayerUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && event.entityLiving.fallDistance > 0 && event.entityLiving.isPotionActive(ModPotions.slowFall)) 
		{		
			event.entityLiving.motionY *= 0.6d;
			event.entityLiving.fallDistance = event.entityLiving.worldObj.isRemote ? 0.0F : -0.2F;
		}
	}
	
	/**
	 * Ensures hostile mobs don't attack the target when target has the Pacifism buff active.
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) 
	{
		if (!event.entityLiving.worldObj.isRemote && event.target != null && 
				event.target.isPotionActive(ModPotions.pacifism) && event.entityLiving instanceof EntityLiving)
		{
			((EntityLiving)event.entityLiving).setAttackTarget(null);
			((EntityLiving)event.entityLiving).setRevengeTarget(null);
		}		
	}
}
