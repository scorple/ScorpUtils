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

    public BoundingBox()
    {
        b1 = new Vertex3f();
        b2 = new Vertex3f();
    }

    public BoundingBox(Vertex3f b1,
                       Vertex3f b2)
    {
        this.b1 = b1;
        this.b2 = b2;
    }

    public BoundingBox(final Mesh mesh)
    {
        b1 = new Vertex3f();
        b2 = new Vertex3f();

        Triangle[] triangles = mesh.getTriangles();

        for (Triangle triangle : triangles)
        {
            for (Vertex3f vertex : triangle.getVerts())
            {
                b1.setX(Math.min(b1.getX(),
                                 vertex.getX()));
                b1.setY(Math.min(b1.getY(),
                                 vertex.getY()));
                b1.setZ(Math.min(b1.getZ(),
                                 vertex.getZ()));

                b2.setX(Math.max(b2.getX(),
                                 vertex.getX()));
                b2.setY(Math.max(b2.getY(),
                                 vertex.getY()));
                b2.setZ(Math.max(b2.getZ(),
                                 vertex.getZ()));
            }
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

    public Vertex3f getBottomCenter()
    {
        float x = b1.getX() + ((b2.getX() - b1.getX()) / 2);
        float y = b1.getY();
        float z = b1.getZ() + ((b2.getZ() - b1.getZ()) / 2);

        return new Vertex3f(x,
                            y,
                            z);
    }
}
