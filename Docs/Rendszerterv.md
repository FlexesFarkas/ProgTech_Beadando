# Rendszerterv
## 1. A rendszer célja
A rendszer célja, hogy egy olyan rendelési rendszer legyen, amelyben a megrendelt ételeket a felhasználó személyre tudja szabni, ezen kívűl könnyedén bővithető legyen új hozzávalókkal és ételtípusokkal. A rendszer egy adatbázist használ a hozzávalók mennyiségének a követésére. A rendszer azstali eszközökre lett tervezve Java nyelv használatával. 
## 2. Projektterv

### 2.1 Projektszerepkörök, felelőségek:
   * Scrum masters: -
   * Product owner: Troll Ede
   * Üzleti szereplő: Kusper Gábor
     
### 2.2 Projektmunkások és felelőségek:
   * Frontend: Farkas Levente
   * Backend: Cz. Tóth Csanád Benedek, Farkas Levente
   * Tesztelés: Cz. Tóth Csanád Benedek, Farkas Levente
     
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

### 5.2 Menühierarchiák

## 6. Fizikai környezet

### Vásárolt softwarekomponensek és külső rendszerek

### Hardver topológia

### Fizikai alrendszerek

### Fejlesztő eszközök


## 8. Architekturális terv

### Webszerver

### Adatbázis rendszer

### A program elérése, kezelése

## 9. Adatbázis terv

## 10. Implementációs terv

## 11. Tesztterv

A tesztelések célja a rendszer és komponensei funkcionalitásának teljes vizsgálata,
ellenőrzése a rendszer által megvalósított üzleti szolgáltatások verifikálása.
A teszteléseket a fejlesztői csapat minden tagja elvégzi.
Egy teszt eredményeit a tagok dokumentálják külön fájlokba.

### Tesztesetek

 | Teszteset | Elvárt eredmény | 
 |-----------|-----------------| 
 | ... | ... |

### A tesztelési jegyzőkönyv kitöltésére egy sablon:

**Tesztelő:** Vezetéknév Keresztnév

**Tesztelés dátuma:** Év.Hónap.Nap

Tesztszám | Rövid leírás | Várt eredmény | Eredmény | Megjegyzés
----------|--------------|---------------|----------|-----------
például. Teszt #01 | Regisztráció | A felhasználó az adatok megadásával sikeresen regisztrálni tud  | A felhasználó sikeresen regisztrált | Nem találtam problémát.
... | ... | ... | ... | ...

## 12. Telepítési terv

Fizikai telepítési terv: 

Szoftver telepítési terv: 

## 13. Karbantartási terv

Fontos ellenőrizni:
...

Figyelembe kell venni a felhasználó által jött visszajelzést is a programmal kapcsolatban.
Ha hibát talált, mielőbb orvosolni kell, lehet az:
*	Működéssel kapcsolatos
*	Kinézet, dizájnnal kapcsolatos