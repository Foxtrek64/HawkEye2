package com.github.foxtrek_64.HawkEye2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.foxtrek_64.HawkEye2.WorldEdit.WESessionFactory;
import com.github.foxtrek_64.HawkEye2.commands.BaseCommand;
import com.github.foxtrek_64.HawkEye2.commands.DeleteCommand;
import com.github.foxtrek_64.HawkEye2.commands.HelpCommand;
import com.github.foxtrek_64.HawkEye2.commands.HereCommand;
import com.github.foxtrek_64.HawkEye2.commands.InfoCommand;
import com.github.foxtrek_64.HawkEye2.commands.PageCommand;
import com.github.foxtrek_64.HawkEye2.commands.PreviewApplyCommand;
import com.github.foxtrek_64.HawkEye2.commands.PreviewCancelCommand;
import com.github.foxtrek_64.HawkEye2.commands.PreviewCommand;
import com.github.foxtrek_64.HawkEye2.commands.RebuildCommand;
import com.github.foxtrek_64.HawkEye2.commands.ReloadCommand;
import com.github.foxtrek_64.HawkEye2.commands.RollbackCommand;
import com.github.foxtrek_64.HawkEye2.commands.SearchCommand;
import com.github.foxtrek_64.HawkEye2.commands.ToolBindCommand;
import com.github.foxtrek_64.HawkEye2.commands.ToolCommand;
import com.github.foxtrek_64.HawkEye2.commands.ToolResetCommand;
import com.github.foxtrek_64.HawkEye2.commands.TptoCommand;
import com.github.foxtrek_64.HawkEye2.commands.UndoCommand;
import com.github.foxtrek_64.HawkEye2.commands.WriteLogCommand;
import com.github.foxtrek_64.HawkEye2.database.ConnectionManager;
import com.github.foxtrek_64.HawkEye2.database.DataManager;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorBlockListener;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorEntityListener;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorFallingBlockListener;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorHeroChatListener;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorLiquidFlow;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorPlayerListener;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorWorldEditListener;
import com.github.foxtrek_64.HawkEye2.listeners.MonitorWorldListener;
import com.github.foxtrek_64.HawkEye2.listeners.ToolListener;
import com.github.foxtrek_64.HawkEye2.util.Config;
import com.github.foxtrek_64.HawkEye2.util.Util;

