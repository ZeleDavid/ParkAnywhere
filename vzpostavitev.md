# VZPOSTAVITEV 

## Orodja
- Intellij 2019
- Docker za Windows (Eneterprise ali Professional windows)
<hr>  

- odprite Intellij
- namestite docker desktop za windows; nastavitve-general; obkljukajte Expose deamon on tcp://localhost...
- namestite docker integration plugin za Intellij
- odprite projekt <br>
edit configuration->run build image (obkljukajte) <br>
določite container name <br>
bind ports: specify <br>
port bindings-port:8080 protcol:tcp hostip:127.0.0.1 .....bindings-port:9990 protcol:tcp hostip:127.0.0.1 
- run

<hr>

Za inštalacijo Angular aplikacije je potrebno:
- odpreti mapo "sb-admin-material"
- v njej zagnati cmd in ukaze za posodobitev vseh odvisnosti: 
```
npm install
npm start
```

<hr>
Aplikacijo nato zaženemo kot vsako Angular aplikacijo, z ukazom  'ng serve' oz. z uporabo izbranega IDE-ja.
