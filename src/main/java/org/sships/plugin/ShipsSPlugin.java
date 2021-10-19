package org.sships.plugin;

import org.jetbrains.annotations.NotNull;
import org.ships.plugin.ShipsPlugin;

public class ShipsSPlugin extends ShipsPlugin {

    @Override
    public @NotNull ShipsMain getPlatformLauncher() {
        return ShipsMain.getPlugin();
    }


}
