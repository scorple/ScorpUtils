package com.scorpius_enterprises.net;

import com.scorpius_enterprises.log.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * {@link Dialog networking.Dialog}
 *
 * @author Scorple
 * @since 2017-04-30
 */
abstract class Dialog
{
    Socket socket;

    boolean ready = false;

    String read()
    {
        Logger.logD("enter trace");

        String[] msg = new String[1];

        switch (SocketUtils.readUTF(socket, msg))
        {
            case 0:
            case -1:
                Logger.logD("exit trace");

                return msg[0];
            default:
                ready = false;
                close();
        }

        Logger.logD("exit trace");

        return null;
    }

    void write(final String msg)
    {
        switch (SocketUtils.writeUTF(socket, msg))
        {
            case 0:
                break;
            default:
                ready = false;
                close();
        }
    }

    boolean isReady()
    {
        ready = socket != null && socket.isBound() && !socket.isClosed() && socket.isConnected();

        return ready;
    }

    void close()
    {
        if (socket != null)
        {
            try
            {
                //write("~close");
                socket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
