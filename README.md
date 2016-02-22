# Réseau social permettant de comparer sa consommation électrique

## Modèle métier (à l'aide de JPA)

- Home (address, size, nbRooms, inhabitant, heaters).
- Person (name, firstName, mail, homes, eds, friends).
- Friends (idP1, idP2)
- SmartDevice (comporte des heaters et des electronicDevice).

Pour se connecter à une autre base de données, il faut modifier le fichier *persistence.xml*

Il faut exécuter jpa/JpaTest.java pour remplir la base de données

## Les Servlet (utilisation du framework Jersey)

Le fichier de mapping des servlet est web.xml (répertoire webapp).

L'URL de base pour accéder au service est /rest/

**/rest** Accueil du site, il n'y a qu'� se laisser guider pour :
- la gestion des personnes **/rest/person/**
		- ajout d'une personne via un formulaire
		- modification d'une personne **/rest/person/{id}**
		- suppression d'une personne **/rest/person/delete/{id}**
- ajouter des maison **/rest/home**
		- ajout d'une maison via un formulaire
		- modification d'une maison **/rest/home/{id}**
		- suppression d'une maison **/rest/home/delete/{id}**
