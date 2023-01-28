package org.sships.plugin;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.core.TranslateCore;
import org.core.command.CommandRegister;
import org.core.implementation.sponge.CoreToSponge;
import org.core.implementation.sponge.platform.SLogger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.sships.plugin.cmd.ShipsRawCommand;

@Plugin(ShipsMain.PLUGIN_ID)
public class ShipsMain {

    public static final String PLUGIN_ID = "ships";
    public static final String PLUGIN_NAME = "Ships";
    public static final String PLUGIN_DESCRIPTION = "Move structures with a single click";
    public static final String PLUGIN_VERSION = "6.0.0.0";

    private static ShipsMain plugin;
    private final PluginContainer container;
    private final SLogger logger;
    private ShipsSPlugin ships;

    @Inject
    public ShipsMain(PluginContainer container, Logger logger) {
        this.container = container;
        this.logger = new SLogger(logger);

    }

    public static ShipsMain getPlugin() {
        return plugin;
    }

    public PluginContainer getContainer() {
        return this.container;
    }

    @Listener
    public void onConstruct(ConstructPluginEvent event) {
        plugin = this;
        this.ships = new ShipsSPlugin();
    }

    @Listener
    public void onRegisterCompatibleCommands(RegisterCommandEvent<? super Command.Raw> event) {
        CommandRegister cmdReg = new CommandRegister();
        this.ships.onRegisterCommands(cmdReg);
        cmdReg
                .getCommands()
                .forEach(command -> event.register(this.container, new ShipsRawCommand(command), command.getName()));
    }

    @Listener
    public void onEngineEvent(StartedEngineEvent<Server> event) {
        new CoreToSponge(this.container);
        this.ships.onConstruct(this, this.logger);
        this.ships.onCoreReady();
    }

    @Listener
    public void onServerLoaded(StartedEngineEvent<Server> event) {
        Sponge.server().scheduler().executor(this.container).submit(() -> {
            if (!TranslateCore.CoreImplementation.hasStarted()) {
                this.onServerLoaded(event);
                return;
            }
            this.ships.onCoreFinishedInit();
        });
    }
}
