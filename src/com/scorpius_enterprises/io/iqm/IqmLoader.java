package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
        Joint[]       joints;
        Pose[]        poses;
        short[]       frames;
        Anim[]        anims;

        InputStream is = IqmLoader.class.getClass().getResourceAsStream(fileName);

        header.load(is);

        int fileSize = header.getFileSize();

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

        vertices = new Vertex[header.getNumVertices()];

        iPosCoordArray = new float[header.getNumVertices() * VertexArray.NUM_POSITION_COMPONENTS];
        iTexCoordArray = new float[header.getNumVertices() * VertexArray.NUM_TEXCOORD_COMPONENTS];
        iNormalVecCompArray = new float[header.getNumVertices() * VertexArray.NUM_NORMAL_COMPONENTS];
        iVertexIndexArray = new int[header.getNumTriangles() * 3];

        vertexArrays = new VertexArray[header.getNumVertexArrays()];

        for (int i = 0; i < header.getNumVertexArrays(); ++i)
        {
            VertexArray vertexArray = new VertexArray();

            vertexArray.load(header, buf, i, vertices.length);

            vertexArrays[i] = vertexArray;
        }

        for (int i = 0; i < header.getNumVertices(); ++i)
        {
            vertices[i] = new Vertex();
            Vertex vertex = vertices[i];

            for (int j = 0; j < header.getNumVertexArrays(); ++j)
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
                        }
                        vertex.setPosition(position);

                        iPosCoordArray[posCoordArrayIndex++] = position[0];
                        iPosCoordArray[posCoordArrayIndex++] = position[2];
                        iPosCoordArray[posCoordArrayIndex++] = -position[1];

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
                        vertex.setTexCoord(texcoord);
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
                        }
                        vertex.setNormal(normal);

                        iNormalVecCompArray[normalVecCompArrayIndex++] = normal[0];
                        iNormalVecCompArray[normalVecCompArrayIndex++] = normal[2];
                        iNormalVecCompArray[normalVecCompArrayIndex++] = -normal[1];

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
                        compBuf = new byte[VertexArray.NUM_BLENDINDICES_COMPONENTS];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        vertex.setBlendIndices(compBuf);
                        break;
                    case VertexArray.TYPE_IQM_BLENDWEIGHTS:
                        compBuf = new byte[VertexArray.NUM_BLENDWEIGHTS_COMPONENTS];
                        System.arraycopy(buf,
                                         vertexArray.getOffset() + compBuf.length * i,
                                         compBuf,
                                         0,
                                         compBuf.length);
                        vertex.setBlendWeights(compBuf);
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
            for (float j : vertex.getTexCoord())
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

            StringBuilder sbbi = new StringBuilder();
            sbbi.append("vbi");
            for (byte j : vertex.getBlendIndices())
            {
                sbbi.append(" ").append(String.format("%02x", j));
            }
            Logger.logD(sbbi.toString());

            StringBuilder sbbw = new StringBuilder();
            sbbw.append("vbw");
            for (byte j : vertex.getBlendWeights())
            {
                sbbw.append(" ").append(String.format("%02x", j));
            }
            Logger.logD(sbbw.toString());
        }

        triangles = new Triangle[header.getNumTriangles()];

        for (int i = 0; i < header.getNumTriangles(); ++i)
        {
            Triangle triangle = new Triangle();

            triangle.load(header, buf, i);

            triangles[i] = triangle;

            StringBuilder sb = new StringBuilder();
            sb.append("fm");
            for (int j : triangle.getVertices())
            {
                sb.append(" ").append(j);
                iVertexIndexArray[vertexIndexArrayIndex++] = j;
            }
            Logger.logD(sb.toString());
        }

        meshes = new Mesh[header.getNumMeshes()];

        for (int i = 0; i < header.getNumMeshes(); ++i)
        {
            Mesh mesh = new Mesh();

            mesh.load(header, buf, i);

            meshes[i] = mesh;
        }

        joints = new Joint[header.getNumJoints()];

        for (int i = 0; i < header.getNumJoints(); ++i)
        {
            Joint joint = new Joint();

            joint.load(header, buf, i);

            joints[i] = joint;
        }

        poses = new Pose[header.getNumPoses()];

        for (int i = 0; i < header.getNumPoses(); ++i)
        {
            Pose pose = new Pose();

            pose.load(header, buf, i);

            poses[i] = pose;
        }

        frames = new short[header.getNumFrames() * header.getNumFrameChannels()];

        ByteBuffer bb =
            ByteBuffer.wrap(buf, header.getOfsFrames(), header.getNumFrames() * header.getNumFrameChannels() * 2);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < header.getNumFrames() * header.getNumFrameChannels(); ++i)
        {
            frames[i] = bb.getShort();
            Logger.logD("" + frames[i]);
        }

        anims = new Anim[header.getNumAnims()];

        for (int i = 0; i < header.getNumAnims(); ++i)
        {
            Anim anim = new Anim();

            anim.load(header, buf, i);

            anims[i] = anim;
        }

        if (numIndices != null)
        {
            numIndices[0] = header.getNumTriangles() * 3;
            Logger.logI("" + numIndices[0]);
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
