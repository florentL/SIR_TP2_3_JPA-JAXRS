# Réseau social permettant de comparer sa consommation électrique

## Lancement

Exécuter `src/main/javajpa/JpaTest.java` pour peupler la base de données

Lancer tomcat pour accéder à l'application
```bash
> mvn tomcat7:run
```
**localhost:8080/rest**

Pour se connecter à une autre base de données, il faut modifier le fichier **persistence.xml**

## Modèle métier (à l'aide de JPA)

- Home (address, size, nbRooms, inhabitant, heaters).
- Person (name, firstName, mail, homes, eds, friends).
- Friends (idP1, idP2)
- SmartDevice (comporte des heaters et des electronicDevice).


## Les Servlet (utilisation du framework Jersey)

Le fichier de mapping des servlet est web.xml (répertoire webapp).

L'URL de base pour accéder au service est /rest/

**/rest** Accueil du site, il n'y a qu'à se laisser guider pour :
- la gestion des personnes **/rest/person/**
	- ajout d'une personne via un formulaire
	- modification d'une personne **/rest/person/{id}**
	- suppression d'une personne **/rest/person/delete/{id}**
- gestion des maisons **/rest/home**
	- ajout d'une maison via un formulaire
	- modification d'une maison **/rest/home/{id}**
	- suppression d'une maison **/rest/home/delete/{id}**
