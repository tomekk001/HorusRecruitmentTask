# Horus Recruitment Task - FileCabinet

Rozwiązanie zadania rekrutacyjnego na stanowisko Junior Java Developer. Projekt zawiera implementację logiki biznesowej dla struktury folderów (Pattern Composite) oraz zestaw testów jednostkowych.

## 🛠 Technologie

* **Java 21**
* **Maven** - zarządzanie zależnościami i budowanie projektu
* **JUnit 5** - testy jednostkowe

## 📋 Opis Rozwiązania

Głównym celem było zaimplementowanie metod interfejsu `Cabinet` w klasie `FileCabinet` z uwzględnieniem struktury zagnieżdżonej (`MultiFolder`).

### Kluczowe założenia implementacyjne:

1.  **Unikanie duplikacji kodu:**
    Cała logika przeszukiwania drzewa folderów została zamknięta w jednej prywatnej metodzie pomocniczej `getFolderStream()`. Dzięki temu metody publiczne (`findFolderByName`, `findFoldersBySize`, `count`) są zwięzłe i zajmują się jedynie filtrowaniem gotowych danych.

2.  **Stream API + Rekurencja:**
    Do "spłaszczenia" struktury drzewiastej użyłem operacji `flatMap` połączonej z rekurencją. Pozwala to na eleganckie dotarcie do każdego folderu, niezależnie od poziomu jego zagnieżdżenia.

3.  **Bezpieczeństwo typów:**
    Kod wykorzystuje `Optional<Folder>` do obsługi przypadków, gdy szukany element nie istnieje, co chroni przed `NullPointerException`.

## ✅ Testy

Projekt posiada pokrycie testami jednostkowymi (plik `FileCabinetTest.java`), które weryfikują:
* Wyszukiwanie folderów na najwyższym poziomie.
* Wyszukiwanie folderów głęboko zagnieżdżonych (wewnątrz `MultiFolder`).
* Poprawność zliczania wszystkich elementów (`count`).
* Obsługę pustych struktur i list.
* Poprawność zwracania pustych wyników (gdy folder nie istnieje).

## 🚀 Jak uruchomić projekt

### Wymagania
* JDK 21
* Maven

### Uruchomienie testów z linii komend
Aby zbudować projekt i uruchomić wszystkie testy, wpisz w terminalu:

```bash
mvn test
