package com.scorpius_enterprises.io.iqm;

import com.scorpius_enterprises.log.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * {@link VertexArray com.scorpius_enterprises.io.iqm.VertexArray}
 *
 * @author Scorple
 * @since 2017-03-24
 */
public class VertexArray
{
    public static final int VERTEX_ARRAY_SIZE = 20;
    public static final int UNSIGNED_INT_SIZE = 4;
    public static final int FLOAT_SIZE        = 4;

    public static final int TYPE_IQM_POSITION           = 0; // float, 3
    public static final int NUM_POSITION_COMPONENTS     = 3;
    public static final int TYPE_IQM_TEXCOORD           = 1; // float, 2
    public static final int NUM_TEXCOORD_COMPONENTS     = 2;
    public static final int TYPE_IQM_NORMAL             = 2; // float, 3
    public static final int NUM_NORMAL_COMPONENTS       = 3;
    public static final int TYPE_IQM_TANGENT            = 3; // float, 4
    public static final int NUM_TANGENT_COMPONENTS      = 4;
    public static final int TYPE_IQM_BLENDINDEXES       = 4; // ubyte, 4
    public static final int NUM_BLENDINDICES_COMPONENTS = 4;
    public static final int TYPE_IQM_BLENDWEIGHTS       = 5; // ubyte, 4
    public static final int NUM_BLENDWEIGHTS_COMPONENTS = 4;
    public static final int TYPE_IQM_COLOR              = 6; // ubyte, 4
    public static final int NUM_COLOR_COMPONENTS        = 4;

    public static final int FORMAT_IQM_BYTE   = 0;
    public static final int FORMAT_IQM_UBYTE  = 1;
    public static final int FORMAT_IQM_SHORT  = 2;
    public static final int FORMAT_IQM_USHORT = 3;
    public static final int FORMAT_IQM_INT    = 4;
    public static final int FORMAT_IQM_UINT   = 5;
    public static final int FORMAT_IQM_HALF   = 6;
    public static final int FORMAT_IQM_FLOAT  = 7;
    public static final int FORMAT_IQM_DOUBLE = 8;

    private int type; // type or custom name
    private int flags;
    private int format; // component format
    private int size; // number of components
    private int
                offset;
    // offset to array of tightly packed components, with num_vertexes * size total entries
    // offset must be aligned to max(sizeof(format), 4)

    //private byte[] buf;

    public VertexArray()
    {

    }

    public void load(final Header header,
                     final byte[] buf,
                     final int index,
                     final int num_vertexes)
    {
        int offset = header.getOfsVertexArrays() + index * VERTEX_ARRAY_SIZE;

        ByteBuffer bb = ByteBuffer.wrap(buf,
                                        offset,
                                        VERTEX_ARRAY_SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        type = bb.getInt();
        Logger.logD("" + type,
                    IqmLoader.LOG_TAG);

        flags = bb.getInt();
        Logger.logD("" + flags,
                    IqmLoader.LOG_TAG);

        format = bb.getInt();
        Logger.logD("" + format,
                    IqmLoader.LOG_TAG);

        size = bb.getInt();
        Logger.logD("" + size,
                    IqmLoader.LOG_TAG);

        this.offset = bb.getInt();
        Logger.logD("" + this.offset,
                    IqmLoader.LOG_TAG);

        /*switch (type)
        {
            case TYPE_IQM_POSITION:
                this.buf = new byte[num_vertexes * NUM_POSITION_COMPONENTS * FLOAT_SIZE];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
            case TYPE_IQM_TEXCOORD:
                this.buf = new byte[num_vertexes * NUM_TEXCOORD_COMPONENTS * FLOAT_SIZE];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
            case TYPE_IQM_NORMAL:
                this.buf = new byte[num_vertexes * NUM_NORMAL_COMPONENTS * FLOAT_SIZE];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
            case TYPE_IQM_TANGENT:
                this.buf = new byte[num_vertexes * NUM_TANGENT_COMPONENTS * FLOAT_SIZE];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
            case TYPE_IQM_BLENDINDEXES:
                this.buf = new byte[num_vertexes * NUM_BLENDINDICES_COMPONENTS];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
            case TYPE_IQM_BLENDWEIGHTS:
                this.buf = new byte[num_vertexes * NUM_BLENDWEIGHTS_COMPONENTS];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
            case TYPE_IQM_COLOR:
                this.buf = new byte[num_vertexes * NUM_COLOR_COMPONENTS];
                System.arraycopy(buf, offset, this.buf, 0, this.buf.length);
                break;
        }*/
    }

    public int getType()
    {
        return type;
    }

    public int getFlags()
    {
        return flags;
    }

    public int getFormat()
    {
        return format;
    }

    public int getSize()
    {
        return size;
    }

    public int getOffset()
    {
        return offset;
    }

    /*public byte[] getBuf()
    {
        return buf;
    }*/
}
