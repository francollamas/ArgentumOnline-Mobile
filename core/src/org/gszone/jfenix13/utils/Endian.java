package org.gszone.jfenix13.utils;


import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import static java.nio.ByteOrder.*;

/**
 * Clase con funciones útiles para el orden de bytes de un número
 *
 * leShort, leInt, etc: cambia el orden de los bytes de un número.
 * leReadShort, etc: lee un número con el mismo orden de bytes que usa VB6 (little endian)
 * y lo cambia para que Java lo entienda (big endian)
 */
public class Endian {

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
}
