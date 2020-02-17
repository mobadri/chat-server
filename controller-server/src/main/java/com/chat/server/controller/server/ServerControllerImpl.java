package com.chat.server.controller.server;

import com.chat.server.network.server.Server;

public class ServerControllerImpl implements ServerController {
    private ServerController serverController;
    private Server server;

    public ServerControllerImpl() {
        server = Server.getInstance();
    }

    @Override
    public void startServer() {
        server.startServer();
    }

    @Override
    public void stopServer() {
        server.stopServer();
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }
}
