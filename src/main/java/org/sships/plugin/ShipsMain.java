package org.sships.plugin;

import org.core.command.CommandRegister;
import org.core.implementation.sponge.CoreToSponge;
import org.spongepowered.api.Server;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.type.MatterTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.fluid.FluidType;
import org.spongepowered.api.fluid.FluidTypes;
import org.spongepowered.api.state.BooleanStateProperties;
import org.spongepowered.api.state.EnumStateProperties;
import org.spongepowered.api.state.IntegerStateProperties;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.sships.plugin.cmd.ShipsRawCommand;
import org.sships.plugin.cmd.ShipsTestCommand;

@Plugin(value = ShipsMain.PLUGIN_ID)
public class ShipsMain {

    public static final String PLUGIN_ID = "ships";
    public static final String PLUGIN_NAME = "Ships";
    public static final String PLUGIN_DESCRIPTION = "Move structures with a single click";
    public static final String PLUGIN_VERSION = "6.0.0.0";

    private static ShipsMain plugin;
    private PluginContainer container;
    private ShipsSPlugin ships;

    public PluginContainer getContainer() {
        return this.container;
    }

    @Listener
    public void onConstruct(ConstructPluginEvent event) {
        plugin = this;
        this.container = event.plugin();
        this.ships = new ShipsSPlugin();
        this.ships.onConstruct(this);
    }

    @Listener
    public void onRegisterCommand(RegisterCommandEvent<? super Command.Parameterized> event) {
        event.register(this.container, ShipsTestCommand.createCommand(), "shipstest");
    }

    @Listener
    public void onRegisterCompatibleCommands(RegisterCommandEvent<? super Command.Raw> event) {
        CommandRegister cmdReg = new CommandRegister();
        this.ships.onRegisterCommands(cmdReg);
        cmdReg.getCommands().forEach(command -> event.register(this.container, new ShipsRawCommand(command), command.getName()));
    }

    @Listener
    public void onEngineEvent(StartedEngineEvent<Server> event) {
        new CoreToSponge(this.container);
        this.ships.onCoreReady();

        try {
            this.ships.loadCustomShipType();
            this.ships.loadVesselTypeFlagData();
            this.ships.loadVessels();
            this.ships.getLoadedMessages();
        } catch (ExceptionInInitializerError e) {
            e.getException().printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static ShipsMain getPlugin() {
        return plugin;
    }
}
