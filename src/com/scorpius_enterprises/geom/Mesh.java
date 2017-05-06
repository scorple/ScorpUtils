package com.scorpius_enterprises.geom;

/**
 * {@link Mesh com.scorpius_enterprises.geom.Mesh}
 *
 * @author Scorple
 * @since 2017-05-04
 */
public class Mesh
{
    private Triangle[] triangles;

    public Mesh()
    {

    }

    public Mesh(final Triangle[] triangles)
    {
        this.triangles = triangles;
    }

    public Triangle[] getTriangles()
    {
        return triangles;
    }

    public void setTriangles(final Triangle[] triangles)
    {
        this.triangles = triangles;
    }
}
