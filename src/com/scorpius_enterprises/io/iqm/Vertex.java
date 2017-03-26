package com.scorpius_enterprises.io.iqm;

/**
 * {@link Vertex com.scorpius_enterprises.io.iqm.Vertex}
 *
 * @author Scorple
 * @since 2017-03-25
 */
public class Vertex
{
    private float[] position;
    private float[] texcoord;
    private float[] normal;
    private float[] tangent;
    private byte[]  blendindices;
    private byte[]  blendweights;
    private byte[]  color;

    public Vertex()
    {
        position = new float[3];
        texcoord = new float[2];
        normal = new float[3];
        tangent = new float[4];
        blendindices = new byte[4];
        blendweights = new byte[4];
        color = new byte[4];
    }

    public void setPosition(final float[] position)
    {
        System.arraycopy(position, 0, this.position, 0, this.position.length);
    }

    public void setTexcoord(final float[] texcoord)
    {
        System.arraycopy(texcoord, 0, this.texcoord, 0, this.texcoord.length);
    }

    public void setNormal(final float[] normal)
    {
        System.arraycopy(normal, 0, this.normal, 0, this.normal.length);
    }

    public void setTangent(final float[] tangent)
    {
        System.arraycopy(tangent, 0, this.tangent, 0, this.tangent.length);
    }

    public void setBlendindices(final byte[] blendindices)
    {
        System.arraycopy(blendindices, 0, this.blendindices, 0, this.blendindices.length);
    }

    public void setBlendweights(final byte[] blendweights)
    {
        System.arraycopy(blendweights, 0, this.blendweights, 0, this.blendweights.length);
    }

    public void setColor(final byte[] color)
    {
        System.arraycopy(color, 0, this.color, 0, this.color.length);
    }

    public float[] getPosition()
    {
        return position;
    }

    public float[] getTexcoord()
    {
        return texcoord;
    }

    public float[] getNormal()
    {
        return normal;
    }

    public float[] getTangent()
    {
        return tangent;
    }

    public byte[] getBlendindices()
    {
        return blendindices;
    }

    public byte[] getBlendweights()
    {
        return blendweights;
    }

    public byte[] getColor()
    {
        return color;
    }
}
