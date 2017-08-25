package org.gszone.jfenix13.containers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import org.gszone.jfenix13.objects.TextureData;

public class Textures {

    private Array<TextureData>[] hash;

    public Textures() {
        hash = new Array[350];
    }

    public Texture getTexture(int num) {
        // Determino en que posición del Hash va la textura solicitada
        int hashIndex = num % hash.length;

        // Si es la primera textura en esa posición del Hash, creo el array y la textura solicitada
        if (hash[hashIndex] == null) {
            hash[hashIndex] = new Array();
            return createTexture(num);
        }

        // De lo contrario, la busco...
        else {
            for (int i = 0; i < hash[hashIndex].size; i++) {
                TextureData td = hash[hashIndex].get(i);
                if (td.getNum() == num) return
                        td.getTex();
            }
        }

        // Si no la encontró, la crea...
        return createTexture(num);
    }

    private Texture createTexture(int num) {
        // Creo la nueva textura y la agrego en el array de la posición correspondiente del Hash
        TextureData td = new TextureData(num);
        hash[num % hash.length].add(td);
        return td.getTex();
    }

    public void dispose() {
        for (Array<TextureData> h : hash)
            if (h != null)
                for (int i = 0; i < h.size; i++)
                    h.get(i).dispose();
    }
}
