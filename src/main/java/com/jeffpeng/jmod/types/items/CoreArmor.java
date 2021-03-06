package com.jeffpeng.jmod.types.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.jeffpeng.jmod.Config;
import com.jeffpeng.jmod.JMODRepresentation;
import com.jeffpeng.jmod.descriptors.ColorDescriptor;
import com.jeffpeng.jmod.interfaces.IArmor;

public class CoreArmor extends ItemArmor implements IArmor {
	
	private String internalName;
	public CreativeTabs creativetab;
	private String matName;
	private String armorType;
	private Config config;
	private JMODRepresentation owner;
	
	
	public CoreArmor(JMODRepresentation owner, String mat,String armorType){
		super(ItemArmor.ArmorMaterial.valueOf(mat),2,translateArmorType(armorType));
		this.owner = owner;
		this.config = owner.getConfig();
		this.matName = mat;
		this.armorType = armorType;
	}
	
	private static int translateArmorType(String armorType){
		int armortypeint = 0;
		
		switch(armorType){
			case "helmet":armortypeint = 0;break;
			case "chest":armortypeint = 1;break;
			case "chestplate":armortypeint = 1;break;
			case "leggings":armortypeint = 2;break;
			case "boots":armortypeint = 3;break;
		}
		
		return armortypeint;
	}

	@Override
	public void setName(String name){
		this.internalName = name;
		this.setUnlocalizedName(getPrefix()+"."+name);
	}
	
	@Override
	public String getName(){
		return this.internalName;
	}

	@Override
	public Item setTextureName(String texname){
		//super.setTextureName(texname);
		switch(this.armorType){
			case "helmet":super.setTextureName(getPrefix() + ":itemHelmetGeneric");break;
			case "chest":super.setTextureName( getPrefix() + ":itemChestplateGeneric");break;
			case "chestplate":super.setTextureName( getPrefix() + ":itemChestplateGeneric");break;
			case "leggings":super.setTextureName(getPrefix() + ":itemLeggingsGeneric");break;
			case "boots":super.setTextureName(getPrefix() + ":itemBootsGeneric");break;
		}
		
		return this;
	}

	@Override
	public Item setMaxStackSize(int stacksize) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasColor(ItemStack is){
		return true;
	}
	
	@Override 
	public int getColor(ItemStack is){
		ColorDescriptor armorColor = config.colors.get(matName);
		int color = armorColor.red*256*256 + armorColor.green*256 + armorColor.blue;
		return color;
	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return owner.getModId();
	}

	@Override
	public JMODRepresentation getOwner() {
		return owner;
	}

}
