Burfin Thoma
Leonarduzzi Alexis

# INFO801 TP1 - Drainage d'une mine

## Introduction
Dans le cadre de ce TP, nous avons pour objectif de simuler le drainage d'une mine. Pour cela, nous allons utiliser un modèle de simulation multi-agents ou chacun de ces agents est un processus et ces dernier communique entre eux avec une espace de tuple en commun.
Nous avons décider de baser notre projet sur la "librarie" jSpace. Qui est une implémentations de Linda pour Java.

## Représentation des agents
Voici la liste des agents que nous avons décidé de représenter dans notre simulation:
- **Ventilateur**: Cet agent est responsable de la ventilation de la mine, et fait diminuer la concentration en monoxyde de carbone et de méthane dans la mine.
- **Pompe**: Cet agent est responsable de l'évacuation de l'eau de la mine. Elle ne peut s'activer que si le ventilateur n'est pas actif.
- **Capteur Gaz Bas**: Cet agent est responsable de la détection de gaz dans la mine. Il envoie un signal au ventilateur de s'arreter si la concentration en gaz est en dessous des seuils.
- **Capteur Gaz Haut**: Cet agent est responsable de la détection de gaz dans la mine. Il envoie un signal au ventilateur de s'arreter si la concentration en gaz est au dessus des seuils.
- **Capteur Eau Bas**: Cet agent est responsable de la détection de l'eau dans la mine. Il envoie un signal à la pompe de s'arreter si la concentration en eau est en dessous des seuils.
- **Capteur Eau Haut**: Cet agent est responsable de la détection de l'eau dans la mine. Il envoie un signal à la pompe de s'arreter si la concentration en eau est au dessus des seuils.
+ **Environement**: Ce dernier nous permet de simuler la réaliter, dans une vrai intégration celui-ci n'existe pas et les valeurs qu'il fournis proviendrait de capteurs réels.

### Ventilateur
````

````

### Pompe
````

````

### Capteur Gaz Bas
````
CapteurGazBas(seuil_CH4_bas, seuil_CO_bas) =
rd(ts, |niveau_CH4, string, ?n_ch4, float|)).
rd(ts, (|niveau_CO, string, ?n_co, float|)).
([((n_ch4 ≥ seuil_CH4_haut) ∧ (n_co ≥ seuil_CO_haut)) ∧ (rd(ts, (|activation_pompe, string|)))]
    out(ts, (|activation_ventilateur, string|)).
    CapteurGazBas(seuil_CH4_bas, seuil_CO_bas)
)
````

### Capteur Gaz Haut
````
CapteurGazHaut(seuil_CH4_haut, seuil_CO_haut) =
rd(ts, |niveau_CH4, string, ?n_ch4, float|)).
rd(ts, (|niveau_CO, string, ?n_co, float|)).
([((n_ch4 ≥ seuil_CH4_haut) ∨ (n_co ≥ seuil_CO_haut)) ∧ non (rd(ts, (|activation_ventilateur, string|)))]
    out(ts, (|activation_pompe, string|)).
    CapteurGazHaut(seuil_CH4_haut, seuil_CO_haut)
)
````

### Capteur Eau Bas
````
CapteurH2OBas(seuil_H2O_bas) =
rd(ts, (|niveau_H2O, string, ?n_h20, float|)).
([n_h20 ≤ seuil_H2O_bas ∧ (rd(ts, (|activation_pompe, string|)))]
    in(ts, (|activation_pompe, string|)).
    CapteurH2OBas(seuil_H2O_bas)
)
````

### Capteur Eau Haut
```
CapteurH2OHaut(seuil_H2O_haut) =
rd(ts, (|niveau_H2O, string, ?n_h20, float|)).
([n_h20 ≥ seuil_H2O_haut ∧ non (rd(ts, (|activation_pompe, string|)))]
    out(ts, (|activation_pompe, string|)).
    CapteurH2OHaut(seuil_H2O_haut)
)
```