package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * {@link Pose com.scorpius_enterprises.io.iqm.Pose}
 *
 * @author Scorple
 * @since 2017-03-26
 */
public class Pose
{
    public static final int POSE_SIZE    = 88;
    public static final int NUM_CHANNELS = 10;

    private int     parent;
    private int     channelMask;
    private float[] channelOffset;
    private float[] channelScale;

    public Pose()
    {
        channelOffset = new float[NUM_CHANNELS];
        channelScale = new float[NUM_CHANNELS];
    }

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfsPoses() + index * POSE_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, POSE_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        parent = bb.getInt();
        Logger.logD("" + parent);

        channelMask = bb.getInt();
        Logger.logD("" + channelMask);

        StringBuilder sbo = new StringBuilder();
        sbo.append("co");
        for (int i = 0; i < NUM_CHANNELS; ++i)
        {
            channelOffset[i] = bb.getFloat();
            sbo.append(" ").append(channelOffset[i]);
        }
        Logger.logD(sbo.toString());

        StringBuilder sbs = new StringBuilder();
        sbs.append("cs");
        for (int i = 0; i < NUM_CHANNELS; ++i)
        {
            channelScale[i] = bb.getFloat();
            sbs.append(" ").append(channelScale[i]);
        }
        Logger.logD(sbs.toString());
    }
}
