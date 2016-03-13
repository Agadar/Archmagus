package archmagus.spell.summon;

import java.lang.reflect.Constructor;
import java.util.List;

import archmagus.entity.EntityRisenHorse;
import archmagus.entity.EntitySummoned;
import archmagus.spell.Spell;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityTameable;

/** Summons a friendly minion that follows and protects the player. */
public class SpellSummon extends Spell 
{
	/** The constructor of the entity this spell summons. 
	 *  It is assumed the only parameter is a World reference. */
	@SuppressWarnings("rawtypes")
	protected final Constructor entityConstr;
	private final String creatureName;
	
	@SuppressWarnings({ "rawtypes" })
	public SpellSummon(String par1Name, Class par2EntityClass)
	{
		super();
		this.creatureName = par1Name;	
		entityConstr = getConstructor(par2EntityClass);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static Constructor getConstructor(Class par1EntityClass)
	{
		try 
		{
			return par1EntityClass.getConstructor(World.class);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public short getMaxLevel()
    {
        return 4;
    }
	
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
        return "spell.summon." + creatureName;
    }
	
	@Override
	public String getDescription()
    {
    	return "spell.description.summon.minion";
    }
	
	@Override
	public String getModelResourceLocationString()
    {
    	return super.getModelResourceLocationString() + creatureName + "_book";
    }

	@Override
	public boolean castSpell(short par1Level, World par2World, EntityPlayer par3EntityPlayer) 
	{
		// Play sound and kill existing summoned minions.
		par2World.playSoundAtEntity(par3EntityPlayer, this.getSoundName(), 1.0F, 1.0F);
		SpellSummon.killExistingMinions(par2World, par3EntityPlayer);
		
		try 
		{
			// The positions around the player where the minions will spawn.
			int[] xSpawnOffset = { -2, 0, 2, 0 };
			int[] zSpawnOffset = { 0, 2, 0, -2 };

			// Summon one minion per spell level.
			for (int i = 0; i < getNormalizedLevel(par1Level); i++)
			{
				EntityCreature entity = (EntityCreature) entityConstr.newInstance(par2World);	
				BlockPos blockPos1 = new BlockPos(par3EntityPlayer.posX + xSpawnOffset[i], par3EntityPlayer.posY, par3EntityPlayer.posZ + zSpawnOffset[i]);
				Block block1 = par2World.getBlockState(blockPos1).getBlock();
				
				// If the block the minion should spawn in or the block above it is solid (makes the minion suffocate)
				// then instead spawn the minion on the player. Likewise if the block below the spawn place is not solid.
                if (block1.getMaterial().isSolid() || (par2World.getBlockState(blockPos1.up()).getBlock().getMaterial().isSolid()) ||
                		!(par2World.getBlockState(blockPos1.down(2)).getBlock().getMaterial().isSolid()))
                	entity.setLocationAndAngles(par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, entity.rotationYaw, 0.0F);
                else
					entity.moveToBlockPosAndAngles(blockPos1, entity.rotationYaw, 0.0F);
								
                // Set minion name, owner, et cetera, then spawn it in the world.
				String comSendName = par3EntityPlayer.getCommandSenderEntity().getName();
				((EntityTameable) entity).setOwnerId(par3EntityPlayer.getUniqueID().toString());
				entity.setCustomNameTag(comSendName + "'s Minion");
				entity.setAlwaysRenderNameTag(true);
				par2World.spawnEntityInWorld(entity);
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/** Kills all of the player's existing summoned minions. 
	 *  Should be called every time before new minions are summoned. 
	 *  Kills off EntitySummoned and EntitySummonedHorse, both which implement ISummoned. */
	public static void killExistingMinions(World par1World, EntityPlayer par2EntityPlayer) 
	{
		List<EntitySummoned> minions = par1World.getEntitiesWithinAABB(EntitySummoned.class, par2EntityPlayer.getEntityBoundingBox().expand(20.0D, 20.0D, 20.0D));
		List<EntityRisenHorse> mounts = par1World.getEntitiesWithinAABB(EntityRisenHorse.class, par2EntityPlayer.getEntityBoundingBox().expand(20.0D, 20.0D, 20.0D));
		
		for (EntitySummoned minion : minions)
		{
			if (minion.getOwner() == par2EntityPlayer)
				minion.attackEntityFrom(DamageSource.generic, minion.getMaxHealth());	
		}
		
		for (EntityRisenHorse mount : mounts)
		{
			if (mount.getTamedBy() == par2EntityPlayer)
				mount.attackEntityFrom(DamageSource.generic, mount.getMaxHealth());	
		}
	}
}
