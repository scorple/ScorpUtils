package com.scorpius_enterprises.io.iqm;

import java.io.InputStream;
import java.util.ArrayList;

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
        Header          header = new Header();
        ArrayList<Mesh> meshes = new ArrayList<>();

        InputStream is = IqmLoader.class.getClass().getResourceAsStream(fileName);

        header.load(is);

        for (int i = 0; i < header.getNum_meshes(); ++i)
        {
            Mesh mesh = new Mesh();

            mesh.load(is);

            meshes.add(mesh);
        }
    }
}
