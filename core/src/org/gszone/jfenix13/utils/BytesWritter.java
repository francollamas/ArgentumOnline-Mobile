package org.gszone.jfenix13.utils;

import java.nio.ByteBuffer;

import static java.nio.ByteOrder.*;

/**
 * Realiza operaciones de escritura sobre un array de bytes.
 *
 * bytes: array de bytes en donde se realiza la escritura
 * littleEndian: indica el orden en que se tienen que leer los bytes.
 */
public class BytesWritter {
    private byte[] bytes;
    private boolean littleEndian;

    public BytesWritter() {
        this(false);
    }

    public BytesWritter(boolean littleEndian) {
        bytes = new byte[0];
        setLittleEndian(littleEndian);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void clear() {
        bytes = new byte[0];
    }

    public int getSize() {
        return bytes.length;
    }

    public boolean isLittleEndian() {
        return littleEndian;
    }

    public void setLittleEndian(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    /**
     * Agrega los nuevos bytes al final del array principal de bytes.
     */
    private void add(byte[] b) {
        byte[] bytes2 = new byte[bytes.length + b.length];
        System.arraycopy(bytes, 0, bytes2, 0, bytes.length);
        System.arraycopy(b, 0, bytes2, bytes.length, b.length);
        bytes = bytes2;
    }

    public void writeByte(int val) {
        byte[] b = new byte[1];
        if (val > 127) val -= 256;
        b[0] = (byte)val;
        add(b);
    }

    public void writeBoolean(boolean val) {
        writeByte((byte)(val? 1 : 0));
    }

    public void writeShort(int val) {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.order(LITTLE_ENDIAN);
        buf.putShort((short)val);
        buf.order(BIG_ENDIAN);
        add(buf.array());
    }

    public void writeInt(int val) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(LITTLE_ENDIAN);
        buf.putInt(val);
        buf.order(BIG_ENDIAN);
        add(buf.array());
    }

    public void writeFloat(float val) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(LITTLE_ENDIAN);
        buf.putFloat(val);
        buf.order(BIG_ENDIAN);
        add(buf.array());
    }

    public void writeDouble(double val) {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.order(LITTLE_ENDIAN);
        buf.putDouble(val);
        buf.order(BIG_ENDIAN);
        add(buf.array());
    }

    public void writeString(String texto) {
        writeString(texto, -1);
    }

    public void writeString(String texto, int length) {
        // Si la longitud es por defecto o sobrepasa el límite, se ajusta al tamaño del texto.
        if (length == -1 || (length > texto.length()))
            length = texto.length();

        writeShort((short) length);

        // Si la longitud es 0 no agregamos nada.
        if (length == 0) return;
        add(texto.substring(0, length).getBytes());
    }


}
