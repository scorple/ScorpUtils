package com.scorpius_enterprises.geom;

/**
 * {@link BoundingBox com.scorpius_enterprises.geom.BoundingBox}
 *
 * @author Scorple
 * @since 2017-05-04
 */
public class BoundingBox
{
    private Vertex3f b1;
    private Vertex3f b2;

    public BoundingBox(Vertex3f b1, Vertex3f b2)
    {
        this.b1 = b1;
        this.b2 = b2;
    }

    public BoundingBox(final Mesh mesh)
    {
        b1 = new Vertex3f();
        b2 = new Vertex3f();

        Vertex3f[] vertices = mesh.getVertices();

        for (Vertex3f vertex : vertices)
        {
            b1.setX(Math.min(b1.getX(), vertex.getX()));
            b1.setX(Math.min(b1.getY(), vertex.getY()));
            b1.setX(Math.min(b1.getZ(), vertex.getZ()));

            b2.setX(Math.max(b2.getX(), vertex.getX()));
            b2.setX(Math.max(b2.getY(), vertex.getY()));
            b2.setX(Math.max(b2.getZ(), vertex.getZ()));
        }
    }

    public Vertex3f getB1()
    {
        return b1;
    }

    public void setB1(final Vertex3f b1)
    {
        this.b1 = b1;
    }

    public Vertex3f getB2()
    {
        return b2;
    }

    public void setB2(final Vertex3f b2)
    {
        this.b2 = b2;
    }
}
