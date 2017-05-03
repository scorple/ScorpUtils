package com.scorpius_enterprises.net;

import com.scorpius_enterprises.log.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * {@link SocketUtils networking.SocketUtils}
 *
 * @author Scorple
 * @since 2017-04-29
 */
abstract class SocketUtils
{
    static int readUTF(final Socket socket, final String[] out)
    {
        Logger.logD("enter trace");

        if (out != null)
        {
            Logger.logD("out != null");

            out[0] = null;

            if (socket != null)
            {
                Logger.logD("socket != null");

                try
                {
                    Logger.logD("getting dis");

                    DataInputStream dis = new DataInputStream(socket.getInputStream());

                    Logger.logD("got dis");

                    try
                    {
                        Logger.logD("performing read");

                        out[0] = dis.readUTF();

                        Logger.logD("exit trace");

                        return 0;
                    }
                    catch (EOFException e)
                    {
                        Logger.logD("exit trace");

                        return -1;
                    }
                    catch (Exception e)
                    {
                        Logger.logD("exit trace");

                        return -5;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();

                    Logger.logD("exit trace");

                    return -4;
                }
            }
            Logger.logD("exit trace");

            return -3;
        }
        Logger.logD("exit trace");

        return -2;
    }

    static int writeUTF(final Socket socket, final String out)
    {
        if (out != null)
        {
            if (socket != null)
            {
                try
                {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(out);
                    return 0;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return -3;
                }
            }
            return -2;
        }
        return -1;
    }
}
