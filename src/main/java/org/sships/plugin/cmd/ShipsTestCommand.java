package org.sships.plugin.cmd;

import org.core.source.command.CommandSource;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShipsTestCommand {

    public static class CreateBlockTypeList implements CommandExecutor {

        @Override
        public CommandResult execute(CommandContext context) throws CommandException {
            File file = new File("ShipsTest/Items.txt");
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                Sponge.getRegistry().getCatalogRegistry().streamAllOf(org.spongepowered.api.item.ItemType.class).forEach(it -> {
                    try {
                        writer.write("public static final Optional<BlockType> " + it.getKey().getValue().toUpperCase() + " = CorePlugin.getPlatform().get(new ItemTypes1V12(\"" + it.getKey().asString() + "\"));\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return CommandResult.success();
        }
    }

    public static Command.Parameterized createCommand(){
        return Command.builder().setExecutor(new CreateBlockTypeList()).build();
    }
}
