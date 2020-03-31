Projet: AvisShop

Equipe: Chaima Chekhaba, Maher Jaouadi, Mohamed Daboussi

#Ce qui ne marche pas dans cette phase:

Le scan du code à barre se fait en mode landscape seulement à cause de la library utilisée (Zxing). Cette dernière ne permet pas le scan en mode portrait.
#Les modifications des choix considérés dans le rapport d'analyse:

Nous utilisons Room (avec les coroutines) pour le stockage au lieu de MongoDB comme prévu dans le rapport d'analyse car Room est plus facile à mettre en place, plus adéquat pour android. Et comme nous travaillons avec une petite base de données, Room est plus que suffisant.
#Remarques :

étant donné que la partie backend n'est pas encore implémenté (la communication avec le cloud et les webservices). Nous avons utilisé un fichier Json contenant 5 products pour la démonstration.
Après le scan d'un code à barre, nous choisissons un produit du fichier des produits Json aléatoirement et nous mettons à jour seulement son code à barre. Alors, nous pouvons avoir des produits avec les mêmes informations (nom, image, etc) mais avec des codes à barre différent.
#Ce qui reste à faire :

La communication avec le backend (le cloud et les webservices).
Mode offline.
L'option "A propos" dans le menu de navigation n'est pas encore implémenté.
Le bouton "Acheter" référence les sites d'Amazon et Ebay directement au lieu les liens des produits car le backend n'est pas encore implémenté.
