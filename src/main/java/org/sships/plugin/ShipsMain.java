package org.sships.plugin;

import org.ships.implementation.sponge.CoreToSponge;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.sships.plugin.cmd.ShipsTestCommand;

@Plugin(id = ShipsMain.PLUGIN_ID, name = ShipsMain.PLUGIN_NAME, description = ShipsMain.PLUGIN_DESCRIPTION, version = ShipsMain.PLUGIN_VERSION)
public class ShipsMain {

    public static final String PLUGIN_ID = "ships";
    public static final String PLUGIN_NAME = "Ships";
    public static final String PLUGIN_DESCRIPTION = "Move structures with a single click";
    public static final String PLUGIN_VERSION = "6.0.0.0";

    private static ShipsMain plugin;

    public ShipsMain(){
        plugin = this;
    }

    @Listener
    public void onInit(GameStartingServerEvent event){
        Sponge.getCommandManager().register(this, ShipsTestCommand.createCommand(), "shipstest");
        new CoreToSponge(this);

    }

    @Listener
    public void onEnable(GameStartedServerEvent event){
        try {
            ShipsSPlugin plugin = new ShipsSPlugin();
            plugin.loadCustomShipType();
            plugin.loadVessels();
            plugin.getLoadedMessages();
        }catch (ExceptionInInitializerError e){
            e.getException().printStackTrace();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public static ShipsMain getPlugin(){
        return plugin;
    }
}
