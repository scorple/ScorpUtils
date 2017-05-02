package com.scorpius_enterprises.net;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * {@link Server}
 *
 * @author Scorple
 * @since 2017-04-29
 */
public class Server extends Dialog
{
    public Server()
    {
        ServerConnectionThread serverConnectionThread = new ServerConnectionThread();
        serverConnectionThread.start();
    }

    private class ServerConnectionThread extends Thread
    {
        public void run()
        {
            try
            {
                ServerSocket serverSocket = new ServerSocket(NetworkConstants.SOCKET);
                socket = serverSocket.accept();
                serverSocket.close();
                ready = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
