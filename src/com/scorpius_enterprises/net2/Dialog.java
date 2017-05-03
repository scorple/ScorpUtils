package com.scorpius_enterprises.net2;

import java.io.IOException;
import java.net.Socket;

/**
 * {@link Dialog com.scorpius_enterprises.net2.Dialog}
 *
 * @author Scorple
 * @since 2017-05-02
 */
class Dialog implements IDialog
{
    private boolean open;

    Socket socket;

    IDialogListener listener;

    @Override
    public void open()
    {
        open = true;
        Reader reader = new Reader();
        reader.start();
    }

    private void read(final String msg)
    {
        listener.notifyMessage(msg);
    }

    void status(final String sts)
    {
        listener.notifyStatus(sts);
    }

    void error(final String err)
    {
        listener.notifyError(err);
    }

    @Override
    public void write(final String out)
    {
        int res = SocketUtils.writeUTF(socket, out);

        if (res < 0)
        {
            error("write error");
            close();
        }
    }

    @Override
    public boolean isReady()
    {
        return (socket != null && socket.isBound() && socket.isConnected() && !socket.isClosed());
    }

    @Override
    public void close()
    {
        open = false;

        if (socket != null)
        {
            try
            {
                socket.close();

                status("connection closed");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        socket = null;
    }

    private class Reader extends Thread implements IReadListener
    {
        @Override
        public void run()
        {
            while (open)
            {
                if (isReady())
                {
                    int res = SocketUtils.readUTF(this, socket);

                    if (res < 0)
                    {
                        error("read error");
                        close();
                    }
                }
            }
        }

        @Override
        public void read(final String in)
        {
            Dialog.this.read(in);
        }
    }
}
