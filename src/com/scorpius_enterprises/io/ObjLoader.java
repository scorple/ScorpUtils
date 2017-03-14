package com.scorpius_enterprises.io;

import com.scorpius_enterprises.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * {@link ObjLoader com.scorpius_enterprises.io.ObjLoader}
 *
 * @author Scorple
 * @since 2017-03-12
 */
public class ObjLoader
{
    public static final int POSITION_SIZE         = 3;
    public static final int TEXTURE_POSITION_SIZE = 2;
    public static final int NORMAL_VECTOR_SIZE    = 3;

    public static final int FACE_INDICES = 3;

    /**
     * Generate an OpenGL compatible Index Buffer Object (IBO) from a given
     * .obj file. Accepts the name of the file as a String and 5 one element
     * arrays used to return the int number of indices in the IBO as well as
     * the IBO's vertex position coordinate, texture coordinate, normal vector
     * component, and index arrays. .obj Model must consist entirely of tris
     * (3-gons) and may or may not include texture or normal information. If
     * texture or normal information is missing, the corresponding address with
     * be set to null.
     *
     * @param fileName           String: The path to the .obj file to generate
     *                           an IBO from.
     * @param numIndices         int[]: An array of int which should have
     *                           exactly one address to put the number of
     *                           indices in the generated IBO.
     * @param posCoordArray      float[][]: An array of float[] which should
     *                           have exactly one address for the vertex
     *                           position coordinates array of the generated
     *                           IBO.
     * @param texCoordArray      float[][]: An array of float[] which should
     *                           have exactly one address for the vertex
     *                           texture coordinates array of the generated
     *                           IBO.
     * @param normalVecCompArray float[][]: An array of float[] which should
     *                           have exactly one address for the vertex normal
     *                           vector components array of the generated IBO.
     * @param vertexIndexArray   int[][]: An array of int[] which should have
     *                           exactly one address for the vertex index array
     *                           of the generated IBO.
     */
    public static void loadIBO(final String fileName,
                               final int[] numIndices,
                               final float[][] posCoordArray,
                               final float[][] texCoordArray,
                               final float[][] normalVecCompArray,
                               final int[][] vertexIndexArray)
    {
        ArrayList<Float>   posCoords      = new ArrayList<>();
        ArrayList<Float>   texCoords      = new ArrayList<>();
        ArrayList<Float>   normalVecComps = new ArrayList<>();
        ArrayList<Integer> indices        = new ArrayList<>();

        Float[] texCoordsInVertexOrder      = null;
        Float[] normalVecCompsInVertexOrder = null;

        HashMap<Integer, float[]> texCoordsByPosIndex =
            new HashMap<>();
        HashMap<Integer, float[]> normalVecCompsByPosIndex =
            new HashMap<>();

        String   objText  = TextLoader.loadTextFile(fileName);
        String[] splitObj = objText.split("\n");

        Arrays.stream(splitObj).parallel().forEachOrdered(line ->
                                                          {
                                                              String[] splitLine = line.split(" ");

                                                              switch (splitLine[0])
                                                              {
                                                                  case "v":
                                                                      for (int i = 1; i < POSITION_SIZE + 1; ++i)
                                                                      {
                                                                          posCoords.add(Float.parseFloat(splitLine[i]));
                                                                      }
                                                                      break;
                                                                  case "vt":
                                                                      texCoords.add(Float.parseFloat(splitLine[1]));
                                                                      texCoords.add(
                                                                          1.0f - Float.parseFloat(splitLine[2]));
                                                                      break;
                                                                  case "vn":
                                                                      for (int i = 1; i < NORMAL_VECTOR_SIZE + 1; ++i)
                                                                      {
                                                                          normalVecComps.add(Float.parseFloat(splitLine[i]));
                                                                      }
                                                                      break;
                                                                  case "f":
                                                                      int[] vi = new int[3];
                                                                      int[] ti = new int[3];
                                                                      int[] ni = new int[3];

                                                                      IntStream.range(1, 4).parallel().forEach(i ->
                                                                                                               {
                                                                                                                   String
                                                                                                                       triad
                                                                                                                       [
                                                                                                                       ] =
                                                                                                                       splitLine[i]
                                                                                                                           .split(
                                                                                                                               "/");
                                                                                                                   vi[3 -
                                                                                                                      i] =
                                                                                                                       Integer
                                                                                                                           .parseInt(
                                                                                                                               triad[0]) -
                                                                                                                       1;
                                                                                                                   if (!triad[1]
                                                                                                                       .equals(
                                                                                                                           ""))
                                                                                                                   {
                                                                                                                       ti[3 -
                                                                                                                          i] =
                                                                                                                           Integer
                                                                                                                               .parseInt(
                                                                                                                                   triad[1]) -
                                                                                                                           1;
                                                                                                                   }
                                                                                                                   ni[3 -
                                                                                                                      i] =
                                                                                                                       Integer
                                                                                                                           .parseInt(
                                                                                                                               triad[2]) -
                                                                                                                       1;
                                                                                                               });

                                                                      IntStream.range(0, 3)
                                                                               .parallel()
                                                                               .forEachOrdered(i ->
                                                                                               {
                                                                                                   if (indices
                                                                                                       .contains(
                                                                                                           vi[i]))
                                                                                                   {
                                                                                                       int
                                                                                                           nextIndexTripleStartIndex =
                                                                                                           vi[i] *
                                                                                                           POSITION_SIZE;

                                                                                                       IntStream.range(0,
                                                                                                                       3)
                                                                                                                .parallel()
                                                                                                                .forEachOrdered(
                                                                                                                    j -> posCoords
                                                                                                                        .add(
                                                                                                                            posCoords
                                                                                                                                .get(
                                                                                                                                    nextIndexTripleStartIndex +
                                                                                                                                    j)));

                                                                                                       vi[i] =
                                                                                                           (posCoords
                                                                                                                .size() /
                                                                                                            3) -
                                                                                                           1;
                                                                                                   }

                                                                                                   indices.add(
                                                                                                       vi[i]);

                                                                                                   if (texCoords
                                                                                                           .size() >
                                                                                                       0)
                                                                                                   {
                                                                                                       texCoordsByPosIndex
                                                                                                           .put(
                                                                                                               vi[i],
                                                                                                               new float[]{texCoords.get(
                                                                                                                   ti[i] *
                                                                                                                   TEXTURE_POSITION_SIZE), texCoords.get(
                                                                                                                   ti[i] *
                                                                                                                   TEXTURE_POSITION_SIZE +
                                                                                                                   1)});
                                                                                                   }

                                                                                                   if (normalVecComps
                                                                                                           .size() >
                                                                                                       0)
                                                                                                   {
                                                                                                       normalVecCompsByPosIndex
                                                                                                           .put(
                                                                                                               vi[i],
                                                                                                               new float[]{normalVecComps.get(
                                                                                                                   ni[i] *
                                                                                                                   NORMAL_VECTOR_SIZE), normalVecComps.get(
                                                                                                                   ni[i] *
                                                                                                                   NORMAL_VECTOR_SIZE +
                                                                                                                   1), normalVecComps.get(
                                                                                                                   ni[i] *
                                                                                                                   NORMAL_VECTOR_SIZE +
                                                                                                                   2)});
                                                                                                   }
                                                                                               });

                                                                      break;
                                                              }
                                                          });

        if (texCoords.size() > 0)
        {
            texCoordsInVertexOrder = new Float[texCoordsByPosIndex.size() * TEXTURE_POSITION_SIZE];
        }

        if (normalVecComps.size() > 0)
        {
            normalVecCompsInVertexOrder = new Float[normalVecCompsByPosIndex.size() * NORMAL_VECTOR_SIZE];
        }

        try
        {
            if (texCoordsInVertexOrder != null)
            {
                Float[] finalTexCoordsInVertexOrder = texCoordsInVertexOrder;

                texCoordsByPosIndex.keySet()
                                   .stream()
                                   .parallel()
                                   .forEach(vertexIndex -> IntStream.range(0, TEXTURE_POSITION_SIZE)
                                                                    .parallel()
                                                                    .forEach(
                                                                        texCoordIndex ->
                                                                            finalTexCoordsInVertexOrder[
                                                                                vertexIndex *
                                                                                TEXTURE_POSITION_SIZE +
                                                                                texCoordIndex] =
                                                                                texCoordsByPosIndex
                                                                                    .get(
                                                                                        vertexIndex)[texCoordIndex]));
            }

            if (normalVecCompsInVertexOrder != null)
            {
                Float[] finalNormalsInVertexOrder = normalVecCompsInVertexOrder;

                normalVecCompsByPosIndex.keySet()
                                        .stream()
                                        .parallel()
                                        .forEach(vertexIndex -> IntStream.range(0, NORMAL_VECTOR_SIZE)
                                                                         .parallel()
                                                                         .forEach(normalCoordIndex ->
                                                                                      finalNormalsInVertexOrder[
                                                                                          vertexIndex *
                                                                                          NORMAL_VECTOR_SIZE +
                                                                                          normalCoordIndex] =
                                                                                          normalVecCompsByPosIndex.get(
                                                                                              vertexIndex)[normalCoordIndex]));
            }
        }
        catch (Exception X)
        {
            Logger.logD("encountered bad index for model <" + fileName + ">, check model format");
            X.printStackTrace();
            return;
        }

        numIndices[0] = indices.size();

        if (posCoordArray != null && posCoords.size() > 0)
        {
            posCoordArray[0] = new float[posCoords.size()];
            IntStream.range(0, posCoordArray[0].length).parallel().forEach(i -> posCoordArray[0][i] = posCoords.get(i));
        }
        if (texCoordArray != null)
        {
            if (texCoordsInVertexOrder != null)
            {
                texCoordArray[0] = new float[texCoordsInVertexOrder.length];
                Float[] finalTexCoordsInVertexOrder1 = texCoordsInVertexOrder;
                IntStream.range(0, texCoordArray[0].length)
                         .parallel()
                         .forEach(i -> texCoordArray[0][i] = finalTexCoordsInVertexOrder1[i]);
            }
            else
            {
                texCoordArray[0] = null;
            }
        }
        if (normalVecCompArray != null)
        {
            if (normalVecCompsInVertexOrder != null)
            {
                normalVecCompArray[0] = new float[normalVecCompsInVertexOrder.length];
                Float[] finalNormalsInVertexOrder1 = normalVecCompsInVertexOrder;
                IntStream.range(0, normalVecCompArray[0].length)
                         .parallel()
                         .forEach(i -> normalVecCompArray[0][i] = finalNormalsInVertexOrder1[i]);
            }
            else
            {
                normalVecCompArray[0] = null;
            }
        }
        if (vertexIndexArray != null && indices.size() > 0)
        {
            vertexIndexArray[0] = new int[indices.size()];
            IntStream.range(0, vertexIndexArray[0].length)
                     .parallel()
                     .forEach(i -> vertexIndexArray[0][i] = indices.get(i));
        }
    }
}
