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
    private int   firstFrame;
    private int   numFrames;
    private float frameRate;
    private int   flags;

    private String nameString;

    public void load(final Header header,
                     final byte[] buf,
                     final int index)
    {
        int offset = header.getOfsAnims() + index * ANIM_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf,
                                        offset,
                                        ANIM_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        name = bb.getInt();
        Logger.logD("" + name);

        firstFrame = bb.getInt();
        Logger.logD("" + firstFrame);

        numFrames = bb.getInt();
        Logger.logD("" + numFrames);

        frameRate = bb.getFloat();
        Logger.logD("" + frameRate);

        flags = bb.getInt();
        Logger.logD("" + flags);

        bb = ByteBuffer.wrap(buf);
        bb.position(header.getOfsText() + name);

        StringBuilder sb = new StringBuilder();
        byte[]        b  = new byte[1];
        String        s;
        while ((b[0] = bb.get()) != '\0')
        {
            s = new String(b);
            sb.append(s);
        }

        nameString = sb.toString();
        Logger.logD(nameString);
    }

    public int getName()
    {
        return name;
    }

    public int getFirstFrame()
    {
        return firstFrame;
    }

    public int getNumFrames()
    {
        return numFrames;
    }

    public float getFrameRate()
    {
        return frameRate;
    }

    public int getFlags()
    {
        return flags;
    }

    public String getNameString()
    {
        return nameString;
    }
}
