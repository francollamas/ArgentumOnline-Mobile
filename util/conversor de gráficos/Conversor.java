import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Conversor de gráficos
 *
 * Convierte gráficos de BMP/PNG a PNG, permitiendo cambiar ciertos colores por otros
 * Además con la opción de cambiar su tamaño a potencias de 2.
 *
 * Se pueden cambiar varios colores al mismo tiempo (o ninguno)
 * Se puede manipular el color del exceso del gráfico (si se usan potencias de 2).
 *
 * Thusing, 2017 - GS-ZONE
 */
public class Conversor {
    public static Scanner lector;

    public static String dirInput;
    public static String dirOutput;
    public static String extInput;
    public static String extOutput;
    public static String pow2;
    public static Color excedente;

    public static Color[][] colores;

    static {
        lector = new Scanner(System.in);

        dirInput = "input";
        dirOutput = "output";

        extInput = "bmp";
        extOutput = "png";

        pow2 = "no";
        excedente = new Color(0, 0, 0, 0);

        colores = new Color[1][2];
        colores[0][0] = new Color(0, 0, 0, 255);
        colores[0][1] = new Color(0, 0, 0, 0);
    }


    public static void main(String[] args) {

        int opc = menu();

        while (opc != 8) {
            switch (opc) {
                case 1:
                    System.out.println("Ingrese la extension de origen (bmp/png): ");
                    extInput = lector.next();
                    extInput = extInput.toLowerCase();
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del directorio de origen: ");
                    dirInput = lector.next();
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del directorio de destino: ");
                    dirOutput = lector.next();
                    break;
                case 4:
                    System.out.print("¿Potencias de 2? (si/no): ");
                    pow2 = lector.next();

                    if (pow2.equals("si")) {
                        System.out.print("¿Cambiar el color de exceso? (si/no): ");
                        String cambiar = lector.next();

                        if (cambiar.equals("si")) {
                            System.out.println("Color de exceso:");
                            System.out.print("\tR:");
                            int r = lector.nextInt();
                            System.out.print("\tG:");
                            int g = lector.nextInt();
                            System.out.print("\tB:");
                            int b = lector.nextInt();
                            System.out.print("\tA:");
                            int a = lector.nextInt();
                            excedente = new Color(r, g, b, a);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Agregar color>>");
                    System.out.println("Color de origen:");
                    System.out.print("\tR:");
                    int r = lector.nextInt();
                    System.out.print("\tG:");
                    int g = lector.nextInt();
                    System.out.print("\tB:");
                    int b = lector.nextInt();
                    System.out.print("\tA:");
                    int a = lector.nextInt();

                    System.out.println("\nColor de destino:");
                    System.out.print("\tR:");
                    int r2 = lector.nextInt();
                    System.out.print("\tG:");
                    int g2 = lector.nextInt();
                    System.out.print("\tB:");
                    int b2 = lector.nextInt();
                    System.out.print("\tA:");
                    int a2 = lector.nextInt();
                    Color[][] cols = new Color[colores.length + 1][2];
                    for (int i = 0; i < colores.length; i++) {
                        cols[i] = colores[i];
                    }
                    cols[cols.length - 1][0] = new Color(r, g, b, a);
                    cols[cols.length - 1][1] = new Color(r2, g2, b2, a2);
                    colores = cols;
                    break;

                case 6:
                    colores = new Color[0][2];
                    break;
                case 7:
                    convertir();
                    break;
            }

            opc = menu();
        }
    }

    public static void convertir() {
        int current = 0;

        ArrayList<File> finalFiles = new ArrayList();

        File dir = new File(dirInput);
        File[] files = dir.listFiles();

        for (File f : files) {
            String filePath = f.getPath();
            if (f.isFile() && (filePath.toLowerCase().endsWith("." + extInput))) {
                finalFiles.add(f);
            }
        }

        for (File f : finalFiles) {
            String name = f.getName().substring(0, f.getName().length() - 4);

            current += 1;

            try {

                BufferedImage img = ImageIO.read(new File(dirInput + "/" + name + "." + extInput));

                int width = img.getWidth();
                int height = img.getHeight();

                if (pow2.equals("si")) {
                    width = nextPow2(width);
                    height = nextPow2(height);
                }

                BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                for (int x = 0; x < img.getWidth(); x++) {
                    for (int y = 0; y < img.getHeight(); y++) {

                        Color c = new Color(img.getRGB(x, y), true);
                        img2.setRGB(x, y, img.getRGB(x, y));

                        for (int i = 0; i < colores.length; i++) {
                            if (c.getRGB() == colores[i][0].getRGB()) {
                                img2.setRGB(x, y, colores[i][1].getRGB());
                            }
                        }
                    }
                }

                if (pow2.equals("si"))
                for (int x = 0; x < img2.getWidth(); x++) {
                    for (int y = img.getHeight(); y < img2.getHeight(); y++) {
                        img2.setRGB(x, y, excedente.getRGB());
                    }
                }

                for (int x = img.getWidth(); x < img2.getWidth(); x++) {
                    for (int y = 0; y < img2.getHeight(); y++) {
                        img2.setRGB(x, y, excedente.getRGB());
                    }
                }


                ImageIO.write(img2, extOutput, new File(dirOutput + "/" + name + "." + extOutput));
                System.out.println((int)(current / (float)finalFiles.size() * 100) + "%");
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static int nextPow2(int num) {
        int i = 0;
        while (num > Math.pow(2, i)) {
            i++;
        }

        return (int)Math.pow(2, i);
    }

    public static int menu() {
        System.out.println("\n///// Conversor de graficos /////");
        System.out.println("1. Extension de origen: " + extInput);
        System.out.println("2. Directorio de origen: " + dirInput);
        System.out.println("3. Directorio de destino: " + dirOutput);
        System.out.println("4. Usar potencias de 2: " + pow2);

        System.out.println("\tcolor de exceso: " + getSColor(excedente));

        System.out.println("5. Agregar colores a cambiar (R, G, B, A):");

        for (int i = 0; i < colores.length; i++) {
            System.out.print("\t");
            for (int j = 0; j < colores[i].length; j++) {
                System.out.print(getSColor(colores[i][j]) + " ");
                if (j == colores[i].length - 2) {
                    System.out.print("se cambia por ");
                }
            }
            System.out.println("");
        }

        System.out.println("6. Vaciar colores a cambiar");
        System.out.println("7. Convertir");
        System.out.println("8. SALIR");

        System.out.print("\nOpcion: ");
        return lector.nextInt();

    }

    public static String getSColor(Color c) {
        return "(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + c.getAlpha() + ")";
    }
}
