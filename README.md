# TP Intergiciel - Kafka

Auteurs : ***Maxence ZOLNIERUCK*** & ***Josue VIDREQUIN***

## Sommaire

- [TP Intergiciel - Kafka](#tp-intergiciel---kafka)
  - [Sommaire](#sommaire)
  - [Introduction](#introduction)
  - [Commandes](#commandes)
  - [Démarrer les services](#démarrer-les-services)
    - [Topics Kafka](#topics-kafka)
    - [Base de données PostgreSQL](#base-de-données-postgresql)
    - [Consumers & Producers Kafa Java](#consumers--producers-kafa-java)
  - [Problèmes connus](#problèmes-connus)
  - [Points techniques](#points-techniques)
    - [Architecture applicative](#architecture-applicative)
    - [Base de données](#base-de-données)
    - [Format des données](#format-des-données)
    - [Reception des resultats](#reception-des-resultats)

## Introduction

Ce dépôt GitHub fait office de rendu de projet dans le cadre du module Intergiciel à l'INSA Hauts-de-France. Le but de ce projet est d'utiliser un bus de messages Kafka afin de faire communiquer plusieurs projets Java. L'application finale est utilisable via ligne de commandes et restitue plusieurs informations et statistiques calculés grâce aux données de l'API publique <https://api.covid19api.com>.

Parmis les valeurs que nous restituons, nous retrouvons : le nombre total de cas confirmés, le nombre total de personnes décédées, le nombre de nouveaux cas, le nombre de nouveaux décès et enfin, le nombre de personnes rétablies. Ces données sont regroupées soit par pays, soit à l'international avec des valeurs cumulées.

Toutes les données (sauf pour l'export) sont retournées à l'utilisateur au format JSON.

## Commandes

Voici les commandes supportées par l'application :

- **help** : affiche les commandes disponibles.
- **get_global_values** : affiche les valeurs cumulées de tous les pays.
- **get_country_values V_PAYS** : affiche les données spécifiques à un pays. Exemple : V_PAYS=FR
- **get_confirmed_avg** : affiche une moyenne des cas confirmés.
- **get_deaths_avg** : affiche une moyenne des décès.
- **get_countries_deaths_percent** : affiche le pourcentage de décès par rapport aux cas confirmés par pays.
- **export** : affiche une extraction de la base de données au format XML.

Ci dessous, le résultat de l'exécution de la commande **help** :

![image info](./Images/commande_help.png)

Un exemple de retour de commande avec **get_country_values FR** :

![image info](./Images/get_country_values-exemple.png)

## Démarrer les services

Le projet est composé grossièrement de trois blocs :

1. Les topics Kafka
2. La base de données PostgreSQL
3. Les projets Java (Consumers & Producers Kafka)

Nous allons voir comment lancer le projet, pas à pas. Il y a à votre disposition des [Scripts](./Scripts) qui vous aideront dans ce processus.

Il est important de suivre les étapes dans l'ordre car il y a une certaine logique dans le branchement des différentes parties.

### Topics Kafka

Tout d'abord, il vous faut avoir installé Kafka sur votre PC. Pour notre part, nous l'avons installé sur Windows 11 sur `C:\Kafka`.

Si vous rencontrez des problèmes pour l'installation de Kafka, nous vous recommandons ce tutoriel que nous avons suivi pour ce TP : <https://www.geeksforgeeks.org/how-to-install-and-run-apache-kafka-on-windows/>

Une fois Kafka installé, vous pouvez utiliser le script [demarrage_kafka_zookeeper.bat](./Scripts/demarrage_kafka_zookeeper.bat) afin de démarrer les services `Kafka` et `Zookeeper`.

Maintenant, nous allons passer à la création des topics qui seront utilisés par les applications Java. Pour cela, le script [creation_topics_kafka.bat](./Scripts/creation_topics_kafka.bat) a été créé.

Finalement, après avoir démarré Kafka et créé les topics, nous pouvons tester le bon fonctionnement des scripts avec la commande :

> C:\Kafka\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic TOPIC_NAME

Où TOPIC_NAME peut prendre les valeurs : topic1, topic2 et topic3.

Bien, nous avons passé la première étape, intéressons nous maintenant à la base de données PostgreSQL.

### Base de données PostgreSQL

Pour ce projet nous utilisons une base de données PostgreSQL car elle permet de stocker des valeurs au format JSON directement. Chose très utile pour nous qui utilisons une API publique nous retournant les valeurs au format JSON. De ce fait, après quelques traitements, nous pouvons directement stocker le resultat de l'appel dans la base de données.

Nous avons utilisé une image docker pour heberger notre base de données. Vous pouvez retrouver le [docker-compose](docker-compose.yml) a la racine de ce repértoire pour lancer vous même l'image docker.

La base de données sera accessible via l'adresse `http://localhost:5432/HOPSIIA`.

Ensuite, vous pouvez utiliser l'outil [pgAdmin](https://www.pgadmin.org/) mis à disposition par PostgreSQL afin d'administrer vos bases de données.

![image info](./Images/pgadmin_db.png)

Maintenant que la base de données a été créée, il faut instancier la table qui accueillera les données de l'API Covid-19. Pour cela, vous avez à votre disposition un [script SQL](./Scripts/creation_table_postgres.sql) dans ce dépot pour la création de la table **covid**.

Ensuite, il faut initialiser la table avec un enregistrement vide. Pour cela, exécutez la requête qui se trouve dans [initialiser_table_postgres.sql](./Scripts/initialiser_table_postgres.sql).

Le second point de ce projet est prêt. Cette base de données sera alimentée grâce à deux projets Java : Cs1 (Consumer 1) et Pr1 (Producer 1) que nous allons aborder dès maintenant.

### Consumers & Producers Kafa Java

## Problèmes connus

## Points techniques

### Architecture applicative

### Base de données

### Format des données

### Reception des resultats
