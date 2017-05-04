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

    public float getLength()
    {
        return getLength(this);
    }

    public float getDistance(final Vector3f other)
    {
        return getDistance(this, other);
    }

    public Vector3f getDelta(final Vector3f other)
    {
        Vector3f res = new Vector3f();

        res.setX(other.getX() - x);
        res.setY(other.getY() - y);
        res.setZ(other.getZ() - z);

        return res;
    }

    public static float getLength(final Vector3f vector)
    {
        float x = vector.getX();
        float y = vector.getY();
        float z = vector.getZ();

        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public static float getDistance(final Vector3f vec1, final Vector3f vec2)
    {
        return getLength(getDelta(vec1, vec2));
    }

    public static Vector3f getDelta(final Vector3f vec1, final Vector3f vec2)
    {
        Vector3f res = new Vector3f();

        res.setX(vec2.getX() - vec1.getX());
        res.setY(vec2.getY() - vec1.getY());
        res.setZ(vec2.getZ() - vec1.getZ());

        return res;
    }
}
