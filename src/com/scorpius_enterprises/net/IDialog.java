package com.scorpius_enterprises.net;

/**
 * {@link IDialog com.scorpius_enterprises.net.IDialog}
 *
 * @author Scorple
 * @since 2017-05-02
 */
public interface IDialog
{
    void write(final String out);

    boolean isReady();

    void close();
}
