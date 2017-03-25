package com.scorpius_enterprises.io.iqm;

/**
 * {@link Header com.scorpius_enterprises.io.iqm.Header}
 *
 * @author Scorple
 * @since 2017-03-24
 */
public class Header
{
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
}
