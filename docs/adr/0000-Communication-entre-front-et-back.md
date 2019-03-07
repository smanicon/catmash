# Communication entre Front et Back

* Status: accepted
* Date: 2019-03-14

## Contexte et énnoncé du problème

Le projet est composé de deux parties :
 * __une partie présentation__, qui a pour but de gérer l'affichage et les
   interactions de l'utilisateur. La partie présentation doit fonctionner avec
   le navigateur _Google Chrome_.
 * __Une partie back__, qui mettra à disposition une API pour la partie
   présentation.
Vu que la partie présentation doit s'exécuter sur un navigateur, la
communication devra se faire via le protocole _HTTP_.

## Options envisagées

 1. API Rest JSON
 2. API GraphQL
 3. SOAP

## Résultat de la décision

Option choisie : 1 - API Rest JSON

 * [+] API standard
 * [+] Évolutif
 * [+] De nombreux outils permettent de faire des appels
 * [+] Javascript comprend nativement le JSON
 * [-] Limitation des verbes d'action : GET, POST, PUT, PATCH, DELETE
