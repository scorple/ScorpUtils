package com.scorpius_enterprises.geom;

/**
 * {@link Vector3f com.scorpius_enterprises.geom.Vector3f}
 *
 * @author Scorple
 * @since 2017-05-04
 */
public class Vector3f
{
    private float x;
    private float y;
    private float z;

    public Vector3f()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3f(final float x, final float y, final float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX()
    {
        return x;
    }

    public void setX(final float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(final float y)
    {
        this.y = y;
    }

    public float getZ()
    {
        return z;
    }

    public void setZ(final float z)
    {
        this.z = z;
    }
}
