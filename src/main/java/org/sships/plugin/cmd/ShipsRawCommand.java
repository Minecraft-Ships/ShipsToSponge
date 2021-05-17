package org.sships.plugin.cmd;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.core.CorePlugin;
import org.core.command.CommandLauncher;
import org.core.exceptions.NotEnoughArguments;
import org.core.source.command.CommandSource;
import org.ships.implementation.sponge.platform.SpongePlatform;
import org.spongepowered.api.SystemSubject;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.ArgumentReader;
import org.spongepowered.api.entity.Entity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShipsRawCommand implements Command.Raw {

    private final CommandLauncher launcher;

    public ShipsRawCommand(CommandLauncher launcher) {
        this.launcher = launcher;
    }

    private CommandSource getSource(Object audience) {
        SpongePlatform platform = ((SpongePlatform) CorePlugin.getPlatform());
        if (audience instanceof Entity) {
            return (CommandSource) platform.createEntityInstance((Entity) audience);
        } else if (audience instanceof SystemSubject) {
            return CorePlugin.getConsole();
        } else {
            throw new IllegalStateException("Unknown conversion from " + audience.toString());
        }
    }

    @Override
    public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) {
        String[] args = arguments.input().split("/");
        Audience audience = cause.audience();
        CommandSource source = getSource(audience);
        try {
            boolean result = this.launcher.run(source, args);
            return result ? CommandResult.success() : CommandResult.empty();
        } catch (NotEnoughArguments notEnoughArguments) {
            audience.sendMessage(Component.text("Not enough arguments").color(NamedTextColor.RED));
            return CommandResult.empty();
        }
    }

    @Override
    public List<CommandCompletion> complete(CommandCause cause, ArgumentReader.Mutable arguments) {
        String[] args = arguments.input().split("/");
        Audience audience = cause.audience();
        SpongePlatform platform = ((SpongePlatform) CorePlugin.getPlatform());
        CommandSource source = getSource(audience);
        return this.launcher.tab(source, args).stream().map(CommandCompletion::of).collect(Collectors.toList());
    }

    @Override
    public boolean canExecute(CommandCause cause) {
        return this.launcher.hasPermission(getSource(cause.subject()));
    }

    @Override
    public Optional<Component> shortDescription(CommandCause cause) {
        return Optional.empty();
    }

    @Override
    public Optional<Component> extendedDescription(CommandCause cause) {
        return Optional.empty();
    }

    @Override
    public Component usage(CommandCause cause) {
        return Component.text(this.launcher.getUsage(getSource(cause.audience()))).color(NamedTextColor.WHITE);
    }
}
