package com.morphanone.webizen2;

import com.morphanone.webizen2.commands.SocketCommand;
import org.mcmonkey.denizen2core.Denizen2Core;
import org.mcmonkey.denizen2core.addons.DenizenAddon;

public class Webizen2 extends DenizenAddon {

    @Override
    public void enable() {
        Denizen2Core.register(new SocketCommand());
    }

    @Override
    public void disable() {
        // do disable-y things
    }
}