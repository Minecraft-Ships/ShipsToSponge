package org.sships.plugin.cmd;

import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.registry.RegistryTypes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Deprecated
public final class ShipsTestCommand {

    public static class CreateBlockTypeList implements CommandExecutor {

        @Override
        public CommandResult execute(CommandContext context) {
            File file = new File("ShipsTest" + File.pathSeparatorChar + "Items.txt");

            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                RegistryTypes.ITEM_TYPE.get().stream().forEach(it -> {
                    try {
                        writer.write("public static final Optional<BlockType> " + it.key(RegistryTypes.ITEM_TYPE).value().toUpperCase() + " = CorePlugin.getPlatform().get(new ItemTypes1V12(\"" + it.key(RegistryTypes.ITEM_TYPE).asString() + "\"));" + File.separator);
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

    private ShipsTestCommand() {
        throw new RuntimeException("Should not be used");
    }

    public static Command.Parameterized createCommand() {
        return Command.builder().executor(new CreateBlockTypeList()).build();
    }
}
