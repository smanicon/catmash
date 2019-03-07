# choix du moteur de production

* Status: accepted
* Date: 2019-03-14

## Contexte et énnoncé du problème

Nous souhaitons simplifier la compilation, le lancement des tests, ainsi que le
packaging du projet. Nous souhaitons également que le projet possède une
structure normée.

## Options envisagées

 1. Maven
 2. SBT
 3. Gradle
 4. Ant

## Résultat de la décision

Le choix est : 4 - Gradle

* Entre Maven et Ant
* DSL en Groovy/Kotlin
* Très utilisé dans les projets libres
* Compilation optimisée
* configuration concise
