package org.sships.plugin;

import org.ships.implementation.sponge.CoreToSponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = ShipsMain.PLUGIN_ID, name = ShipsMain.PLUGIN_NAME, version = ShipsMain.PLUGIN_VERSION)
public class ShipsMain {

    public static final String PLUGIN_ID = "ships";
    public static final String PLUGIN_NAME = "Ships";
    public static final String PLUGIN_VERSION = "6.0.0.0";

    private static ShipsMain plugin;

    public ShipsMain(){
        this.plugin = this;
    }

    @Listener
    public void onInit(GameStartingServerEvent event){
        new CoreToSponge(this);

    }

    @Listener
    public void onEnable(GameStartedServerEvent event){
        ShipsSPlugin plugin = new ShipsSPlugin();
        plugin.getLoadedMessages();
    }

    public static ShipsMain getPlugin(){
        return plugin;
    }
}
