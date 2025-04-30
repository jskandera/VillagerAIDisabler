# VillagerAIDisabler v1.0

**Description**

VillagerAIDisabler est un plugin Spigot/Paper qui permet de contrôler l'intelligence artificielle (IA) des villageois. Il offre la possibilité de désactiver l'IA des villageois en fonction de plusieurs critères configurables, ce qui peut être utile pour optimiser les performances du serveur ou pour des configurations de jeu spécifiques.

**Fonctionnalités**

* **Désactivation Globale:** Désactivez l'IA de tous les villageois par défaut.
* **Exclusion par Profession:** Empêchez la désactivation de l'IA pour les villageois ayant des professions spécifiques (par exemple, les bibliothécaires).
* **Exclusion par Nom:** Empêchez la désactivation de l'IA pour les villageois ayant des noms personnalisés spécifiques (par exemple, les PNJ commerçants).
* **Exclusion par Proximité du Joueur:** Réactivez l'IA des villageois lorsqu'ils se trouvent à une certaine distance des joueurs (pour permettre les interactions).
* **Commandes:** Commandes pour recharger la configuration et activer/désactiver manuellement l'IA d'un villageois ciblé.
* **Permissions:** Système de permissions pour contrôler l'accès aux commandes et exclure des joueurs de la désactivation par proximité.

**Installation**

1.  Téléchargez le fichier `VillagerAIDisabler.jar`.
2.  Placez le fichier `.jar` dans le dossier `plugins/` de votre serveur Spigot/Paper.
3.  Redémarrez le serveur.
4.  Le plugin générera automatiquement un fichier de configuration (`config.yml`) dans le dossier `plugins/VillagerAIDisabler/`.
5.  Configurez le plugin selon vos besoins en modifiant le fichier `config.yml`.
6.  Rechargez le plugin ou le serveur pour appliquer les modifications.

**Configuration**

Le fichier `config.yml` permet de personnaliser le comportement du plugin.

Pour référence, voici un exemple de configuration 

```yaml
disable-by-default: true

excluded-professions:

librarian
excluded-names:

TraderNPC
distance-from-player-threshold: 20
```

* `disable-by-default`: (booléen) Si `true`, l'IA de tous les villageois est désactivée par défaut. Si `false`, l'IA est activée par défaut.
* `excluded-professions`: (liste de chaînes) Liste des professions des villageois pour lesquels l'IA ne doit pas être désactivée. Les noms des professions doivent être en minuscules (par exemple, `librarian`, `farmer`, `smith`).
* `excluded-names`: (liste de chaînes) Liste des noms personnalisés des villageois pour lesquels l'IA ne doit pas être désactivée.
* `distance-from-player-threshold`: (entier) Distance (en blocs) à partir de laquelle l'IA des villageois est réactivée lorsqu'un joueur se trouve à proximité.

**Commandes**

* `/villagerai reload`: Recharge la configuration du plugin. Permission: `villagerai.use`
* `/villagerai enable`: Active l'IA du villageois que le joueur regarde. Permission: `villagerai.use`
* `/villagerai disable`: Désactive l'IA du villageois que le joueur regarde. Permission: `villagerai.use`
* `/villagerai version`: Affiche la version du plugin. Permission: `villagerai.use`

**Permissions**

* `villagerai.use`: Permet d'utiliser les commandes du plugin. Par défaut: `op`
* `villagerai.ignore`: Les joueurs avec cette permission sont exclus de la désactivation de l'IA des villageois par proximité. Par défaut: `false`

**Auteur**

joff1507 & ChatGPT

**Version**

1.0

**Support**

Pour toute question ou problème, veuillez contacter l'auteur.
