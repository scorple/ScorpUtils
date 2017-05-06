package com.scorpius_enterprises.geom;

/**
 * {@link Triangle com.scorpius_enterprises.geom.Triangle}
 *
 * @author Scorple
 * @since 2017-05-05
 */
public class Triangle
{
    private Vertex3f a;
    private Vertex3f b;
    private Vertex3f c;

    public Triangle()
    {
        a = new Vertex3f();
        b = new Vertex3f();
        c = new Vertex3f();
    }

    public Triangle(final Vertex3f a, final Vertex3f b, final Vertex3f c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Triangle(final Vertex3f[] verts)
    {
        a = (0 < verts.length) ? verts[0] : new Vertex3f();
        b = (1 < verts.length) ? verts[1] : new Vertex3f();
        c = (2 < verts.length) ? verts[2] : new Vertex3f();
    }

    public Vertex3f getA()
    {
        return a;
    }

    public void setA(final Vertex3f a)
    {
        this.a = a;
    }

    public Vertex3f getB()
    {
        return b;
    }

    public void setB(final Vertex3f b)
    {
        this.b = b;
    }

    public Vertex3f getC()
    {
        return c;
    }

    public void setC(final Vertex3f c)
    {
        this.c = c;
    }

    public Vertex3f[] getVerts()
    {
        final Vertex3f[] res = new Vertex3f[3];

        res[0] = a;
        res[1] = b;
        res[2] = c;

        return res;
    }

    public Vector3f getNormal()
    {
        Vector3f a = Vector3f.getDelta(this.a.getPosition(), this.b.getPosition());
        Vector3f b = Vector3f.getDelta(this.a.getPosition(), this.c.getPosition());

        return Vector3f.crossProduct(a, b);
    }
}
