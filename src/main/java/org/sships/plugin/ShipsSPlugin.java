package org.sships.plugin;

import org.ships.plugin.ShipsPlugin;

import java.io.File;
import java.util.Optional;

public class ShipsSPlugin extends ShipsPlugin {
    @Override
    public File getShipsConigFolder() {
        return new File("config/Ships/");
    }

    @Override
    public Optional<String> checkForUpdates() {
        return Optional.empty();
    }

    @Override
    public Object getBukkitLauncher() {
        return null;
    }

    @Override
    public Object getSpongeLauncher() {
        return ShipsMain.getPlugin();
    }
}
