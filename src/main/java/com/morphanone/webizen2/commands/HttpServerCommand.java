package com.morphanone.webizen2.commands;

import com.morphanone.webizen2.servers.http.WebizenHttpServer;
import org.mcmonkey.denizen2core.commands.AbstractCommand;
import org.mcmonkey.denizen2core.commands.CommandEntry;
import org.mcmonkey.denizen2core.commands.CommandQueue;
import org.mcmonkey.denizen2core.tags.objects.IntegerTag;
import org.mcmonkey.denizen2core.utilities.CoreUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpServerCommand extends AbstractCommand {

    // <--[command]
    // @Name httpserver
    // @Arguments "start"/"stop" <port>
    // @Short Controls an HTTP server.
    // @Updated 2016/08/28
    // @Authors Morphan1
    // @Group Webizen2
    // @Addon Webizen2
    // @Minimum 2
    // @Maximum 2
    // @Description
    // Starts or stops an HTTP server on the specified port. The server will listen for incoming
    // HTTP requests and will fire off related ScriptEvents.
    // TODO: Explain more!
    // @Example
    // # This example opens an HTTP web socket on port 8080.
    // - httpserver start 8080
    // -->

    @Override
    public String getName() {
        return "httpserver";
    }

    @Override
    public String getArguments() {
        return "'start'/'stop' <port>";
    }

    @Override
    public int getMinimumArguments() {
        return 2;
    }

    @Override
    public int getMaximumArguments() {
        return 2;
    }

    @Override
    public boolean isProcedural() {
        return false;
    }

    public static final Map<Integer, WebizenHttpServer> httpServerMap = new HashMap<>();

    @Override
    public void execute(CommandQueue queue, CommandEntry entry) {
        String action = CoreUtilities.toLowerCase(entry.getArgumentObject(queue, 0).toString());
        int port = (int) IntegerTag.getFor(queue.error, entry.getArgumentObject(queue, 1)).getInternal();
        if (action.equals("start")) {
            if (httpServerMap.containsKey(port)) {
                queue.handleError(entry, "Server is already running on port " + port + ".");
            }
            else {
                try {
                    WebizenHttpServer httpServer = new WebizenHttpServer(port);
                    httpServer.start();
                    httpServerMap.put(port, httpServer);
                }
                catch (IOException e) {
                    queue.handleError(entry, "Failed to create new HTTP server due to an exception.");
                }
            }
        }
        else if (action.equals("stop")) {
            if (httpServerMap.containsKey(port)) {
                httpServerMap.get(port).stop();
                httpServerMap.remove(port);
            }
            else {
                queue.handleError(entry, "There is no server running on port " + port + ".");
            }
        }
        else {
            queue.handleError(entry, "Invalid action in HttpServer command!");
        }
    }
}