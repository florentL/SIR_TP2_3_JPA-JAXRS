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

**/rest/home** permet de récupérer les informations sur chaque home au format JSON (méthode GET)

**http://localhost:8080/myform.html** affiche un formulaire pour saisir de nouvelles personnes. Cela va directement compléter la base de données. Un récapitulatif des informations sera affiché à l'envoi du formulaire.
