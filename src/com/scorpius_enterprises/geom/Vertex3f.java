package com.scorpius_enterprises.geom;

/**
 * {@link Vertex3f com.scorpius_enterprises.geom.Vertex3f}
 *
 * @author Scorple
 * @since 2017-05-04
 */
public class Vertex3f
{
    private Vector3f position;

    public Vertex3f()
    {
        position = new Vector3f();
    }

    public Vertex3f(final float x,
                    final float y,
                    final float z)
    {
        position = new Vector3f(x,
                                y,
                                z);
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public float getX()
    {
        return position.getX();
    }

    public void setX(final float x)
    {
        position.setX(x);
    }

    public float getY()
    {
        return position.getY();
    }

    public void setY(final float y)
    {
        position.setY(y);
    }

    public float getZ()
    {
        return position.getZ();
    }

    public void setZ(final float z)
    {
        position.setZ(z);
    }
}
