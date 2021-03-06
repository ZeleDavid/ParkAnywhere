<p align="center"><img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/logo/pa.png" alt="ParkAnywhere" title="ParkAnywhere" width="600px" height="120px"/></p>

ParkAnywhere je rešitev, ki uporabnikom omogoča da najdejo parkirno mesto in plačajo parkirnino kar z mobilno aplikacijo, to pa podpira blockchain platforma ARK. Spletna aplikacija pa omogoča mestni upravi in lastnikom parkirišč da le-ta upravljajo.

<p align="center"><img src="https://www.cryptoninjas.net/wp-content/uploads/arkio-ARK-crypto-ninjas.png" height="200px" width="400px"/></p>
ARK zagotavlja uporabnikom, razvijalcem in startupom inovativne blockchain tehnologije. Naš cilj je ustvariti celoten ekosistem povezanih verig in navidezno pajkova mreža neskončnih primerov, zaradi katerih je ARK zelo prilagodljiv, prilagodljiv in prilagodljiv. ARK je varna platforma, namenjena množičnemu sprejemanju in bo zagotavljala storitve, ki jih potrošniki želijo in potrebujejo razvijalcem.

<hr>

## FUNKCIONALNOSTI

Naša rešitev je sestavljena iz spletne aplikacije namenjene občini in lastnikom parkirnih hiš, ki lahko z njimi upravljajo, jih dodajajo, brišejo itd.. Omogočen je pregled vseh parkirnih hiš in statistike parkiranja. <br>
Za uporabike pa smo izdelali mobilno aplikacijo, ki omogoča pregled parkirnih hiš, navigiranje do izbrane hiše, parkiranje vozila ter plačevanje s pomočjo <b>P</b>.

### SPLETNA APLIKACIJA

Spletna aplikacija je namenjena administrativnim primerom uporabe. V njej je glavni administrator občina, ki ima zmožnost nadzora vseh podatkov ter skrbi za profile uporabnikov strani - lastnikov parkirišč in parkirniš hiš. Ti lahko svoja parkirišča dodajajo / odstranjujejo iz aplikacije, nadzorujejo zasedenost preko statistike, ali preko vpogleda v transakcije preverijo kako dolgo (po kakšni ceni) so ljudje (z identifikatorjem - registrsko številko) parkirali v njihovih parkiriščih.

<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_8.png" alt="Prijava" title="Prijava" width="400px" height="200px" margin="20px" />|<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_1.png" alt="Dashboard" title="Dashboard" width="400px" height="200px"/>|
:---: |:---: |
<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_2.png" alt="Tabela" title="Tabela" width="400px" height="200px"/>|<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_3.png" alt="Dodaj" title="Dodaj" width="400px" height="200px"/> |

### MOBILNA APLIKACIJA

Mobilna aplikacija podpira celotno izkušnjo parkiranja. Uporabniku prikaže vsa prosta parkirišča, ki so razvrščena po oddaljenosti, ga vodi do njih in mu z uporabo GPS lokacije in BLE beaconov omogoča samodejno zaznavanje parkirišča in s tem lažje plačevanje. Ko si uporabnik prvič ustvari denarnico dobi 100 ParkCoinov s katerimi plačuje parkirnino.

<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_20190612-195453.jpg" alt="Pogled1" title="Pogled1" width="250" height="500"/>|<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_20190612-195501.jpg" alt="Pogled2" title="Pogled2" width="250" height="500"/>|<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_20190612-195611.jpg" alt="Pogled3" title="Pogled3" width="250" height="500"/>|
:---: |:---: | :---: |
<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_20190612-195513.jpg" alt="Pogled4" title="Pogled4" width="250" height="500"/>|<img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_20190612-195524.jpg" alt="Pogled5" title="Pogled5" width="250" height="500"/> | <img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/Screenshot_20190612-195923.jpg" alt="Pogled6" title="Pogled6" width="250" height="500"/>|

<br><br>
## ARHITEKTURA

Mobilna aplikacija je namenjena uporabnikom, ki bodo le parkirali, spletna pa je namenjena lastnikom parkirišč in mestni upravi. Ti dve aplikaciji pa povezuje naše blockchain zaledje, ki sprejme transakcije od uporabnikov in omogoči upravljalcem parkirišč da te podatke vidijo. Na spodnji sliki pa je prikazana arhitektura našega ParkChaina.
<p align="center"><img src="https://raw.githubusercontent.com/ZeleDavid/ParkAnywhere/master/Documentation/screenshoti/arhitektura.png" alt="Arhitektura" title="Arhitektura" width="700px" height="500px"/></p>

## TEHNOLOŠKI SKLAD

#### MOBILNA APLIKACIJA
- Kotlin
- Java
- Retrofit za parsanje JSON

#### SPLETNA APLIKACIJA
- Angular
- TypeScript
- HTML
- CSS
- JavaScript

#### PARKCHAIN
- TypeScript
- Postgress

## VZPOSTAVITEV

Če želite vzpostaviti spletno in mobilno aplikacijo, najdete podrobnejša navodila na spodnjem linku: <br>
<a href="vzpostavitev.md">Vzpostavitev</a>

<br>
<br> 

# AVTORJI
[<img alt="EvaSmolak" src="https://avatars0.githubusercontent.com/u/33725038?s=400&v=4" width="117">](https://github.com/EvaSmolak) |[<img alt="CetinaLuka" src="https://avatars3.githubusercontent.com/u/33715779?s=400&u=4752d8027850c8f376c54dd977df726c1d24c58a&v=4" width="117">](https://github.com/CetinaLuka) |[<img alt="KovacZan" src="https://avatars1.githubusercontent.com/u/39158639?s=400&v=4" width="117">](https://github.com/KovacZan) |[<img alt="ZeleDavid" src="https://avatars3.githubusercontent.com/u/33752926?s=400&v=4" width="117">](https://github.com/ZeleDavid) |
:---: |:---: |:---: |:---: |
[EvaSmolak](https://github.com/EvaSmolak) |[CetinaLuka](https://github.com/CetinaLuka) |[KovacZan](https://github.com/KovacZan) |[ZeleDavid](https://github.com/ZeleDavid) |
| <p> Spletna aplikacija </p> | <p> Mobilna aplikacija </p> | <p> ParkChain </p> | <p> Spletna aplikacija </p> |
