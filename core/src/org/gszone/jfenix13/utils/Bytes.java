package org.gszone.jfenix13.utils;


import com.badlogic.gdx.utils.Array;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import static java.nio.ByteOrder.*;

/**
 * Clase con funciones útiles para manejo de bytes.
 *
 * leShort, leInt, etc: cambia el orden de los bytes de un número.
 * leReadShort, etc: lee un número de un DataInputStream con el mismo orden de bytes que usa VB6 (little endian)
 * y lo cambia para que Java lo entienda (big endian). Se usan para la lectura de archivos binarios.
 *
 * readShort, readInt, writeShort, writeInt, etc: lee o escribe bytes en un array de bytes.
 * Se usan mucho para pasar datos por el socket.
 */
public class Bytes {

    public static short leReadShort(DataInputStream dis) throws IOException {
        return leShort(dis.readShort());
    }

    public static int leReadInt(DataInputStream dis) throws IOException {
        return leInt(dis.readInt());
    }

    public static float leReadFloat(DataInputStream dis) throws IOException {
        return leFloat(dis.readFloat());
    }

    public static int leReadByte(DataInputStream dis) throws IOException {
        int res = dis.readByte();
        if (res >= 0 && res <= 127) {
            return res;
        }
        else {
            return 256 - res;
        }
    }

    public static short leShort(short n) {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.order(BIG_ENDIAN);
        buf.putShort(n);
        buf.order(LITTLE_ENDIAN);

        return buf.getShort(0);
    }

    public static int leInt(int n) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(BIG_ENDIAN);
        buf.putInt(n);
        buf.order(LITTLE_ENDIAN);

        return buf.getInt(0);
    }

    public static float leFloat(float n) {

        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(BIG_ENDIAN);
        buf.putFloat(n);
        buf.order(LITTLE_ENDIAN);

        return buf.getFloat(0);
    }

    public static byte leByte(byte n) {
        ByteBuffer buf = ByteBuffer.allocate(1);
        buf.order(BIG_ENDIAN);
        buf.put(n);
        buf.order(LITTLE_ENDIAN);

        return buf.get(0);
    }


    /**
     * Extrae los primeros "length" bytes del array (y los borra definitivamente de éste)
     */
    private static byte[] getBytes(Array<Byte> bytes, int length) {
        byte[] bytes2 = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes2[i] = bytes.get(0);
            bytes.removeIndex(0);
        }
        return bytes2;
    }

    public static byte readByte(Array<Byte> bytes) {
        return getBytes(bytes, 1)[0];
    }

    public static boolean readBoolean(Array<Byte> bytes) {
        byte b = getBytes(bytes, 1)[0];
        if (b == 1) return true;
        return false;
    }

    public static short readShort(Array<Byte> bytes) {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(bytes, 2));
        buf.order(LITTLE_ENDIAN);
        return buf.getShort();
    }

    public static int readInt(Array<Byte> bytes) {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(bytes, 4));
        buf.order(LITTLE_ENDIAN);
        return buf.getInt();
    }

    public static float readFloat(Array<Byte> bytes) {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(bytes, 4));
        buf.order(LITTLE_ENDIAN);
        return buf.getFloat();
    }

    public static double readDouble(Array<Byte> bytes) {
        ByteBuffer buf = ByteBuffer.wrap(getBytes(bytes, 8));
        buf.order(LITTLE_ENDIAN);
        return buf.getDouble();
    }

    public static String readString(Array<Byte> bytes) {
        int length = readShort(bytes);

        String texto = "";
        for (int i = 0; i < length; i++)
            texto += (char)getBytes(bytes, 1)[0];

        return texto;
    }

    /**
     * Inserta los bytes especificados en el array de bytes
     */
    public static void setBytes(Array<Byte> bytes, ByteBuffer buf) {
        for (int i = 0; i < buf.array().length; i++) {
            bytes.add(buf.array()[i]);
        }
    }

    public static void writeByte(Array<Byte> bytes, byte num) {
        bytes.add(num);
    }

    public static void writeBoolean(Array<Byte> bytes, boolean bool) {
        if (bool)
            bytes.add((byte)1);
        else
            bytes.add((byte)0);
    }

    public static void writeShort(Array<Byte> bytes, short num) {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.order(LITTLE_ENDIAN);
        buf.putShort(num);
        buf.order(BIG_ENDIAN);
        setBytes(bytes, buf);
    }

    public static void writeInt(Array<Byte> bytes, int num) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(LITTLE_ENDIAN);
        buf.putInt(num);
        buf.order(BIG_ENDIAN);
        setBytes(bytes, buf);
    }

    public static void writeFloat(Array<Byte> bytes, float num) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(LITTLE_ENDIAN);
        buf.putFloat(num);
        buf.order(BIG_ENDIAN);
        setBytes(bytes, buf);
    }

    public static void writeDouble(Array<Byte> bytes, double num) {
        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.order(LITTLE_ENDIAN);
        buf.putDouble(num);
        buf.order(BIG_ENDIAN);
        setBytes(bytes, buf);
    }

    public static void writeString(Array<Byte> bytes, String texto) {
        writeString(bytes, texto, -1);
    }

    public static void writeString(Array<Byte> bytes, String texto, int length) {
        // Si la longitud es por defecto o sobrepasa el límite, se ajusta al tamaño del texto.
        if (length == -1 || (length > texto.length()))
            length = texto.length();

        // Si la longitud es 0 no grabamos nada.
        if (length == 0) return;

        writeShort(bytes, (short) length);

        byte[] textoBytes = texto.substring(0, length).getBytes();
        for (int i = 0; i < texto.length(); i++) {
            bytes.add(textoBytes[i]);
        }
    }

}
