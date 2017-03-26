package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

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
        LinkedList<Float>   posCoordList      = new LinkedList<>();
        LinkedList<Float>   texCoordList      = new LinkedList<>();
        LinkedList<Float>   normalVecCompList = new LinkedList<>();
        LinkedList<Integer> vertexIndexList   = new LinkedList<>();

        float[] iPosCoordArray;
        int     posCoordArrayIndex      = 0;
        float[] iTexCoordArray;
        int     texCoordArrayIndex      = 0;
        float[] iNormalVecCompArray;
        int     normalVecCompArrayIndex = 0;
        int[]   iVertexIndexArray;
        int     vertexIndexArrayIndex   = 0;

        Header        header = new Header();
        Vertex[]      vertices;
        VertexArray[] vertexArrays;
        Triangle[]    triangles;
        Mesh[]        meshes;

        InputStream is = IqmLoader.class.getClass().getResourceAsStream(fileName);

        header.load(is);

        int fileSize = header.getFilesize();

        byte[] buf = new byte[fileSize];

        byte[] headerBuf = header.getBuf();

        System.arraycopy(headerBuf, 0, buf, 0, headerBuf.length);

        try
        {
            is.read(buf, Header.HEADER_SIZE, fileSize - Header.HEADER_SIZE);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        vertices = new Vertex[header.getNum_vertexes()];

        iPosCoordArray = new float[header.getNum_vertexes() * VertexArray.NUM_POSITION_COMPONENTS];
        iTexCoordArray = new float[header.getNum_vertexes() * VertexArray.NUM_TEXCOORD_COMPONENTS];
        iNormalVecCompArray = new float[header.getNum_vertexes() * VertexArray.NUM_NORMAL_COMPONENTS];
        iVertexIndexArray = new int[header.getNum_triangles() * 3];

        vertexArrays = new VertexArray[header.getNum_vertexarrays()];

        for (int i = 0; i < header.getNum_vertexarrays(); ++i)
        {
            VertexArray vertexArray = new VertexArray();

            vertexArray.load(header, buf, i, vertices.length);

            vertexArrays[i] = vertexArray;
        }

        for (int i = 0; i < header.getNum_vertexes(); ++i)
        {
            vertices[i] = new Vertex();
            Vertex vertex = vertices[i];

            for (int j = 0; j < header.getNum_vertexarrays(); ++j)
            {
                VertexArray vertexArray = vertexArrays[j];
                byte[]      compBuf;
                ByteBuffer  bb;

                switch (vertexArray.getType())
                {
                    case VertexArray.TYPE_IQM_POSITION:
                        float[] position = new float[VertexArray.NUM_POSITION_COMPONENTS];
                        compBuf = new byte[VertexArray.NUM_POSITION_COMPONENTS * VertexArray.FLOAT_SIZE];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        bb = ByteBuffer.wrap(compBuf);
                        bb.order(ByteOrder.LITTLE_ENDIAN);
                        for (int k = 0; k < VertexArray.NUM_POSITION_COMPONENTS; ++k)
                        {
                            position[k] = bb.getFloat();
                            iPosCoordArray[posCoordArrayIndex++] = position[k];
                        }
                        vertex.setPosition(position);
                        break;
                    case VertexArray.TYPE_IQM_TEXCOORD:
                        float[] texcoord = new float[VertexArray.NUM_TEXCOORD_COMPONENTS];
                        compBuf = new byte[VertexArray.NUM_TEXCOORD_COMPONENTS * VertexArray.FLOAT_SIZE];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        bb = ByteBuffer.wrap(compBuf);
                        bb.order(ByteOrder.LITTLE_ENDIAN);
                        for (int k = 0; k < VertexArray.NUM_TEXCOORD_COMPONENTS; ++k)
                        {
                            texcoord[k] = bb.getFloat();
                            iTexCoordArray[texCoordArrayIndex++] = texcoord[k];
                        }
                        vertex.setTexcoord(texcoord);
                        break;
                    case VertexArray.TYPE_IQM_NORMAL:
                        float[] normal = new float[VertexArray.NUM_NORMAL_COMPONENTS];
                        compBuf = new byte[VertexArray.NUM_NORMAL_COMPONENTS * VertexArray.FLOAT_SIZE];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        bb = ByteBuffer.wrap(compBuf);
                        bb.order(ByteOrder.LITTLE_ENDIAN);
                        for (int k = 0; k < VertexArray.NUM_NORMAL_COMPONENTS; ++k)
                        {
                            normal[k] = bb.getFloat();
                            iNormalVecCompArray[normalVecCompArrayIndex++] = normal[k];
                        }
                        vertex.setNormal(normal);
                        break;
                    case VertexArray.TYPE_IQM_TANGENT:
                        float[] tangent = new float[VertexArray.NUM_TANGENT_COMPONENTS];
                        compBuf = new byte[VertexArray.NUM_TANGENT_COMPONENTS * VertexArray.FLOAT_SIZE];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        bb = ByteBuffer.wrap(compBuf);
                        bb.order(ByteOrder.LITTLE_ENDIAN);
                        for (int k = 0; k < VertexArray.NUM_TANGENT_COMPONENTS; ++k)
                        {
                            tangent[k] = bb.getFloat();
                        }
                        vertex.setTangent(tangent);
                        break;
                    case VertexArray.TYPE_IQM_BLENDINDEXES:
                        compBuf = new byte[VertexArray.NUM_BLENDINDEXES_COMPONENTS];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        vertex.setBlendindices(compBuf);
                        break;
                    case VertexArray.TYPE_IQM_BLENDWEIGHTS:
                        compBuf = new byte[VertexArray.NUM_BLENDWEIGHTS_COMPONENTS];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        vertex.setBlendweights(compBuf);
                        break;
                    case VertexArray.TYPE_IQM_COLOR:
                        compBuf = new byte[VertexArray.NUM_COLOR_COMPONENTS];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        vertex.setColor(compBuf);
                        break;
                }
            }

            StringBuilder sbp = new StringBuilder();
            sbp.append("vp");
            for (float j : vertex.getPosition())
            {
                sbp.append(" ").append(j);
            }
            Logger.logD(sbp.toString());

            StringBuilder sbt = new StringBuilder();
            sbt.append("vt");
            for (float j : vertex.getTexcoord())
            {
                sbt.append(" ").append(j);
            }
            Logger.logD(sbt.toString());

            StringBuilder sbn = new StringBuilder();
            sbn.append("vn");
            for (float j : vertex.getNormal())
            {
                sbn.append(" ").append(j);
            }
            Logger.logD(sbn.toString());
        }

        triangles = new Triangle[header.getNum_triangles()];

        for (int i = 0; i < header.getNum_triangles(); ++i)
        {
            Triangle triangle = new Triangle();

            triangle.load(header, buf, i);

            triangles[i] = triangle;

            StringBuilder sb = new StringBuilder();
            sb.append("fm");
            for (int j : triangle.getVertexes())
            {
                sb.append(" ").append(j);
                iVertexIndexArray[vertexIndexArrayIndex++] = j;
            }
            Logger.logD(sb.toString());
        }

        meshes = new Mesh[header.getNum_meshes()];

        for (int i = 0; i < header.getNum_meshes(); ++i)
        {
            Mesh mesh = new Mesh();

            mesh.load(header, buf, i);

            meshes[i] = mesh;
        }

        if (numIndices != null)
        {
            numIndices[0] = header.getNum_vertexes();
        }
        if (posCoordArray != null)
        {
            posCoordArray[0] = iPosCoordArray;
        }
        if (texCoordArray != null)
        {
            texCoordArray[0] = iTexCoordArray;
        }
        if (normalVecCompArray != null)
        {
            normalVecCompArray[0] = iNormalVecCompArray;
        }
        if (vertexIndexArray != null)
        {
            vertexIndexArray[0] = iVertexIndexArray;
        }
    }
}
