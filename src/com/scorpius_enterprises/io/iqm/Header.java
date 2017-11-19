package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * {@link Header com.scorpius_enterprises.io.iqm.Header}
 *
 * @author Scorple
 * @since 2017-03-24
 */
public class Header
{
    public static final int HEADER_SIZE       = 124;
    public static final int MAGIC_SIZE        = 16;
    public static final int UNSIGNED_INT_SIZE = 4;

    private char[] magic;
    private int    version; // must be version 2
    private int    fileSize;
    private int    flags;
    private int    numText;
    private int    ofsText;
    private int    numMeshes;
    private int    ofsMeshes;
    private int    numVertexArrays;
    private int    numVertices;
    private int    ofsVertexArrays;
    private int    numTriangles;
    private int    ofsTriangles;
    private int    ofsAdjacency;
    private int    numJoints;
    private int    ofsJoints;
    private int    numPoses;
    private int    ofsPoses;
    private int    numAnims;
    private int    ofsAnims;
    private int    numFrames;
    private int    numFrameChannels;
    private int    ofsFrames;
    private int    ofsBounds;
    private int    numComment;
    private int    ofsComment;
    private int    numExtensions;
    private int    ofsExtensions; // these are stored as a linked list, not as a contiguous array

    private byte[] buf;

    public Header()
    {
        magic = new char[MAGIC_SIZE];

        buf = new byte[HEADER_SIZE];
    }

    public void load(final InputStream is)
    {
        try
        {
            is.read(buf);

            ByteBuffer bb = ByteBuffer.wrap(buf);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            byte[] magicBuf = new byte[MAGIC_SIZE];
            bb.get(magicBuf);
            String s  = new String(magicBuf);
            char[] ca = s.toCharArray();

            for (int i = 0;
                 i < MAGIC_SIZE;
                 ++i)
            {
                try
                {
                    magic[i] = ca[i];
                }
                catch (IndexOutOfBoundsException x)
                {
                    x.printStackTrace();
                }
            }

            Logger.logD(new String(magic));

            version = bb.getInt();
            Logger.logD("" + version);

            fileSize = bb.getInt();
            Logger.logD("" + fileSize);

            flags = bb.getInt();
            Logger.logD("" + flags);

            numText = bb.getInt();
            Logger.logD("" + numText);

            ofsText = bb.getInt();
            Logger.logD("" + ofsText);

            numMeshes = bb.getInt();
            Logger.logD("" + numMeshes);

            ofsMeshes = bb.getInt();
            Logger.logD("" + ofsMeshes);

            numVertexArrays = bb.getInt();
            Logger.logD("" + numVertexArrays);

            numVertices = bb.getInt();
            Logger.logD("" + numVertices);

            ofsVertexArrays = bb.getInt();
            Logger.logD("" + ofsVertexArrays);

            numTriangles = bb.getInt();
            Logger.logD("" + numTriangles);

            ofsTriangles = bb.getInt();
            Logger.logD("" + ofsTriangles);

            ofsAdjacency = bb.getInt();
            Logger.logD("" + ofsAdjacency);

            numJoints = bb.getInt();
            Logger.logD("" + numJoints);

            ofsJoints = bb.getInt();
            Logger.logD("" + ofsJoints);

            numPoses = bb.getInt();
            Logger.logD("" + numPoses);

            ofsPoses = bb.getInt();
            Logger.logD("" + ofsPoses);

            numAnims = bb.getInt();
            Logger.logD("" + numAnims);

            ofsAnims = bb.getInt();
            Logger.logD("" + ofsAnims);

            numFrames = bb.getInt();
            Logger.logD("" + numFrames);

            numFrameChannels = bb.getInt();
            Logger.logD("" + numFrameChannels);

            ofsFrames = bb.getInt();
            Logger.logD("" + ofsFrames);

            ofsBounds = bb.getInt();
            Logger.logD("" + ofsBounds);

            numComment = bb.getInt();
            Logger.logD("" + numComment);

            ofsComment = bb.getInt();
            Logger.logD("" + ofsComment);

            numExtensions = bb.getInt();
            Logger.logD("" + numExtensions);

            ofsExtensions = bb.getInt();
            Logger.logD("" + ofsExtensions);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getFileSize()
    {
        return fileSize;
    }

    public int getFlags()
    {
        return flags;
    }

    public int getNumText()
    {
        return numText;
    }

    public int getOfsText()
    {
        return ofsText;
    }

    public int getNumMeshes()
    {
        return numMeshes;
    }

    public int getOfsMeshes()
    {
        return ofsMeshes;
    }

    public int getNumVertexArrays()
    {
        return numVertexArrays;
    }

    public int getNumVertices()
    {
        return numVertices;
    }

    public int getOfsVertexArrays()
    {
        return ofsVertexArrays;
    }

    public int getNumTriangles()
    {
        return numTriangles;
    }

    public int getOfsTriangles()
    {
        return ofsTriangles;
    }

    public int getOfsAdjacency()
    {
        return ofsAdjacency;
    }

    public int getNumJoints()
    {
        return numJoints;
    }

    public int getOfsJoints()
    {
        return ofsJoints;
    }

    public int getNumPoses()
    {
        return numPoses;
    }

    public int getOfsPoses()
    {
        return ofsPoses;
    }

    public int getNumAnims()
    {
        return numAnims;
    }

    public int getOfsAnims()
    {
        return ofsAnims;
    }

    public int getNumFrames()
    {
        return numFrames;
    }

    public int getNumFrameChannels()
    {
        return numFrameChannels;
    }

    public int getOfsFrames()
    {
        return ofsFrames;
    }

    public int getOfsBounds()
    {
        return ofsBounds;
    }

    public int getNumComment()
    {
        return numComment;
    }

    public int getOfsComment()
    {
        return ofsComment;
    }

    public int getNumExtensions()
    {
        return numExtensions;
    }

    public int getOfsExtensions()
    {
        return ofsExtensions;
    }

    public byte[] getBuf()
    {
        return buf;
    }
}
