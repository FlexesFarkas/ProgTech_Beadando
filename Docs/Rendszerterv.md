# Rendszerterv
## 1. A rendszer célja
A rendszer célja, hogy egy olyan rendelési rendszer legyen, amelyben a megrendelt ételeket a felhasználó személyre tudja szabni, ezen kívűl könnyedén bővithető legyen új hozzávalókkal és ételtípusokkal. A rendszer egy adatbázist használ a hozzávalók mennyiségének a követésére. A rendszer azstali eszközökre lett tervezve Java nyelv használatával. 
## 2. Projektterv

### 2.1 Projektszerepkörök, felelőségek:
   * Scrum masters: -
   * Product owner: Troll Ede
   * Üzleti szereplő: Kusper Gábor
     
### 2.2 Projektmunkások és felelőségek:
   * Frontend: Détári Levente
   * Backend: Cz. Tóth Csanád Benedek, Détári Levente
   * Tesztelés: Cz. Tóth Csanád Benedek, Détári Levente
     
### 2.3 Ütemterv:

|Funkció                  | Feladat                                | Prioritás | Becslés (nap) | Aktuális becslés (nap) | Eltelt idő (nap) | Becsült idő (nap) |
|-------------------------|----------------------------------------|-----------|---------------|------------------------|------------------|---------------------|
|Rendszerterv             |Megírás                                 |         1 |             1 |                      1 |                1 |                   1 |
|Szoftver|Fejlesztés|2|14|14|14|
Szoftver|Tesztelés|3|1|1|1|

## 3. Üzleti folyamatok modellje

### 3.1 Üzleti szereplők
Vevő

Étterem tulajdonos
### 3.2 Üzleti folyamatok
#### Vevő:
* étel személyreszabása
* rendelés leadása
* saját rendelés törlése
* fizetés

#### Étterem tulajdonos:
* új ételtípusok megadása
* új hozzávalók megadása
* régi ételtípusok módosítása/törlése
* régi hozzávalók módosítása/törlése

## 4. Követelmények

### Funkcionális követelmények

| ID | Megnevezés | Leírás |
| --- | --- | --- |
| K1 | Étel személyre szabása | A felhasználó képes legyen személyre szabni az ételt, amit rendel |
| K2 | Rendelés leadása | A felhasználó képes leadni a megrendelt ételeit |
| K3 | Rendelés törlése | A felhasználó képes kitörölni a rendelését, mielőtt leadná |
| K4 | Új hozzávalók hozzáadása | Új hozzávalók hozzáadása kódból |
| K5 | Új ételtípusok hozzáadása | Új ételtípusok hozzáadása kódból |
| K6 | Régi hozzávalók módosítása | Régi hozzávalók módosítása és törlése kódból |
| K7 | Régi ételtípusok módosítása | Régi ételtípusok módosítása és törlése kódból |
| K8 | Raktár tartalmának követése | A rendszer automatikusan számon tartja a felhasznált hozzávalókat |

### Nemfunkcionális követelmények

| ID | Megnevezés | Leírás |
| --- | --- | --- |
| K9 | Dinamikus felület | A felület automatikusan hozzáadja az új hozzávalókat és ételtípusokat indításkor. |

### Támogatott eszközök
Asztali gépek, amelyek képesek Java-ban írt alkalmazásokat futtatni.

## 5. Funkcionális terv

### 5.1 Rendszerszereplők
* Vevő
* Éttermi alkalmazott

### 5.2 Menühierarchiák
* Kezdőképernyő:
    * Rendelés kezdése
* Rendelés:
    * Új étel hozzáadása
    * Hozzávalók mennyiségének állítása
    * Tovább a fizetéshez
    * Kosárból törlés
    * Rendelés törlése
* Fizetés:
    * Fizetés törlése
    * Fizetés
* Rendelés befejezése

## 6. Fizikai környezet
A szoftver asztali gépekre készült, amelyek képesek Java applikációkat futtatni.

### Vásárolt softwarekomponensek és külső rendszerek
\-
### Fejlesztő eszközök
IntelliJ, DB Browser (SQLite)


## 7. Architekturális terv

### Adatbázis rendszer
Az adatbázis kezelése SQLite motorral történik.

### A program elérése, kezelése
A szoftver futtatásához szükséges az Oracle Java SE 8-as verziója.
## 8. Adatbázis terv
* Foods
    * food_id
    * ingredients
    * food_type
    * price
* IngredientTypes
    * ingredient_id
    * ingredient_type
* Ingredients
    * ingredient_id
    * ingredient_name
    * ingredient_price
    * ingredient_amount
* Orders
    * order_id
    * food_id
* Types
    * type

## 9. Implementációs terv
A backendhez Java-t használunk, a frontendhez JavaFX-et.

## 10. Tesztterv

Unit teszt:

A backendben található osztályok függvényeit unit tesztekkel kell tesztelni.
## 12. Telepítési terv

Fizikai telepítési terv: 

Szoftver telepítési terv: 

A szoftver működéséhez szükséges a Java SE 8-as verziója, standalone futtatható a szoftver.

## 13. Karbantartási terv


Fontos ellenőrizni, hogy a különböző rendelések helyesen mentődnek el, a rendszer bővítése nem okoz problémákat korábban létrehozott részein. Figyelembe kell venni a felhasználó által jött visszajelzést is a programmal kapcsolatban.
Ha hibát talált, mielőbb orvosolni kell, lehet az:
*	Működéssel kapcsolatos
*	Kinézet, dizájnnal kapcsolatos
