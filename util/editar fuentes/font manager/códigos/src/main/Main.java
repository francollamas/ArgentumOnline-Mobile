package main;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private Fonts fonts;
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main p = new Main();
    }

    public Main() {
        fonts = new Fonts();
        int opc = menu();

        while (opc != 4) {
            switch (opc) {
            case 1:
                opcion1();
                break;
            case 2:
                opcion2();
                break;
            case 3:
                opcion3();
                break;
            }

            opc = menu();
        }

    }

    public int menu() {
        System.out.print("\n" +
                "///////////////////////////////////////////////////////\n" +
                "//////////////////// FONT MANAGER /////////////////////\n" +
                "//                                                   //\n" +
                "//    1. Mostrar fuentes                             //\n" +
                "//    2. Agregar fuente                              //\n" +
                "//    3. Borrar fuente                               //\n" +
                "//    4. Salir                                       //\n" +
                "//                                                   //\n" +
                "///////////////////////////////////////////////////////\n" +
                "Ingrese la opción: ");
        return sc.nextInt();
    }

    public void opcion1() {
        if (fonts.getFont().size() == 0) {
            System.out.println("No hay ninguna fuente guardada");
        }
        else {
            for (int i = 0; i < fonts.getFont().size(); i++) {
                System.out.println(i + 1 + ". '" + fonts.getFont().get(i).getName() + "'");
            }
        }
    }


    public void opcion2() {

        System.out.print("Ingrese el nombre de la fuente: ");
        String filename = "fonts/" + sc.next() + ".csv";

        File f = new File(filename);
        if (!f.exists()) {
            System.out.println("Error... el archivo no existe");
            return;
        }

        // LEER ARCHIVO CSV Y GUARDAR SU INFO EN UN ARRAY
        ArrayList<String> array = new ArrayList<String>();
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                array.add(nextLine[1]);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // INTERPRETAR CSV DESDE EL ARRAY
        Csv csv = new Csv();
        csv.imageWidth = Integer.parseInt(array.get(0));
        csv.imageHeight = Integer.parseInt(array.get(1));
        csv.cellWidth = Integer.parseInt(array.get(2));
        csv.cellHeight = Integer.parseInt(array.get(3));
        csv.startChar = Integer.parseInt(array.get(4));
        csv.name = array.get(5) + ", " + array.get(6);
        for (int i = 8; i < 264; i++) {
            csv.baseWidth[i - 8] = Integer.parseInt(array.get(i));
        }
        csv.globalWOffset = Integer.parseInt(array.get(1032));

        // CONVERSIÓN AL SISTEMA DE FUENTES DEL AO
        fonts.getFont().add(new Font());
        int index = fonts.getFont().size() - 1;

        // borro esto porque no se leer strings en VB6
        //fonts.getFont().get(index).setName(csv.name);
        fonts.getFont().get(index).setName("Font");

        System.out.println("Se agregará la fuente '" + csv.name + "'");

        System.out.print("\tIngresar un número de textura: ");
        fonts.getFont().get(index).setTex(sc.nextInt());

        System.out.print("\tIngrese el número de offset (en pixeles): ");
        fonts.getFont().get(index).setOffset(sc.nextInt());

        // Indicar la informacion de cada char existente
        int charsPerRow = csv.imageWidth / csv.cellWidth;
        int quantRows = (256 - csv.startChar) / charsPerRow;
        int quantLastRow = (256 - csv.startChar) % charsPerRow;

        int tempX = 0, tempY = 0;
        int startChar = csv.startChar;
        for (int i = 1; i <= quantRows; i++) {
            for (int j = startChar; j - startChar < charsPerRow; j++) {
                fonts.getFont().get(index).getChar()[j].setSrcX(tempX);
                fonts.getFont().get(index).getChar()[j].setSrcY(tempY);
                fonts.getFont().get(index).getChar()[j].setSrcWidth(csv.baseWidth[j] + csv.globalWOffset);
                fonts.getFont().get(index).getChar()[j].setSrcHeight(csv.cellHeight);
                tempX += csv.cellWidth;
            }
            tempX = 0;
            tempY = i * csv.cellHeight;
            startChar += charsPerRow;
        }

        for (int i = startChar; i - startChar < quantLastRow; i++) {
            fonts.getFont().get(index).getChar()[i].setSrcX(tempX);
            fonts.getFont().get(index).getChar()[i].setSrcY(tempY);
            fonts.getFont().get(index).getChar()[i].setSrcWidth(csv.baseWidth[i] + csv.globalWOffset);
            fonts.getFont().get(index).getChar()[i].setSrcHeight(csv.cellHeight);
            tempX += csv.cellWidth;
        }

        save();
        System.out.println("La fuente se guardó correctamente");

    }

    public void save() {
        // GUARDAR TODAS LAS FUENTES EN EL ARCHIVO
        try {
            RandomAccessFile file = new RandomAccessFile("fonts.ind", "rw");
            file.writeInt(Util.bigToLittle_Int(fonts.getFont().size()));
            for (int i = 0; i < fonts.getFont().size(); i++) {
                // borro esto porque no se leer strings en VB6
                //file.writeUTF(fonts.getFont().get(i).getName());
                file.writeInt(Util.bigToLittle_Int(fonts.getFont().get(i).getTex()));
                file.writeInt(Util.bigToLittle_Int(fonts.getFont().get(i).getOffset()));
                for (int j = 0; j < fonts.getFont().get(i).getChar().length; j++) {
                    file.writeInt(Util.bigToLittle_Int(fonts.getFont().get(i).getChar()[j].getSrcX()));
                    file.writeInt(Util.bigToLittle_Int(fonts.getFont().get(i).getChar()[j].getSrcY()));
                    file.writeInt(Util.bigToLittle_Int(fonts.getFont().get(i).getChar()[j].getSrcWidth()));
                    file.writeInt(Util.bigToLittle_Int(fonts.getFont().get(i).getChar()[j].getSrcHeight()));
                }
            }
            file.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void opcion3() {
        System.out.println("Ingrese el número de fuente que desea borrar:");
        int index = sc.nextInt();
        if (index - 1 < fonts.getFont().size() && index - 1 >= 0) {
            fonts.getFont().remove(index - 1);
            save();
            System.out.println("La fuente se borró correctamente");
        }
        else {
            System.out.println("Error... la fuente que intenta borrar no existe");
        }

    }
}
