package com.scorpius_enterprises.geom;

/**
 * {@link Mesh com.scorpius_enterprises.geom.Mesh}
 *
 * @author Scorple
 * @since 2017-05-04
 */
public class Mesh
{
    private Vertex3f[] vertices;

    public Mesh()
    {

    }

    public Mesh(final Vertex3f[] vertices)
    {
        this.vertices = vertices;
    }

    public Vertex3f[] getVertices()
    {
        return vertices;
    }

    public void setVertices(final Vertex3f[] vertices)
    {
        this.vertices = vertices;
    }
}
