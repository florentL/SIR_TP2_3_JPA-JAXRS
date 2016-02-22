# RÃ©seau social permettant de comparer sa consommation Ã©lectrique

## ModÃ¨le mÃ©tier (Ã  l'aide de JPA)

- Home (address, size, nbRooms, inhabitant, heaters).
- Person (name, firstName, mail, homes, eds, friends).
- Friends (idP1, idP2)
- SmartDevice (comporte des heaters et des electronicDevice).

Pour se connecter Ã  une autre base de donnÃ©es, il faut modifier le fichier *persistence.xml*

Il faut exÃ©cuter jpa/JpaTest.java pour remplir la base de donnÃ©es

## Les Servlet (utilisation du framework Jersey)

Le fichier de mapping des servlet est web.xml (rÃ©pertoire webapp).

L'URL de base pour accÃ©der au service est /rest/

**/rest** Accueil du site, il n'y a qu'à se laisser guider pour :
- la gestion des personnes **/rest/person/**
	- ajout d'une personne via un formulaire
	- modification d'une personne **/rest/person/{id}**
	- suppression d'une personne **/rest/person/delete/{id}**
- ajouter des maison **/rest/home**
	- ajout d'une maison via un formulaire
	- modification d'une maison **/rest/home/{id}**
	- suppression d'une maison **/rest/home/delete/{id}**
