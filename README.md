# Stáž Edhouse 2024

## Zadání
Eda potřebuje, abys sečetl čísla aktivních kontejnerů. Budeš k tomu potřebovat jeho mapku, kterou najdeš přiloženou k tomuto zadání. Podívejme se na následující příklad:
```
467..257..

...*......

..35..633.

......#...

617*......

.....+.13.

..592.....

......755.

...$.*....

.664.598..
```
Na mapce je vyznačena spousta čísel a symbolů, kterým úplně nerozumíš. To ale nevadí, protože jak Eda prozrazuje, každé číslo, které sousedí s nějakým symbolem, byť diagonálně, je číslo aktivního kontejneru a je nutné ho započítat do výsledné sumy (Tečky (.) nepovažujeme za symbol). Na mapce příkladu jsou dvě čísla, která nejsou čísly aktivního kontejneru, neboť nesousedí s žádným symbolem: 257 (vpravo nahoře) a 13 (vpravo uprostřed). Všechna ostatní čísla sousedí s nějakým symbolem a tudíž se jedná o čísla aktivních kontejnerů; jejich součet je 4361.

## Popis programu a algoritmu
### Načítání souborů

První fází programu je načítání souborů pomocí `BufferedReader`. Načítání probíhá po řádcích, přičemž se tyto řádky analyzují, tj. hledají se kontejnery a speciální symboly. Kontejnery se ukládají do `TreeSet` kolekce a speciální znaky do `ArrayDeque` kvůli jejich optimalizaci v rámci použitého algoritmu.
### Datové struktury a třídy

Program pracuje primárně s `TreeSet` kolekcí, která umožňuje uspořádat prvky pomocí rozhraní/interface `Comparable`. Toto rozhraní je implementováno v abstraktní třídě `LocationComparable`, která definuje abstraktní metody `x()` a `y()`. Tyto metody jsou využity v metodě `compareTo()`, která seřazuje objekty podle `y()` a poté podle `x()` v kolekci `TreeSet`.

Třídy, které dědí od abstraktní třídy `LocationComparable`, jsou `Container` a `Location`. `TreeSet` kolekce může uchovávat tyto třídy, které jsou potomky `LocationComparable`, a jsou seřazeny podle `compareTo()` metody. Třídy `Container` a `Location` nemohou přebít/override metodu `compareTo()`, protože je tato metoda označena jako _final_ v `LocationComparable` - tím se docílí univerzality při komparaci a nemusí se definovat tato metoda pro každého potomka zvlášť.
### Algoritmus

Poté, co jsou datové struktury naplněny kontejnery a speciálními znaky, se začne provádět jádro celého programu.

- Začne se provádět cyklus nad speciálními znaky:
    - Pro každý speciální znak se vygenerují jeho sousedící prvky:
        - Každý tento sousedící prvek, instance třídy `Location`, se porovná s pozicemi kontejnerů v `TreeSet`, zda se překrývají v rámci jejich x a y souřadnice. `Location` lze hledat mezi objekty tříd `Container`, díky jejich společnému dědění abstraktní třídy `LocationComparable` a díky tomu, že `TreeSet` může obsahovat instance tříd `Location` i `Container`. Díky uspořádání `Comparable` má toto prohledávání časovou složitost O(log n).
        - Pokud je nějaký prvek nalezen, tzn. byl nalezen aktivní kontejner, neboť "sousedí" se speciálním znakem, přičte se jeho hodnota do výsledné sumy a kontejner se odstraní z `TreeSet`, aby nebyl nalezen a započítán skrz jiný speciální znak.

Na závěr se vypíše celková výsledná suma a program se ukončí.
