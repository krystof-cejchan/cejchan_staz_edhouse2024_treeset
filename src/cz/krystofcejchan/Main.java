package cz.krystofcejchan;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import static java.lang.Character.isDigit;

public class Main {

    public static void main(String... args) {
        long l = System.currentTimeMillis();
        TreeSet<? super ContainerComparable> containers = new TreeSet<>();
        LinkedList<Coordinate> specialSymbols = new LinkedList<>();
        try (var br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            int lineCounter = 0;
            StringBuilder numericValues = new StringBuilder();
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);
                    if (isDigit(currentChar)) {
                        numericValues.setLength(0);
                        ArrayList<Coordinate> currentNumberCoordinates = new ArrayList<>();
                        do {
                            numericValues.append(currentChar);
                            currentNumberCoordinates.add(new Coordinate(i, lineCounter));
                        }
                        while (++i < line.length() && isDigit(currentChar = line.charAt(i)));
                        containers.add(new Container(Integer.parseInt(numericValues.toString()),
                                currentNumberCoordinates));
                    }
                    if (Character.toString(currentChar).matches("[^.0-9]"))
                        specialSymbols.add(new Coordinate(i, lineCounter));
                }
                lineCounter++;
            }
            int result = new Algorithm(containers, specialSymbols).getContainerValueSum();
            System.out.printf("Eda má ve skladu %d aktivních kontejnerů.\n", result);
            System.out.println(System.currentTimeMillis() - l);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.printf("Soubor nebyl nalezen: %s", Path.of(args[0]).toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
