package com.jeffpeng.jmod;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;

import com.jeffpeng.jmod.crafting.AnvilHandler;
import com.jeffpeng.jmod.crafting.DropHandler;
import com.jeffpeng.jmod.crafting.ToolRepairRecipe;
import com.jeffpeng.jmod.modintegration.rotarycraft.PatchRoCSteelTools;
import com.jeffpeng.jmod.primitives.JMODInfo;
import com.jeffpeng.jmod.scripting.JScript;
import com.jeffpeng.jmod.tooltipper.ToolTipper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class JMODRepresentation {

	private JScript script;
	private Config config = new Config();
	private Lib lib; 
	private JMODContainer container;
	
	private Logger log;
	private JMODInfo modinfo;
	
	private boolean zipMod = false;
	private JMODRepresentation instance = this;
	private boolean scriptingFinished = false;
	
	public JMODRepresentation(JMODInfo modinfo){
		this(modinfo,false);
	}
	
	public JMODRepresentation(JMODInfo modinfo, boolean zipmod){
		this.zipMod = zipmod;
		this.modinfo = modinfo;
		this.log = LogManager.getLogger(""+modinfo.modid);
		this.lib = new Lib(this);

	}
	
	protected void runScripts(){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				script = new JScript(instance);
				for(String entry : modinfo.scripts){
					script.evalScript(entry);
				}
				scriptingFinished = true;
			}
		});

		t.setName(modinfo.modid + "/StartupScript");
		t.start();
	}
	
	public JMODInfo getModInfo(){
		return this.modinfo;
	}
	

	public void on(FMLConstructionEvent event) {
		
	}	

	public void on(FMLPreInitializationEvent event) {
		if(!JMOD.isDevVersion())lib.checkDependencies();
	}

	public void on(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ToolTipper(this));
		if(config.enhancedAnvilRepair)		MinecraftForge.EVENT_BUS.register(new AnvilHandler(this));
		if(config.blockDrops.size() > 0) 	MinecraftForge.EVENT_BUS.register(new DropHandler(this));
	}

	public void on(FMLPostInitializationEvent event) {
		if(Loader.isModLoaded("RotaryCraft") &&	config.patchRotarycraftSteelTools) PatchRoCSteelTools.patchRoCSteelTools();
		if(config.craftingGridToolRepair)		GameRegistry.addRecipe(new ToolRepairRecipe(this));
		
		
	}

	public void on(FMLLoadCompleteEvent event){
		
	}
	
	protected void setContainer(JMODContainer container){
		this.container = container;
	}
	
	public JMODContainer getContainer(){
		return this.container;
	}
	
	public String getModId(){
		return modinfo.modid;
	}
	
	public String getModName(){
		return modinfo.name;
	}
	
	public String getVersion(){
		return modinfo.version;
	}
	
	public Config getConfig(){
		return config;
	}
	
	public Lib getLib(){
		return lib;
	}
	
	public Logger getLogger(){
		return log;
	}
	
	public boolean isZipMod(){
		return zipMod;
	}
	
	public boolean testForMod(String modId){
		if(!Loader.isModLoaded(modId)){
			log.warn(this.getModName() + " tries to do something that requires " + modId + " to be loaded - but it isn't. This is either an error or lazy scripting.)");
			return false;
		}
		return true;
	}
	
	public boolean isScriptingFinished(){
		return scriptingFinished;
	}
	

}