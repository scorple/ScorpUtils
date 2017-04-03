package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by rickm on 3/25/2017.
 */
public class Mesh
{
    public static final int MESH_SIZE         = 24;
    public static final int UNSIGNED_INT_SIZE = 4;

    private int name; // unique name for the mesh, if desired
    private int material; // set to a name of a non-unique material or texture
    private int firstVertex;
    private int numVertices;
    private int firstTriangle;
    private int numTriangles;

    private String nameString;
    private String materialString;

    public Mesh()
    {

    }

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfsMeshes() + index * MESH_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, MESH_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        name = bb.getInt();
        Logger.logD("" + name);

        material = bb.getInt();
        Logger.logD("" + material);

        firstVertex = bb.getInt();
        Logger.logD("" + firstVertex);

        numVertices = bb.getInt();
        Logger.logD("" + numVertices);

        firstTriangle = bb.getInt();
        Logger.logD("" + firstTriangle);

        numTriangles = bb.getInt();
        Logger.logD("" + numTriangles);

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

        bb.position(header.getOfsText() + material);

        sb = new StringBuilder();
        while ((b[0] = bb.get()) != '\0')
        {
            s = new String(b);
            sb.append(s);
        }

        materialString = sb.toString();
        Logger.logD(materialString);
    }

    public int getName()
    {
        return name;
    }

    public int getMaterial()
    {
        return material;
    }

    public int getFirstVertex()
    {
        return firstVertex;
    }

    public int getNumVertices()
    {
        return numVertices;
    }

    public int getFirstTriangle()
    {
        return firstTriangle;
    }

    public int getNumTriangles()
    {
        return numTriangles;
    }

    public String getNameString()
    {
        return nameString;
    }

    public String getMaterialString()
    {
        return materialString;
    }
}
