package main;

public class Csv {
    public int imageWidth;
    public int imageHeight;
    public int cellWidth;
    public int cellHeight;
    public int startChar;
    public String name;
    public int[] baseWidth;
    public int globalWOffset;

    public Csv() {
        imageWidth = 0;
        imageHeight = 0;
        cellWidth = 0;
        cellHeight = 0;
        startChar = 0;
        name = "";
        baseWidth = new int[256];
        globalWOffset = 0;
    }
}
