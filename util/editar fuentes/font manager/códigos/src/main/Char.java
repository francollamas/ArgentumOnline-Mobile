package main;

public class Char {
    private int srcX;
    private int srcY;
    private int srcWidth;
    private int srcHeight;

    public Char() {
        srcX = 0;
        srcY = 0;
        srcWidth = 0;
        srcHeight = 0;
    }

    public void setSrcX(int srcX) {
        this.srcX = srcX;
    }

    public void setSrcY(int srcY) {
        this.srcY = srcY;
    }

    public void setSrcWidth(int srcWidth) {
        this.srcWidth = srcWidth;
    }

    public void setSrcHeight(int srcHeight) {
        this.srcHeight = srcHeight;
    }

    public int getSrcX() {
        return srcX;
    }

    public int getSrcY() {
        return srcY;
    }

    public int getSrcWidth() {
        return srcWidth;
    }

    public int getSrcHeight() {
        return srcHeight;
    }
}