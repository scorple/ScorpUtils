package com.scorpius_enterprises.net;

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
        if (out != null)
        {
            out[0] = null;

            if (socket != null)
            {
                try
                {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    try
                    {
                        out[0] = dis.readUTF();
                        return 0;
                    }
                    catch (EOFException e)
                    {
                        return -1;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    return -4;
                }
            }
            return -3;
        }
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
