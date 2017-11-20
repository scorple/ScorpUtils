package com.scorpius_enterprises.geom;

public class Model
{
    private int     numIndices;
    private float[] posCoordArray;
    private float[] texCoordArray;
    private float[] normalVecCompArray;
    private int[]   vertexIndexArray;

    public Model(final int numIndices,
                 final float[] posCoordArray,
                 final float[] texCoordArray,
                 final float[] normalVecCompArray,
                 final int[] vertexIndexArray)
    {
        this.numIndices = numIndices;
        this.posCoordArray = posCoordArray;
        this.texCoordArray = texCoordArray;
        this.normalVecCompArray = normalVecCompArray;
        this.vertexIndexArray = vertexIndexArray;
    }

    public int getNumIndices()
    {
        return numIndices;
    }

    public float[] getPosCoordArray()
    {
        return posCoordArray;
    }

    public float[] getTexCoordArray()
    {
        return texCoordArray;
    }

    public float[] getNormalVecCompArray()
    {
        return normalVecCompArray;
    }

    public int[] getVertexIndexArray()
    {
        return vertexIndexArray;
    }
}
