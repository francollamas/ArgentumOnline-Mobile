package main;

public class Font {
    private String name;
    private int tex;
    private int offset;
    private Char[] character;

    public Font() {
        character = new Char[256];
        for (int i = 0; i < character.length; i++) {
            character[i] = new Char();
        }
    }

    public String getName() {
        return name;
    }

    public int getTex() {
        return tex;
    }

    public int getOffset() {
        return offset;
    }

    public Char[] getChar() {
        return character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTex(int tex) {
        this.tex = tex;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
