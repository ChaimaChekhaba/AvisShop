Projet: AvisShop

Equipe: Chaima Chekhaba, Maher Jaouadi, Mohamed Daboussi


#Ce que nous avons fait:
- La communication avec le backend en utilisant Retrofit.
- L'architecture de l'application: MVVM
- Le mode offline avec Worker.
- Les capteurs utilisés: La caméra pour le scan des codes à barres et accès réseau pour le mode offline.
- Les tests, nous avons utilisé la librarie Mockito.s

#Ce qui ne marche pas dans cette phase:
- Le work manager ne marche pas en background correctement.

#Remarques :
- Les codes à barres qui ne sont pas de type UPC (code à barres internationaux) ne sont pas évalués. Voici des exemples de code UPC: 0640135291395 - 0883412740890.
- Site pour trouver le code UPC d’un produit : https://www.barcodespider.com/

