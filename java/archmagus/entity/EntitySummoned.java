package archmagus.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntitySummoned extends EntityTameable implements ISummoned
{
	public EntitySummoned(World par1World) 
	{
		super(par1World);
		
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        
        this.setTamed(true);
        this.setAttackTarget((EntityLivingBase)null);
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable var1) 
	{
		return null;
	}
    
    @Override
    protected Item getDropItem()
    {
    	return Item.getItemById(-1);
    }
    
    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return false;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 0;
    }

    @Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
        return false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }
    
    @Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.worldObj.isRemote) 
        	return;
        
        EntityLivingBase owner = this.getOwner();
        if (owner == null || owner.isDead || owner.dimension != this.dimension) 
        	this.attackEntityFrom(DamageSource.generic, this.getMaxHealth());
    }
    
    @Override
    protected String getFallSoundString(int damageValue)
    {
        return damageValue > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }
    
    @Override
    public void setAttackTarget(EntityLivingBase e)
    {
    	EntityLivingBase owner = this.getOwner();
    	
    	if (e == owner || (e instanceof EntitySummoned && ((EntitySummoned)e).getOwner() == owner))
    		return;
    	
    	super.setAttackTarget(e);
    }
}
