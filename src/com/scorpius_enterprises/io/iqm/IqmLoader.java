package com.scorpius_enterprises.io.iqm;

import java.io.InputStream;

/**
 * {@link IqmLoader com.scorpius_enterprises.io.iqm.IqmLoader}
 *
 * @author Scorple
 * @since 2017-03-24
 */
public class IqmLoader
{
    public static void loadIBO(final String fileName,
                               final int[] numIndices,
                               final float[][] posCoordArray,
                               final float[][] texCoordArray,
                               final float[][] normalVecCompArray,
                               final int[][] vertexIndexArray)
    {
        InputStream is = IqmLoader.class.getClass().getResourceAsStream(fileName);
    }
}
