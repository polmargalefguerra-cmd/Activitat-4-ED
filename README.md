[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/gN9rMu-y)
# Activitat 3: Implementacio d'un Graf Bipartit (Multillista) Usuari-Canço

En aquesta darrera activitat autoavaluable heu d'implementar una nova estructura de dades capaç de guardar relacions entre **dos conjunts diferents de dades** mitjançant una impliementació d'una multillista en memòria dinàmica. En concret, treballareu amb:

* un conjunt d'**usuaris**
* un conjunt de **cançons**
* i les **relacions** entre usuaris i cançons

L'objectiu de la practica es aprofitar els TADs que heu construit a les activitats anteriors, especialment la **llista** i la **taula de hashing**, per implementar ara un **graf bipartit generic**.

**IMPORTANT: Hi ha alguns canvis menors als TADS Llista i Hashing que haureu d'adaptar per a poder-la utilitzar en aquesta activitat.**

## 1. Context de l'activitat

En les activitats anteriors heu treballat amb:

* una estructura de llista per guardar col.leccions de dades
* una estructura de hashing per accedir rapidament als elements a partir d'una clau

En aquesta activitat no comenceu de zero: la idea es **reutilitzar aquests TADs** com a base per construir una estructura mes rica, un graf bipartit.

El problema que volem modelar es el seguent:

* cada usuari pot estar relacionat amb diverses cançons
* cada cançó pot estar relacionada amb diversos usuaris
* cada relacio usuari-cançó guarda una **valoracio entera entre 1 i 5**

Per tant, ja no n'hi ha prou amb guardar objectes de manera independent: ara cal guardar **la connexio entre dos tipus diferents de vertexs**.

## 2. Que es un graf bipartit en aquesta practica

El graf que heu d'implementar es **bipartit i no dirigit**.

Aixo vol dir que:

* hi ha **dos costats diferenciats** dins del graf
* en aquesta practica, el costat esquerre correspon als **usuaris**
* el costat dret correspon a les **cançons**
* nomes es poden crear arestes entre un vertex del costat esquerre i un vertex del costat dret

Per tant:

* **no** es poden crear relacions usuari-usuari
* **no** es poden crear relacions cançó-cançó

Com que el graf es no dirigit:

* una relacio usuari-cançó s'ha de poder consultar des dels dos costats
* pero aquesta relacio compta com **una unica aresta**

## 3. Tipus generics del TAD

Us proporcionem la interficie `TADGrafBipartit`, que defineix el comportament del TAD que heu d'implementar.

Aquest TAD treballa amb **cinc tipus generics**:

* `K1`: clau dels vertexs del costat esquerre
* `V1`: valor guardat als vertexs del costat esquerre
* `K2`: clau dels vertexs del costat dret
* `V2`: valor guardat als vertexs del costat dret
* `E`: valor guardat a les arestes

En aquesta practica concreta:

* `K1` sera el `username` de l'usuari
* `V1` sera un objecte `Usuari`
* `K2` sera el `titol` de la cançó
* `V2` sera un objecte `Canco`
* `E` sera un `Integer` corresponent a la valoracio

## 4. Estructures que podeu i heu de reutilitzar

Per implementar aquest graf bipartit podeu reaprofitar els vostres TADs previs.

En particular:

* per guardar els vertexs del costat usuari podeu fer servir una taula de hashing
* per guardar els vertexs del costat cançó podeu fer servir una altra taula de hashing
* per retornar col.leccions d'elements heu de fer servir la **vostra classe de llista**

La implementacio serà una multillista dinàmica tal i com hem vist a classe. A més, ha de continuar el mateix estil de treball de les practiques anteriors, sense dependre d'estructures alienes a l'enunciat per resoldre la logica principal

## 5. Classes principals del projecte

En aquesta activitat treballareu sobretot amb aquestes peces:

