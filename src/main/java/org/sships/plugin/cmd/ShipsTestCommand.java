package org.sships.plugin.cmd;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShipsTestCommand {

    public static class CreateBlockTypeList implements CommandExecutor {

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            File file = new File("ShipsTest/Items.txt");
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                Sponge.getRegistry().getAllOf(org.spongepowered.api.item.ItemType.class).stream().forEach(it -> {
                    try {
                        writer.write("public static final Optional<BlockType> " + it.getId().substring(10).toUpperCase() + " = CorePlugin.getPlatform().get(new ItemTypes1V12(\"" + it.getId() + "\"));\n");
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

    public static CommandSpec createCommand(){
        return CommandSpec.builder().executor(new CreateBlockTypeList()).build();
    }
}
