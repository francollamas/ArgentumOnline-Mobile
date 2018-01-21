package org.gszone.jfenix13.utils;

import java.nio.ByteBuffer;

import static java.nio.ByteOrder.*;

/**
 * Realiza operaciones de lectura sobre un array de bytes.
 *
 * bytes: array de bytes en donde se realiza la lectura
 * pos: posición actual en el array
 * posMark: posición de marca
 * littleEndian: indica el orden en que se tienen que leer los bytes.
 */
public class BytesReader {
    private byte[] bytes;
    private int pos;
    private int posMark;
    private boolean littleEndian;

    public BytesReader() {
        this(false);
    }

    public BytesReader(boolean littleEndian) {
        this(new byte[0], littleEndian);
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

    /**
     * Reemplaza los datos del buffer por otros
     * @param bytes
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        pos = 0;
        posMark = 0;
    }

    /**
     * Agrega mas bytes al final del buffer
     * @param b
     */
    public void appendBytes(byte[] b) {
        byte[] bytes2 = new byte[bytes.length + b.length];
        System.arraycopy(bytes, 0, bytes2, 0, bytes.length);
        System.arraycopy(b, 0, bytes2, bytes.length, b.length);
        bytes = bytes2;
    }

    /**
     * Vacía el buffer
     */
    public void clear() {
        setBytes(new byte[0]);
    }

    public boolean isLittleEndian() {
        return littleEndian;
    }

    public void setLittleEndian(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    /**
     * Devuelve la cantidad de bytes no leídos.
     */
    public int getAvailable() {
        return bytes.length - pos;
    }

    /**
     * Devuelve el tamaño total del buffer
     */
    public int getSize() {
        return bytes.length;
    }

    public int getPos() {
        return pos;
    }

    /**
     * Guarda la posición actual
     */
    public void mark() {
        posMark = pos;
    }

    /**
     * La posición del buffer vuelve a la marca
     */
    public void reset() {
        pos = posMark;
    }

    /**
     * Saltea una cantidad determinada de bytes
     * @param size cantidad de bytes a saltar
     */
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

    public int readByte() throws NotEnoughDataException {
        if (pos + 1 > bytes.length) throw new NotEnoughDataException();

        int num = bytes[pos++];
        if (num < 0) num += 256;
        return num;
    }

    public boolean readBoolean() throws NotEnoughDataException {
        if (pos + 1 > bytes.length) throw new NotEnoughDataException();

        return bytes[pos++] != 0;
    }

    public short readShort() throws NotEnoughDataException {
        if (pos + 2 > bytes.length) throw new NotEnoughDataException();

        ByteBuffer buf = ByteBuffer.wrap(getBytes(2));
        pos += 2;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getShort();
    }

    public int readInt() throws NotEnoughDataException {
        if (pos + 4 > bytes.length) throw new NotEnoughDataException();

        ByteBuffer buf = ByteBuffer.wrap(getBytes(4));
        pos += 4;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getInt();
    }

    public float readFloat() throws NotEnoughDataException {
        if (pos + 4 > bytes.length) throw new NotEnoughDataException();

        ByteBuffer buf = ByteBuffer.wrap(getBytes(4));
        pos += 4;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getFloat();
    }

    public double readDouble() throws NotEnoughDataException {
        if (pos + 8 > bytes.length) throw new NotEnoughDataException();

        ByteBuffer buf = ByteBuffer.wrap(getBytes(8));
        pos += 8;
        if (littleEndian) buf.order(LITTLE_ENDIAN);
        return buf.getDouble();
    }

    public String readString() throws NotEnoughDataException {
        int length = readShort();

        if (pos + length > bytes.length) throw new NotEnoughDataException();

        StringBuilder texto = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int num = bytes[pos + i];
            if (num < 0) num += 256;
            texto.append((char) num);
        }

        pos += texto.toString().length();
        return texto.toString();
    }
/*
    public String read() {
        int length = bytes.length - pos;
        StringBuilder texto = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int num = bytes[pos + i];
            if (num < 0) num += 256;
            texto.append((char) num);
        }

        pos += texto.toString().length();
        return texto.toString();
    }*/
}
