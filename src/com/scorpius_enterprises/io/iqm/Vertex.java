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
    private float[] texCoord;
    private float[] normal;
    private float[] tangent;
    private byte[]  blendIndices;
    private byte[]  blendWeights;
    private byte[]  color;

    public Vertex()
    {
        position = new float[VertexArray.NUM_POSITION_COMPONENTS];
        texCoord = new float[VertexArray.NUM_TEXCOORD_COMPONENTS];
        normal = new float[VertexArray.NUM_NORMAL_COMPONENTS];
        tangent = new float[VertexArray.NUM_TANGENT_COMPONENTS];
        blendIndices = new byte[VertexArray.NUM_BLENDINDICES_COMPONENTS];
        blendWeights = new byte[VertexArray.NUM_BLENDWEIGHTS_COMPONENTS];
        color = new byte[VertexArray.NUM_COLOR_COMPONENTS];
    }

    public void setPosition(final float[] position)
    {
        System.arraycopy(position, 0, this.position, 0, this.position.length);
    }

    public void setTexCoord(final float[] texCoord)
    {
        System.arraycopy(texCoord, 0, this.texCoord, 0, this.texCoord.length);
    }

    public void setNormal(final float[] normal)
    {
        System.arraycopy(normal, 0, this.normal, 0, this.normal.length);
    }

    public void setTangent(final float[] tangent)
    {
        System.arraycopy(tangent, 0, this.tangent, 0, this.tangent.length);
    }

    public void setBlendIndices(final byte[] blendIndices)
    {
        System.arraycopy(blendIndices, 0, this.blendIndices, 0, this.blendIndices.length);
    }

    public void setBlendWeights(final byte[] blendWeights)
    {
        System.arraycopy(blendWeights, 0, this.blendWeights, 0, this.blendWeights.length);
    }

    public void setColor(final byte[] color)
    {
        System.arraycopy(color, 0, this.color, 0, this.color.length);
    }

    public float[] getPosition()
    {
        return position;
    }

    public float[] getTexCoord()
    {
        return texCoord;
    }

    public float[] getNormal()
    {
        return normal;
    }

    public float[] getTangent()
    {
        return tangent;
    }

    public byte[] getBlendIndices()
    {
        return blendIndices;
    }

    public byte[] getBlendWeights()
    {
        return blendWeights;
    }

    public byte[] getColor()
    {
        return color;
    }
}
