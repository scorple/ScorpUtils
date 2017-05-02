package com.scorpius_enterprises.net;

import java.io.IOException;
import java.net.Socket;

/**
 * {@link Client}
 *
 * @author Scorple
 * @since 2017-04-29
 */
public class Client extends Dialog
{
    public Client(final String serverAddress)
    {
        ClientConnectionThread clientConnectionThread = new ClientConnectionThread(serverAddress);
        clientConnectionThread.start();
    }

    private class ClientConnectionThread extends Thread
    {
        private String serverAddress;

        ClientConnectionThread(String serverAddress)
        {
            this.serverAddress = serverAddress;
        }

        public void run()
        {
            try
            {
                socket = new Socket(serverAddress, NetworkConstants.SOCKET);
                ready = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
