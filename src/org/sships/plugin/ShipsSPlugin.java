package org.sships.plugin;

import org.ships.plugin.ShipsPlugin;

import java.io.File;

public class ShipsSPlugin extends ShipsPlugin {
    @Override
    public File getShipsConigFolder() {
        return new File("config/Ships/");
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
