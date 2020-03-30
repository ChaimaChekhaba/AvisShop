Projet: AvisShop

Equipe: Chaima Chekhaba, Maher Jaouadi, Mohamed Daboussi

Ce qui ne marche pas dans cette phase:
- Le scan du code à barre se fait en mode landscape seulement à cause de la library utilisée (Zxing). Cette dernière ne permet pas le scan en mode portrait.
- L'option "About" dans le menu de navigation n'est pas encore implémenté.
- La partie backend n'est pas implémenté (la communication avec le cloud et les webservices). Nous avons utilisé un fichier Json contenant des produts pour la démonstration.
- Le bouton "Acheter" référence les sites d'Amazon et Ebay directement au lieu les liens des produits car le backend n'est pas implémenté.

Les modifications des choix considérés dans le rapport d'analyse:
- Nous utilisons Room pour le stockage au lieu de MongoDB comme prévu dans le rapport d'analyse car Room est plus facile à mettre en place, plus adéquat pour android. Et comme nous travaillons avec une petite base de données, Room est plus que suffisant.

Ce qui reste à faire :
- La communication avec le backend (le cloud et les webservices).
- Mode offline.
