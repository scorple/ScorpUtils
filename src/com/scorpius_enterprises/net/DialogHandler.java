package com.scorpius_enterprises.net;

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
                if (!ready)
                {
                    notifyListener("Connection established");
                    ready = true;
                }

                String msg = dialog.read();

                if (msg != null)
                {
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
        if (listener != null)
        {
            listener.newMessage(msg);
        }
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
