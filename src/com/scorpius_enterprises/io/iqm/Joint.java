package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * {@link Joint com.scorpius_enterprises.io.iqm.Joint}
 *
 * @author Scorple
 * @since 2017-04-02
 */
public class Joint
{
    public static final int JOINT_SIZE         = 48;
    public static final int TRANSLATE_CHANNELS = 3;
    public static final int ROTATE_CHANNELS    = 4;
    public static final int SCALE_CHANNELS     = 3;

    private int   name;
    private int   parent;
    private float translate[];
    private float rotate[];
    private float scale[];

    private String nameString;

    public Joint()
    {
        translate = new float[TRANSLATE_CHANNELS];
        rotate = new float[ROTATE_CHANNELS];
        scale = new float[SCALE_CHANNELS];
    }

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfsJoints() + index * JOINT_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, JOINT_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        name = bb.getInt();
        Logger.logD("" + name);

        parent = bb.getInt();
        Logger.logD("" + parent);

        StringBuilder sbt = new StringBuilder();
        sbt.append("jt");
        for (int i = 0; i < TRANSLATE_CHANNELS; ++i)
        {
            translate[i] = bb.getFloat();
            sbt.append(" ").append(translate[i]);
        }
        Logger.logD(sbt.toString());

        StringBuilder sbr = new StringBuilder();
        sbt.append("jr");
        for (int i = 0; i < ROTATE_CHANNELS; ++i)
        {
            rotate[i] = bb.getFloat();
            sbr.append(" ").append(rotate[i]);
        }
        Logger.logD(sbr.toString());

        StringBuilder sbs = new StringBuilder();
        sbt.append("js");
        for (int i = 0; i < SCALE_CHANNELS; ++i)
        {
            scale[i] = bb.getFloat();
            sbs.append(" ").append(scale[i]);
        }
        Logger.logD(sbs.toString());

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

    public int getParent()
    {
        return parent;
    }

    public float[] getTranslate()
    {
        return translate;
    }

    public float[] getRotate()
    {
        return rotate;
    }

    public float[] getScale()
    {
        return scale;
    }

    public String getNameString()
    {
        return nameString;
    }
}
