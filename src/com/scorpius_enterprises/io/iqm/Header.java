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
    public static final int UNSIGNED_INT_SIZE = 4;

    private char[] magic;
    private int    version; // must be version 2
    private int    filesize;
    private int    flags;
    private int    num_text;
    private int    ofs_text;
    private int    num_meshes;
    private int    ofs_meshes;
    private int    num_vertexarrays;
    private int    num_vertexes;
    private int    ofs_vertexarrays;
    private int    num_triangles;
    private int    ofs_triangles;
    private int    ofs_adjacency;
    private int    num_joints;
    private int    ofs_joints;
    private int    num_poses;
    private int    ofs_poses;
    private int    num_anims;
    private int    ofs_anims;
    private int    num_frames;
    private int    num_framechannels;
    private int    ofs_frames;
    private int    ofs_bounds;
    private int    num_comment;
    private int    ofs_comment;
    private int    num_extensions;
    private int    ofs_extensions; // these are stored as a linked list, not as a contiguous array

    private byte[] buf;

    public Header()
    {
        magic = new char[16];

        buf = new byte[HEADER_SIZE];
    }

    public void load(final InputStream is)
    {
        try
        {
            is.read(buf);

            ByteBuffer bb = ByteBuffer.wrap(buf);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            byte[] magicBuf = new byte[16];
            bb.get(magicBuf);
            String s  = new String(magicBuf);
            char[] ca = s.toCharArray();

            for (int i = 0; i < 16; ++i)
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

            filesize = bb.getInt();
            Logger.logD("" + filesize);

            flags = bb.getInt();
            Logger.logD("" + flags);

            num_text = bb.getInt();
            Logger.logD("" + num_text);

            ofs_text = bb.getInt();
            Logger.logD("" + ofs_text);

            num_meshes = bb.getInt();
            Logger.logD("" + num_meshes);

            ofs_meshes = bb.getInt();
            Logger.logD("" + ofs_meshes);

            num_vertexarrays = bb.getInt();
            Logger.logD("" + num_vertexarrays);

            num_vertexes = bb.getInt();
            Logger.logD("" + num_vertexes);

            ofs_vertexarrays = bb.getInt();
            Logger.logD("" + ofs_vertexarrays);

            num_triangles = bb.getInt();
            Logger.logD("" + num_triangles);

            ofs_triangles = bb.getInt();
            Logger.logD("" + ofs_triangles);

            ofs_adjacency = bb.getInt();
            Logger.logD("" + ofs_adjacency);

            num_joints = bb.getInt();
            Logger.logD("" + num_joints);

            ofs_joints = bb.getInt();
            Logger.logD("" + ofs_joints);

            num_poses = bb.getInt();
            Logger.logD("" + num_poses);

            ofs_poses = bb.getInt();
            Logger.logD("" + ofs_poses);

            num_anims = bb.getInt();
            Logger.logD("" + num_anims);

            ofs_anims = bb.getInt();
            Logger.logD("" + ofs_anims);

            num_frames = bb.getInt();
            Logger.logD("" + num_frames);

            num_framechannels = bb.getInt();
            Logger.logD("" + num_framechannels);

            ofs_frames = bb.getInt();
            Logger.logD("" + ofs_frames);

            ofs_bounds = bb.getInt();
            Logger.logD("" + ofs_bounds);

            num_comment = bb.getInt();
            Logger.logD("" + num_comment);

            ofs_comment = bb.getInt();
            Logger.logD("" + ofs_comment);

            num_extensions = bb.getInt();
            Logger.logD("" + num_extensions);

            ofs_extensions = bb.getInt();
            Logger.logD("" + ofs_extensions);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public int getFilesize()
    {
        return filesize;
    }

    public int getFlags()
    {
        return flags;
    }

    public int getNum_text()
    {
        return num_text;
    }

    public int getOfs_text()
    {
        return ofs_text;
    }

    public int getNum_meshes()
    {
        return num_meshes;
    }

    public int getOfs_meshes()
    {
        return ofs_meshes;
    }

    public int getNum_vertexarrays()
    {
        return num_vertexarrays;
    }

    public int getNum_vertexes()
    {
        return num_vertexes;
    }

    public int getOfs_vertexarrays()
    {
        return ofs_vertexarrays;
    }

    public int getNum_triangles()
    {
        return num_triangles;
    }

    public int getOfs_triangles()
    {
        return ofs_triangles;
    }

    public int getOfs_adjacency()
    {
        return ofs_adjacency;
    }

    public int getNum_joints()
    {
        return num_joints;
    }

    public int getOfs_joints()
    {
        return ofs_joints;
    }

    public int getNum_poses()
    {
        return num_poses;
    }

    public int getOfs_poses()
    {
        return ofs_poses;
    }

    public int getNum_anims()
    {
        return num_anims;
    }

    public int getOfs_anims()
    {
        return ofs_anims;
    }

    public int getNum_frames()
    {
        return num_frames;
    }

    public int getNum_framechannels()
    {
        return num_framechannels;
    }

    public int getOfs_frames()
    {
        return ofs_frames;
    }

    public int getOfs_bounds()
    {
        return ofs_bounds;
    }

    public int getNum_comment()
    {
        return num_comment;
    }

    public int getOfs_comment()
    {
        return ofs_comment;
    }

    public int getNum_extensions()
    {
        return num_extensions;
    }

    public int getOfs_extensions()
    {
        return ofs_extensions;
    }

    public byte[] getBuf()
    {
        return buf;
    }
}
