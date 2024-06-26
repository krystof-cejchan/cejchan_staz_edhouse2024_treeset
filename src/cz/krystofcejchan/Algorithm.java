package cz.krystofcejchan;

import java.util.ArrayDeque;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Třída Algorithm skrývá většinu logiky za celým programem
 */
public class Algorithm {
    final private TreeSet<? super LocationComparable> containers;
    final private ArrayDeque<Location> specialSymbols;
    private int containerValueSum;

    /**
     * Parametrický kontruktor {@link Algorithm}
     *
     * @param containers     TreeSet kontejnerů
     * @param specialSymbols ArrayDeque umístění speciálních symbolů
     */
    public Algorithm(TreeSet<? super LocationComparable> containers, ArrayDeque<Location> specialSymbols) {
        this.containers = containers;
        this.specialSymbols = specialSymbols;
        sumUpContainers();
    }

    /**
     * Metoda je volána v kontruktoru třídy.
     * Pro každý speciální symbol spustí metodu handleOffsetElements
     */
    private void sumUpContainers() {
        specialSymbols.forEach(this::handleOffsetElements);
    }

    /**
     * Tato metoda zjistí, zda se v okolí speciálního symbolu nachází kontejner; pokud ano, tak jeho hodnotu přičte do containerValueSum
     *
     * @param specialSymbolLocation {@link Location} umístění speciálního symbolu
     */
    private void handleOffsetElements(Location specialSymbolLocation) {
        for (Location location : generateOffset(specialSymbolLocation)) {
            if (containers.isEmpty()) return;
            // v TreeSetu se najde Container pomocí location – containers ukládá <? super LocationComparable>, proto musí být přetypován
            var containerFromTreeSet = (Container) containers.floor(location);
            // pokud se v TreeSetu našel kontejner a tento kontejner se rozpíná na lokacích, mezi nimi je i location z cyklu,
            // tak se přičte hodnota kontejneru a odstraní se z TreeSetu, neboť kontejner může sousedit s více speciálními znaky
            if (containerFromTreeSet != null && containerFromTreeSet.containsWithBinarySearch(location)) {
                containerValueSum += containerFromTreeSet.getValue();
                containers.remove(containerFromTreeSet);
            }
        }
    }

    /**
     * funkce generuje {@link Location}[], které sousedí s {@link Location} zadaným v parametru funkce
     *
     * @param location {@link Location} umístění speciálního symbolu
     * @return Location[] sousedící s parametrem funkce
     */
    private Location[] generateOffset(Location location) {
        return Stream.of(new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}})
                .map(offset -> new Location(location.x() + offset[0], location.y() + offset[1]))
                .toArray(Location[]::new);
    }

    /**
     * Getter vracící součet hodnot aktivních kontejnerů
     *
     * @return součet hodnot aktivních kontejnerů
     */
    public int getContainerValueSum() {
        return containerValueSum;
    }
}