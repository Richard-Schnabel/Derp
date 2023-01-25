![alt](https://user-images.githubusercontent.com/123179478/213934307-720f87a6-fccc-40d3-96e8-684c079a042a.png)
## DERP *(Developer's Engine for Recording Programing)*
- *Kapitán* - Vojtěch Borovský!
- *Kód* - Richard Schnábel
- *Design* - Ondřej Malý

# Zadání Nominačního kola *https://tourdeapp.cz/zadani*
## Základ
- [X] 20 | Zobrazit přidané záznamy
- [X] 10 | Přidat nový záznam s atributy: datum, strávený čas, programovací jazyk, hodnocení, popis
- [X]  6 | Nemožnost vložit nevalidní data: datum 
- [X]  2 | Nemožnost vložit nevalidní data: časový údaj
- [X]  2 | Nemožnost vložit nevalidní data: hodnocení
- [X] 20 | Upravit záznam
- [X] 10 | Odstranit záznam
- [X]  5 | Přidání záznamu s atributy shodnými s již vytvořeným záznamem vytvoří validní záznam
- [X]  5 | Odstranění záznamu s atributy shodnými s již vytvořeným záznamem odstraní pouze zamýšlený záznam
- [ ] 20 | Persistence práce se záznamy[^1]

## Červené Rozšiření
- [X] 10 | Seřadit zobrazené záznamy dle následujících atributů:datum
- [X] 10 | Seřadit zobrazené záznamy dle následujících atributů:strávený čas
- [X] 10 | Seřadit zobrazené záznamy dle následujících atributů:programovací jazyk
- [X] 10 | Seřadit zobrazené záznamy dle následujících atributů:hodnocení
- [X] 10 | Řazení zachovává pořadí z předchozího kroku
- [ ] 40 | Filtrovat zobrazené záznamy (dle atributů viz. požadavek 5)
- [ ] 10 | Filtrování zobrazí průnik vybraných filtrů

## Zelené Rozšiření
- [ ] 20 | Přidat nového programátora
- [ ] 10 | Upravit programátora
- [ ] 10 | Odstranit programátora
- [ ] 20 | Přidat záznam s programátorem
- [ ] 10 | Zobrazit záznamy konkrétního programátora
- [ ] 10 | Úprava programátora neovlivní jeho záznamy
- [ ] 10 | Odstranění programátora odstraní jeho záznam
- [ ] 10 | Odstranění záznamu neodstraní programátora

## Modré Rozšiření
- [ ] 20 | Přidat novou kategorii
- [ ] 10 | Upravit kategorii
- [ ] 10 | Smazat kategorii
- [ ] 15 | Přidat záznamy s kategorií
- [ ]  5 | Smazání záznamu s kategorií nesmaže kategorii
- [ ]  5 | Smazání kategorie se záznamem nesmaže záznam
- [ ] 10 | Přidat záznam s více kategoriemi
- [ ] 10 | Zobrazit záznamy jedné kategorie
- [ ] 15 | Zobrazit záznamy patřící do průniku kategorií

## Černé Rozšiření
- [X] 50 | Aplikace není zranitelná vůči SQL Injection[^2]
- [X] 50 | Aplikace není zranitelná vůči XSS (Cross Site Scriptnig)

## Design (User eXperience)
 -  50 | UX Základ
 -  50 | UX Červené Rozšiření
 -  50 | UX Zelené Rozšíření
 -  50 | UX Modré Rozšíření
 -  50 | UX Bonus 

## Celkem
 - 16/36 Rozšíření (44,4%)
 -	230/500 Bodů za funkcionalitu (46,0%)

[^1]:netušim co má znamenat "Persistence práce se záznamy"
[^2]:nepoužíváme SQL, takže aplikace není zranitelná vůči SQL Injection

# Download
https://github.com/Richard-Schnabel/Derp/blob/main/app/debug/app-debug.apk