import com.dthielke.herochat.Herochat;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class HawkEye extends JavaPlugin {

	public String name;
	public String version;
	public Config config;
	public static Server server;
	public static HawkEye instance;
	public MonitorBlockListener monitorBlockListener = new MonitorBlockListener(this);
	public MonitorEntityListener monitorEntityListener = new MonitorEntityListener(this);
	public MonitorPlayerListener monitorPlayerListener = new MonitorPlayerListener(this);
	public MonitorWorldListener monitorWorldListener = new MonitorWorldListener(this);
	public MonitorFallingBlockListener monitorFBListerner = new MonitorFallingBlockListener(this);
	public MonitorWorldEditListener monitorWorldEditListener = new MonitorWorldEditListener();
	public MonitorLiquidFlow monitorLiquidFlow;
	public ToolListener toolListener = new ToolListener();
	private DataManager dbmanager;
	public MonitorHeroChatListener monitorHeroChatListener = new MonitorHeroChatListener(this);
	public static List<BaseCommand> commands = new ArrayList<BaseCommand>();
	public static HashMap<String, HashMap<String,Integer>> InvSession = new HashMap<String, HashMap<String,Integer>>();
	public static WorldEditPlugin worldEdit = null;
	public static Herochat herochat = null;

	/**
	 * Safely shuts down HawkEye
	 */
	@Override
	public void onDisable() {
		dbmanager.run();
		if (!ConnectionManager.getConnections().isEmpty()) {
			while (ConnectionManager.areConsOpen()) {
				Util.debug("Not ready");
				if (DataManager.getQueue().size() != 0) {
					dbmanager.run();
				}
			}
		}
		DataManager.close();
		Util.info("Version " + version + " disabled!");
	}

	/**
	 * Starts up HawkEye initiation process
	 */
	@Override
	public void onEnable() {
		//Setup metrics
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PluginManager pm = getServer().getPluginManager();

		//Check bukkit dependencies
		try {
			Class.forName("org.bukkit.event.hanging.HangingPlaceEvent");
		} catch (ClassNotFoundException ex) {
			Util.info("HawkEye requires CraftBukkit 1.4+ to run properly!");
			pm.disablePlugin(this);
			return;
		}

		instance = this;
		server = getServer();
		name = this.getDescription().getName();
		version = this.getDescription().getVersion();

		Util.info("Starting HawkEye " + version + " initiation process...");

		//Load config
		config = new Config(this);

		setupUpdater();

		new SessionManager();

		//Initiate database connection
		try {
			this.dbmanager = new DataManager(this);
			getServer().getScheduler().runTaskTimerAsynchronously(this, dbmanager, Config.LogDelay * 20, Config.LogDelay * 20);
		} catch (Exception e) {
			Util.severe("Error initiating HawkEye database connection, disabling plugin");
			pm.disablePlugin(this);
			return;
		}

		checkDependencies(pm);

		//This must be created while the plugin is loading as the constructor is dependent
        monitorLiquidFlow = new MonitorLiquidFlow(this);
        
		registerListeners(pm);
		registerCommands();
		Util.info("Version " + version + " enabled!");
	}

	/**
	 * Checks if required plugins are loaded
	 * @param pm PluginManager
	 */
	private void checkDependencies(PluginManager pm) {
		Plugin we = pm.getPlugin("WorldEdit");
		Plugin hc = pm.getPlugin("Herochat");
		if (we != null) worldEdit = (WorldEditPlugin)we;
		if (hc != null) herochat = (Herochat)hc;
	}

	/**
	 * Registers event listeners
	 * @param pm PluginManager
	 */
	public void registerListeners(PluginManager pm) {
		//Register events
		monitorBlockListener.registerEvents();
		monitorPlayerListener.registerEvents();
		monitorEntityListener.registerEvents();
		monitorWorldListener.registerEvents();
		monitorFBListerner.registerEvents();
		monitorLiquidFlow.registerEvents();
		monitorLiquidFlow.startCacheCleaner();
		pm.registerEvents(toolListener, this);
		if (herochat != null) monitorHeroChatListener.registerEvents();

		if (worldEdit != null)  {
			if (DataType.SUPER_PICKAXE.isLogged()) pm.registerEvents(monitorWorldEditListener, this); //Yes we still need to log superpick!
			
			//This makes sure we OVERRIDE any other plugin that tried to register a EditSessionFactory!
			if (DataType.WORLDEDIT_BREAK.isLogged() || DataType.WORLDEDIT_PLACE.isLogged()) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						WESessionFactory.enableWELogging();
					}
				}, 2L);
			}
		}
	}

	/**
	 * Registers commands for use by the command manager
	 */
	private void registerCommands() {

		//Add commands
		commands.add(new HelpCommand());
		commands.add(new ToolBindCommand());
		commands.add(new ToolResetCommand());
		commands.add(new ToolCommand());
		commands.add(new SearchCommand());
		commands.add(new PageCommand());
		commands.add(new TptoCommand());
		commands.add(new HereCommand());
		commands.add(new PreviewApplyCommand());
		commands.add(new PreviewCancelCommand());
		commands.add(new PreviewCommand());
		commands.add(new RollbackCommand());
		commands.add(new UndoCommand());
		commands.add(new RebuildCommand());
		commands.add(new DeleteCommand());
		commands.add(new InfoCommand());
		commands.add(new WriteLogCommand());
		commands.add(new ReloadCommand());
	}

	/**
	 * Command manager for HawkEye
	 * @param sender - {@link CommandSender}
	 * @param cmd - {@link Command}
	 * @param commandLabel - String
	 * @param args[] - String[]
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {
		if (cmd.getName().equalsIgnoreCase("hawk")) {
			if (args.length == 0)
				args = new String[]{"help"};
			outer:
				for (BaseCommand command : commands.toArray(new BaseCommand[0])) {
					String[] cmds = command.name.split(" ");
					for (int i = 0; i < cmds.length; i++)
						if (i >= args.length || !cmds[i].equalsIgnoreCase(args[i])) continue outer;
					return command.run(this, sender, args, commandLabel);
				}
			new HelpCommand().run(this, sender, args, commandLabel);
			return true;
		}
		return false;
	}

	private void setupUpdater() {
		if (getConfig().getBoolean("general.check-for-updates")) 
			new Updater(this, "hawkeye-reload", this.getFile(), Updater.UpdateType.DEFAULT, false);
	}
}
