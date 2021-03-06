package com.jeffpeng.jmod.types.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.jeffpeng.jmod.Config;
import com.jeffpeng.jmod.JMOD;
import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.interfaces.IBlock;
import com.jeffpeng.jmod.types.blocks.placers.CoreBlockPlacer;

public class CoreBlock extends Block implements IBlock {

	private CoreBlockPlacer placer;
	protected String internalName;
	protected JMODRepresentation owner;
	protected Config config; 

	protected CreativeTabs creativetab = null;

	public CoreBlock(JMODRepresentation owner,Material mat) {
		super(mat);
		this.owner = owner;
		this.config = owner.getConfig();
	}

	public String getPrefix() {
		return owner.getModId();
	}

	public void setPlacer(CoreBlockPlacer placer) {
		this.placer = placer;
	}

	public void setName(String name) {
		
		this.internalName = name;
		this.setBlockName(getPrefix() + "." + name);
	}

	public String getName() {
		return this.internalName;
	}

	public boolean hasPlacer() {
		if (this.placer != null) {
			return true;
		}
		return false;
	}
	
    @Override
    public boolean renderAsNormalBlock()    {        return false;    }
    
    
    @Override
	public void register() {
		if(this.placer == null){
			JMOD.DEEPFORGE.registerBlock(this, this.internalName, owner);
		} else {
			JMOD.DEEPFORGE.registerBlock(this, placer, this.internalName, owner);
		}
	}

	@Override
	public JMODRepresentation getOwner() {
		return owner;
	}
	
	
	

}
