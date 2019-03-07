# choix du langage partie back

* Status: accepted
* Date: 2019-03-14

## Contexte et énnoncé du problème

Le but de la partie back est de pouvoir répondre aux différentes requêtes du
Front. Elle devra donc proposer une API en _REST JSON_. 

Nous souhaitons également, afin de simplifier la testablité du projet, mais
aussi son utilisation, que la partie back soit sans état (_stateless_)

Enfin, la partie back devra tourner sur une JVM.

Quel langage est le mieux adapté à ces critères ?

## Options envisagées

 1. Java
 2. Scala
 3. Kotlin

## Résultat de la décision

Le choix est : 1 - Java

* Langage déjà connu
* Pas suffisamment de temps pour se former sur les autres langages
* Compatible avec beaucoup de framework
