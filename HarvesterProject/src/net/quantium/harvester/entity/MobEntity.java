package net.quantium.harvester.entity;

import net.quantium.harvester.item.ItemSlot;
import net.quantium.harvester.item.instances.ToolItem;
import net.quantium.harvester.item.instances.ToolItem.ToolType;
import net.quantium.harvester.world.World;

public abstract class MobEntity extends LivingEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int damage;
	
	@Override
	public void bump(Entity ent) {
		if(ent instanceof PlayerEntity){
			((LivingEntity) ent).hit(damage);
		}
	}
	
	@Override
	public boolean onInteract(World world2, PlayerEntity playerEntity, InteractionMode im, ItemSlot active) {
		if(active != null && ((active.getItem() instanceof ToolItem) && ((ToolItem) active.getItem()).getToolType() == ToolType.DAGGER))
			hit(((ToolItem) active.getItem()).level * 2 + 2);
		else
			hit(active != null ? active.getItem().getPower() : 1);
		return true;
	}
	
	public int getDamage(){
		return damage;
	}
}
