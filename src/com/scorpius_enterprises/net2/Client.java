package com.scorpius_enterprises.net2;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * {@link Client com.scorpius_enterprises.net2.Client}
 *
 * @author Scorple
 * @since 2017-05-02
 */
public class Client extends Dialog
{
    public Client(final IDialogListener listener, final String serverAddress)
    {
        this.listener = listener;

        ClientConnector clientConnector = new ClientConnector(serverAddress);
        clientConnector.start();

        status("client started");
    }

    private class ClientConnector extends Thread
    {
        private String serverAddress;

        ClientConnector(String serverAddress)
        {
            this.serverAddress = serverAddress;
        }

        @Override
        public void run()
        {
            try
            {
                socket = new Socket(serverAddress, NetworkConstants.SOCKET);

                status("connected to server");

                open();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
