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
    private int first_vertex;
    private int num_vertexes;
    private int first_triangle;
    private int num_triangles;

    public Mesh()
    {

    }

    public void load(final Header header, final byte[] buf, final int index)
    {
        int offset = header.getOfs_meshes() + index * MESH_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf, offset, MESH_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        name = bb.getInt();
        Logger.logD("" + name);

        material = bb.getInt();
        Logger.logD("" + material);

        first_vertex = bb.getInt();
        Logger.logD("" + first_vertex);

        num_vertexes = bb.getInt();
        Logger.logD("" + num_vertexes);

        first_triangle = bb.getInt();
        Logger.logD("" + first_triangle);

        num_triangles = bb.getInt();
        Logger.logD("" + num_triangles);
    }

    public int getName()
    {
        return name;
    }

    public int getMaterial()
    {
        return material;
    }

    public int getFirst_vertex()
    {
        return first_vertex;
    }

    public int getNum_vertexes()
    {
        return num_vertexes;
    }

    public int getFirst_triangle()
    {
        return first_triangle;
    }

    public int getNum_triangles()
    {
        return num_triangles;
    }
}
