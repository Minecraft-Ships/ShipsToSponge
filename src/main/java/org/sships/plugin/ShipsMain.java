package org.sships.plugin;

import com.google.inject.Inject;
import org.ships.implementation.sponge.CoreToSponge;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.jvm.Plugin;
import org.sships.plugin.cmd.ShipsTestCommand;

@Plugin(value = ShipsMain.PLUGIN_ID)
public class ShipsMain {

    public static final String PLUGIN_ID = "ships";
    public static final String PLUGIN_NAME = "Ships";
    public static final String PLUGIN_DESCRIPTION = "Move structures with a single click";
    public static final String PLUGIN_VERSION = "6.0.0.0";

    private static ShipsMain plugin;
    private final PluginContainer container;

    @Inject
    public ShipsMain(PluginContainer container){
        plugin = this;
        this.container = container;
    }

    public void onRegisterCommand(RegisterCommandEvent<Command.Parameterized> event){
        event.register(this.container, ShipsTestCommand.createCommand(), "shipstest");
    }

    public void onEngineEvent(StartedEngineEvent<Server> event){
        new CoreToSponge(this.container);
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
