package org.sships.plugin;

import org.core.config.ConfigurationStream;
import org.jetbrains.annotations.NotNull;
import org.ships.implementation.sponge.configuration.YAMLConfigurationFile;
import org.ships.plugin.ShipsPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

public class ShipsSPlugin extends ShipsPlugin {

    @Override
    public File getShipsConigFolder() {
        return new File("config/Ships/");
    }

    @Override
    public @NotNull ShipsMain getPlatformLauncher() {
        return ShipsMain.getPlugin();
    }



}
