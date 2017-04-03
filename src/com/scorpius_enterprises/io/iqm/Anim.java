package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * {@link Anim com.scorpius_enterprises.io.iqm.Anim}
 *
 * @author Scorple
 * @since 2017-04-03
 */
public class Anim
{
    public static final int ANIM_SIZE = 20;

    private int   name;
    private int   first_frame;
    private int   num_frames;
    private float framerate;
    private int   flags;

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfs_anims() + index * ANIM_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, ANIM_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        name = bb.getInt();
        Logger.logD("" + name);

        first_frame = bb.getInt();
        Logger.logD("" + first_frame);

        num_frames = bb.getInt();
        Logger.logD("" + num_frames);

        framerate = bb.getFloat();
        Logger.logD("" + framerate);

        flags = bb.getInt();
        Logger.logD("" + flags);
    }
}
