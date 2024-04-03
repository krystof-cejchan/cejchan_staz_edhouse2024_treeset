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

/**
 * Main třída s main metodou
 *
 * @author Kryštof Čejchan
 * @version 17
 */
public class Main {
    /**
     * Spuštějící main metoda, která pomocí argumentů načte textový soubor a zpracuje jej
     *
     * @param args relativní nebo absolutní cesta k textovému souboru
     */
    public static void main(String... args) {
        if (args.length < 1) {
            System.out.println("Nebyla zadána cesta k souboru.");
            return;
        }
        // TreeSet ukládající instance tříd, které implementují rozhraní LocationComparable obsahující logiku za uspořádávání objektů podle jejich pozice
        TreeSet<? super LocationComparable> containers = new TreeSet<>();

        // LinkedList ukládající speciální symboly, pro určení, které containers jsou aktivní
        LinkedList<Location> specialSymbols = new LinkedList<>();

        // načtení souboru pomocí BufferedReader a FileReader
        try (var br = new BufferedReader(new FileReader(args[0]))) {
            String line;//řádek

            // index současného řádku
            int lineIndex = 0;

            // StringBuilder využíván pro ukládání čísel - čísla jsou v txt souboru znaky
            StringBuilder numericValues = new StringBuilder();

            // cyklus, který načítá obsahy řádků
            while ((line = br.readLine()) != null) {
                // cyklus procházející řádky po symbolech/písmenech
                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);

                    // pokud je současný symbol číslo, tj. je to kontejner
                    if (isDigit(currentChar)) {
                        //vymaže obsah StringBuilder
                        numericValues.setLength(0);

                        // seznam ukládající koordinace kontejnerů
                        ArrayList<Location> currentContainerLocation = new ArrayList<>();

                        // cyklus přidává číslice do StringBuilder a zároveň ukládá současné koordinace
                        // dokud jsme nedošli na konec řádku či pokud jsme na konci kontejneru
                        do {
                            numericValues.append(currentChar);
                            currentContainerLocation.add(new Location(i, lineIndex));
                        }
                        while (++i < line.length() && isDigit(currentChar = line.charAt(i)));

                        // přidá kontejner do TreeSetu
                        containers.add(new Container(Integer.parseInt(numericValues.toString()),
                                currentContainerLocation));
                    }
                    // pokud současný symbol není tečka nebo číslice, tzn. je to speciální symbol; přidá se do LinkedListu
                    if (Character.toString(currentChar).matches("[^.0-9]"))
                        specialSymbols.add(new Location(i, lineIndex));
                }
                // poté, co se projde řádek, se zvýší index
                lineIndex++;
            }
            // zavolání třídy Algorithm, která vypočítá součet všech aktivních kontejnerů
            int result = new Algorithm(containers, specialSymbols).getContainerValueSum();
            System.out.printf("Eda má ve skladu %d aktivních kontejnerů.\n", result);
        } catch (FileNotFoundException e) {
            // soubor nebyl nalezen
            e.printStackTrace();
            System.out.printf("Soubor nebyl nalezen: %s", Path.of(args[0]).toAbsolutePath());
        } catch (IOException e) {
            // jiná chyba se vstupem
            e.printStackTrace();
        }

    }
}
