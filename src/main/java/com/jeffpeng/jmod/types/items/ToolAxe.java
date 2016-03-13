package com.jeffpeng.jmod.types.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;

import com.jeffpeng.jmod.JMOD;
import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.interfaces.IItem;
import com.jeffpeng.jmod.interfaces.ITool;
import com.jeffpeng.jmod.util.descriptors.ToolDataDescriptor;

public class ToolAxe extends ItemAxe implements ITool, IItem {

	public CreativeTabs creativetab;
	private String internalName;
	private JMODRepresentation owner;

	public ToolAxe(JMODRepresentation owner, ToolDataDescriptor desc) {
		super(ToolMaterial.valueOf(desc.toolmat));
		this.owner = owner;
	}

	@Override
	public String getPrefix() {
		return owner.getModId();
	}

	@Override
	public void setName(String name) {
		this.internalName = name;
		this.setUnlocalizedName(getPrefix() + "." + name);
	}

	@Override
	public void register() {
		JMOD.DEEPFORGE.registerItem(this, this.internalName, owner.getModId());
	}

	@Override
	public void setRecipes() {
	}

}