* `TADGrafBipartit`: interficie del TAD generic
* `GrafBipartit`: implementacio del TAD generic en una multillista utilitzant memòria dinàmica
* `Usuari`: classe de dades per al costat usuari
* `Canco`: classe de dades per al costat cançó
* `GrafUsuarisCancons`: capa de domini que encapsula el graf generic

La classe `GrafUsuarisCancons` juga el mateix paper que altres classes de domini que heu fet en practiques anteriors:

* amaga els detalls del TAD generic
* tradueix les operacions del graf a operacions del domini usuari-cançó
* facilita la carrega de dades des de fitxers CSV

## 6. Operacions que ha de suportar la implementacio

Heu d'implementar correctament totes les operacions descrites a `TADGrafBipartit`.

Entre d'altres, el graf ha de permetre:

* inserir, consultar i esborrar vertexs del costat esquerre
* inserir, consultar i esborrar vertexs del costat dret
* obtenir el nombre de vertexs de cada costat
* obtenir les claus de cada costat en forma de llista
* inserir, consultar i esborrar arestes
* comprovar si una aresta existeix
* obtenir el nombre total d'arestes
* saber si un vertex esta aillat
* obtenir el nombre d'adjacents d'un vertex
* obtenir la llista d'adjacents d'un vertex
* obtenir els vertexs de `K1` que comparteixen almenys 2 adjacents de `K2` amb un vertex donat
* obtenir la `distribucioGrauK1`
* obtenir la `distribucioGrauK2`

A mes, a traves de `GrafUsuarisCancons`, s'han d'oferir les operacions de domini que es fan servir als tests.

Per exemple:

* inserir i consultar usuaris
* inserir i consultar cançons
* inserir i consultar valoracions
* obtenir les cançons valorades per un usuari
* obtenir els usuaris que han valorat una cançó
* comptar quines cançons tenen una valoracio mitjana superior a un llindar
* obtenir els usuaris que han escoltat alguna cançó d'un genere concret

## 7. Regles de funcionament importants

Llegiu aquestes condicions amb atencio:

* En cada costat del graf **no hi pot haver dues claus repetides**.
  * Si s'insereix un vertex amb una clau que ja existia, se n'ha d'**actualitzar el valor**.

* En el graf **no hi pot haver dues arestes diferents amb la mateixa parella de vertexs**.
  * Si s'insereix una aresta que ja existeix, se n'ha d'**actualitzar el pes**.

* Les arestes uneixen **sempre** un vertex de l'esquerra amb un de la dreta.

* Si s'esborra un vertex:
  * s'ha d'eliminar el vertex
  * s'han d'eliminar tambe totes les arestes incidentes

* La valoracio de les relacions usuari-cançó ha de ser un enter entre `1` i `5`.

* Les consultes i esborrats han de respectar el comportament definit a les interficies i als tests, incloent les excepcions corresponents quan un element no existeix.

## 8. Fitxers CSV que es fan servir

Per aquesta activitat es treballa amb aquests fitxers:

* `songs1000.csv`
* `users1000.csv`
* `ratings.csv`
* `ratings1000.csv`

Els formats son els seguents:

* `songs1000.csv`: `id,titol,artista,genere,any,durada_segons,popularitat`
* `users1000.csv`: `id,username,name,country,age`
* `ratings.csv`: `username,song_title,rating`
* `ratings1000.csv`: `username,song_title,rating`

Interpretacio de cada fitxer:

* `songs1000.csv` conte les dades de 1000 cançons
* `users1000.csv` conte les dades de 1000 usuaris inventats
* `ratings.csv` conte relacions usuari-cançó en forma d'arestes
* `ratings1000.csv` conte un conjunt mes petit de relacions, util per a proves addicionals

Quan carregueu les dades:

* cada fila de `songs1000.csv` s'ha de convertir en un objecte `Canco`
* cada fila de `users1000.csv` s'ha de convertir en un objecte `Usuari`
* cada fila de `ratings.csv` o `ratings1000.csv` s'ha de convertir en una aresta del graf
# Activitat-4-ED
