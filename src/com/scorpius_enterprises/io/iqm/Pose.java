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
    public static final int POSE_SIZE = 88;

    private int     parent;
    private int     channelmask;
    private float[] channeloffset;
    private float[] channelscale;

    public Pose()
    {
        channeloffset = new float[10];
        channelscale = new float[10];
    }

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfs_poses() + index * POSE_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, POSE_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        parent = bb.getInt();
        Logger.logD("" + parent);

        channelmask = bb.getInt();
        Logger.logD("" + channelmask);

        StringBuilder sbo = new StringBuilder();
        sbo.append("co");
        for (int i = 0; i < 10; ++i)
        {
            channeloffset[i] = bb.getFloat();
            sbo.append(" ").append(channeloffset[i]);
        }
        Logger.logD(sbo.toString());

        StringBuilder sbs = new StringBuilder();
        sbs.append("cs");
        for (int i = 0; i < 10; ++i)
        {
            channelscale[i] = bb.getFloat();
            sbs.append(" ").append(channelscale[i]);
        }
        Logger.logD(sbs.toString());
    }
}
