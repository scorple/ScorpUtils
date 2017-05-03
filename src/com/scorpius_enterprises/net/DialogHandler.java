package com.scorpius_enterprises.net;

import com.scorpius_enterprises.log.Logger;

/**
 * {@link DialogHandler networking.DialogHandler}
 *
 * @author Scorple
 * @since 2017-04-29
 */
public class DialogHandler extends Thread
{
    private Dialog dialog;

    private IDialogListener listener;

    private boolean ready;

    private boolean running = false;

    public DialogHandler(Dialog dialog)
    {
        this.dialog = dialog;
    }

    @Override
    public void run()
    {
        running = true;

        while (running)
        {
            if (dialog != null && dialog.isReady())
            {
                Logger.logD("dialog ready");

                if (!ready)
                {
                    Logger.logD("new connection, notifying listener");

                    notifyListener("Connection established");
                    ready = true;
                }

                Logger.logD("attempting read");

                String msg = dialog.read();

                if (msg != null)
                {
                    Logger.logD("read non-null message, notifying listener");

                    notifyListener(msg);
                }
            }
            else
            {
                if (ready)
                {
                    notifyListener("Connection lost");
                }
                ready = false;
            }
        }
    }

    public void setListener(IDialogListener listener)
    {
        this.listener = listener;
    }

    private void notifyListener(String msg)
    {
        Logger.logD("enter trace");

        if (listener != null)
        {
            Logger.logD("listener non-null");

            listener.newMessage(msg);
        }

        Logger.logD("exit trace");
    }

    public void write(String msg)
    {
        dialog.write(msg);
    }

    public void close()
    {
        dialog.close();
        dialog = null;
    }
}
