package com.scorpius_enterprises.io.iqm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * {@link Triangle com.scorpius_enterprises.io.iqm.Triangle}
 *
 * @author Scorple
 * @since 2017-03-25
 */
public class Triangle
{
    public static final int TRIANGLE_SIZE = 12;

    private int[] vertices;

    public Triangle()
    {
        vertices = new int[3];
    }

    public void load(final Header header,
                     final byte[] buf,
                     final int index)
    {
        int offset = header.getOfsTriangles() + index * TRIANGLE_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf,
                                        offset,
                                        TRIANGLE_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0;
             i < vertices.length;
             ++i)
        {
            vertices[i] = bb.getInt();
        }
    }

    public int[] getVertices()
    {
        return vertices;
    }
}
