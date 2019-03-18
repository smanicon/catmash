[![Build Status](https://travis-ci.org/smanicon/catmash.svg?branch=master)](https://travis-ci.org/smanicon/catmash)

Catmash
---

## 1.Sujet

Le but de cet exercice est de développer une mini application web qui permet de trouver
le chat le plus mignon.

En se basant sur l'UX de __Facemash__ et les données de https://latelier.co/data/cats.json.
 * L'application devra donc être composée de deux pages :
   * Une page pour voter
   * Une page pour voir tous les chats avec leur score
 * La technologie est libre.
 * L’application doit pouvoir être utilisable depuis __Google Chrome__.
 * Le projet devra être publié sur un __GitHub public__.
 * Le projet devra __être hébergé__ pour être accessible publiquement.

Pendant la review de votre code, une attention toute particulière sera portée à la qualité
du code (structure, organisation, noms des variables et méthodes, etc...), la fréquence et
description des commits, ainsi qu’à l’expérience globale que propose l’application.
Il n’est pas grave que le projet ne soit pas entièrement terminé.

## 2.Compiler

### 2.1.Gradle

Il faut avoir préalablement avoir installé la jdk 8.
Lancer la commande suivante :
```
$ ./gradlew build check jar
```

### 2.2.Conteneur de build

Le conteneur de build permet de ne pas installer tous les outils de build. Il
faut disposer de docker et lancer les commandes suivantes :
```
$ docker build -t catmash-builder -f Dockerfile.build .
$ docker run --name catmash-builder-run -ti catmash-builder
$ docker cp catmash-builder-run:/home/gradle/project/build/lib build/
```

Une fois compilé, nettoyer docker :
```
$ docker rm catmash-builder-run
$ docker rmi catmash-builder
```

## 3.Exécuter

### 3.1.Gradle

Le projet peut être lancé par gradle via la command suivante :
```
$ ./gradlew bootRun
```

### 3.2.Java

La compilation fabrique un fat jar exécutable.
PPour lancer le projet, il faut le lancer avec la commande suivante :
```
$ java -jar catmash.jar --eventStoreFile=xxx
```
