package org.gszone.jfenix13.utils;

import java.nio.ByteBuffer;

import static java.nio.ByteOrder.*;

/**
 * Realiza operaciones de lectura sobre un array de bytes.
 *
 * bytes: array de bytes en donde se realiza la lectura
 * pos: posición actual en el array
 * littleEndian: indica el orden en que se tienen que leer los bytes.
 */
public class BytesReader {
    private byte[] bytes;
    private int pos;
    private boolean littleEndian;

    public BytesReader() {
    }

    public BytesReader(byte[] bytes) {
        this(bytes, false);
    }

    public BytesReader(byte[] bytes, boolean littleEndian) {
        this.bytes = bytes;
        setLittleEndian(littleEndian);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        pos = 0;
   }

    public boolean isLittleEndian() {
        return littleEndian;
    }

    public void setLittleEndian(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    public int getAvailable() {
        return bytes.length - pos;
    }

    public int getSize() {
        return bytes.length;
    }

    public int getPos() {
        return pos;
    }

    public void skipBytes(int size) {
        pos += size;
    }

    /**
     * Copia un trozo del array principal en uno nuevo y lo devuelve
     */
    public byte[] getBytes(int size) {
        byte[] b = new byte[size];
        System.arraycopy(bytes, pos, b, 0, size);
        return b;
    }

    public byte readByte() {
        return bytes[pos++];
    }

    public boolean readBoolean() {
        return bytes[pos++] > 0;
    }

    public short readShort() {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(2));
        pos += 2;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getShort();
    }

    public int readInt() {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(4));
        pos += 4;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getInt();
    }

    public float readFloat() {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(4));
        pos += 4;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getFloat();
    }

    public double readDouble() {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(8));
        pos += 8;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getDouble();
    }

    public String readString() {
        int length = readShort();

        StringBuilder texto = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            texto.append((char)bytes[pos+i]);
        pos += texto.toString().length();
        return texto.toString();
    }
}
