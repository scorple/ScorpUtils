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
    public static final int JOINT_SIZE = 48;

    private int   name;
    private int   parent;
    private float translate[];
    private float rotate[];
    private float scale[];

    public Joint()
    {
        translate = new float[3];
        rotate = new float[4];
        scale = new float[3];
    }

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfs_joints() + index * JOINT_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, JOINT_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        name = bb.getInt();
        Logger.logD("" + name);

        parent = bb.getInt();
        Logger.logD("" + parent);

        StringBuilder sbt = new StringBuilder();
        sbt.append("jt");
        for (int i = 0; i < 3; ++i)
        {
            translate[i] = bb.getFloat();
            sbt.append(" ").append(translate[i]);
        }
        Logger.logD(sbt.toString());

        StringBuilder sbr = new StringBuilder();
        sbt.append("jr");
        for (int i = 0; i < 4; ++i)
        {
            rotate[i] = bb.getFloat();
            sbr.append(" ").append(rotate[i]);
        }
        Logger.logD(sbr.toString());

        StringBuilder sbs = new StringBuilder();
        sbt.append("js");
        for (int i = 0; i < 3; ++i)
        {
            scale[i] = bb.getFloat();
            sbs.append(" ").append(scale[i]);
        }
        Logger.logD(sbs.toString());
    }
}
