package com.scorpius_enterprises.net;

/**
 * {@link IDialogListener com.scorpius_enterprises.net.IDialogListener}
 *
 * @author Scorple
 * @since 2017-05-02
 */
public interface IDialogListener
{
    void notifyMessage(final String message);

    void notifyStatus(final String status);

    void notifyError(final String error);
}
