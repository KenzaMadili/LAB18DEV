# 📱 Lab 18 — ViewModel et LiveData en Android

**Cours** : Programmation Mobile — Android avec Java

---

## 🎯 Objectifs

- Comprendre pourquoi une variable classique est perdue à chaque rotation d'écran
- Voir concrètement la limite de `onSaveInstanceState()`
- Maîtriser **ViewModel** (survit à la destruction/re-création de l'Activity)
- Maîtriser **LiveData** (lifecycle-aware : met à jour l'UI seulement quand l'Activity est active)
- Découvrir les concepts internes : `LifecycleOwner`, `Observer`, `ViewModelStore`, `MutableLiveData` vs `LiveData`, `setValue` vs `postValue`
- Tester des scénarios réels : rotation multiple, changement de thème, kill processus, thread background

---

## 🧠 Concepts Clés

### Le problème classique
```java
// ❌ Variable perdue à chaque rotation
public class MainActivity extends AppCompatActivity {
    private int counter = 0; // remis à 0 à chaque rotation !
}
```

### La solution : ViewModel
```java
// ✅ Le ViewModel survit aux rotations
public class CounterViewModel extends ViewModel {
    private MutableLiveData<Integer> count = new MutableLiveData<>(0);

    public LiveData<Integer> getCount() { return count; }

    public void increment() { count.setValue(count.getValue() + 1); }
}
```

### Observer avec LiveData
```java
// L'UI s'abonne et se met à jour automatiquement
viewModel.getCount().observe(this, value -> {
    tvCount.setText(String.valueOf(value));
});
```

### setValue vs postValue
| Méthode | Thread | Usage |
|---|---|---|
| `setValue()` | Main thread uniquement | Depuis l'UI |
| `postValue()` | N'importe quel thread | Depuis un background thread |

---

## 🏗️ Structure du Projet

```
app/
└── ui/
    ├── CounterActivity.java       # Version AVEC ViewModel + LiveData
    ├── CounterActivityNoVM.java   # Version SANS ViewModel (le problème)
    └── CounterViewModel.java      # ViewModel du compteur
```

---

## ✨ Fonctionnalités

| Bouton | Action |
|---|---|
| ＋ Incrémenter | Incrémente le compteur |
| − Décrémenter | Décrémente le compteur |
| RESET | Remet le compteur à zéro |
| ⚡ THREAD +1 | Incrémente depuis un thread secondaire (`postValue`) |

---

## 🔬 Scénarios de Test



| Scénario | Comportement attendu |
|---|---|
| Rotation de l'écran | Le compteur conserve sa valeur |
| Retour arrière + réouverture | Réinitialisation (pas de persistance) |
| Thread +1 rapide x10 | Pas de crash, UI reste fluide |
| Kill processus (`adb shell kill`) | Valeur perdue (limite du ViewModel) |

---

## 🛠️ Dépendances

```kotlin
// build.gradle.kts
val lifecycle_version = "2.8.7"

implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
```

---

## 📚 Ce que ce Lab enseigne

| Ancienne approche | Approche Jetpack |
|---|---|
| Variable dans l'Activity | `ViewModel` |
| `onSaveInstanceState()` | `LiveData` + `observe()` |
| Mise à jour manuelle de l'UI | Observation automatique |
| Couplage fort Activity/données | Séparation des responsabilités |

---

## 📝 Notes Pédagogiques

> **ViewModel** ne remplace pas totalement la gestion du *process death*.
> Pour ce cas, il existe le module **Saved State for ViewModel** (`SavedStateHandle`).

---

**Cours** : Programmation Mobile — Android avec Java  
**Niveau** : Lab 18 / Intermédiaire
