package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class Recomanador {
    private TADHashMap<String, Usuari> usuaris;
    private TADHashMap<String, Artista> artistes;

    public Recomanador(TADHashMap<String, Usuari> usuaris, TADHashMap<String, Artista> artistes) {
        this.usuaris = usuaris;
        this.artistes = artistes;
    }

    TADLlista<String> recomanacio(String userId, int numMinEscoltes, boolean sexe, int rangEdat, boolean pais,
            boolean preferits) {
        // retornara maxim 10 recomanacions
        TADLlista<String> resultat = new LlistaDinamica<>(10);

        try {
            // usuari a recomanar
            Usuari usuariObjectiu = usuaris.consultar(userId);

            // per controlar que no s'afegeixin artistes duplicats
            TADHashMap<String, Boolean> artistesAfegits = new HashingIndirecte<>(1000);

            // tots els usuaris
            TADLlista<String> idsUsuaris = usuaris.obtenirClaus();

            for (int i = 0; i < idsUsuaris.numElem(); i++) {
                String altreUserId = idsUsuaris.consultar(i);

                // no comparar amb si mateix
                if (altreUserId.equals(userId)) {
                    continue;
                }

                Usuari altreUsuari = usuaris.consultar(altreUserId);
                boolean similar = true;

                // filtre sexe
                if (sexe) {
                    if (!usuariObjectiu.getSexe().equalsIgnoreCase(altreUsuari.getSexe())) {
                        similar = false;
                    }
                }

                // filtre pais
                if (pais) {
                    if (!usuariObjectiu.getPais().equalsIgnoreCase(altreUsuari.getPais())) {
                        similar = false;
                    }
                }

                // filtre edat
                if (rangEdat != -1) {
                    int edatObjectiu = usuariObjectiu.getEdat();
                    int edatAltre = altreUsuari.getEdat();

                    // si algun no té edat, no es pot comparar
                    if (edatObjectiu == -1 || edatAltre == -1) {
                        similar = false;
                    } else {
                        int min = edatObjectiu - rangEdat;
                        int max = edatObjectiu + rangEdat;

                        if (edatAltre < min || edatAltre > max) {
                            similar = false;
                        }
                    }
                }

                // filtre de preferits
                if (preferits) {
                    String preferit1 = usuariObjectiu.getArtistaPreferit();
                    String preferit2 = altreUsuari.getArtistaPreferit();

                    if (preferit1 == null || preferit2 == null || !preferit1.equals(preferit2)) {
                        similar = false;
                    }
                }

                // Si es similar, recomanar
                if (similar) {
                    TADLlista<String> artistesAltreUsuari = altreUsuari.getEscoltes().obtenirClaus();

                    for (int j = 0; j < artistesAltreUsuari.numElem(); j++) {
                        String artistId = artistesAltreUsuari.consultar(j);
                        int reproduccions = altreUsuari.getReproduccions(artistId);

                        // mínim escoltes
                        if (reproduccions < numMinEscoltes) {
                            continue;
                        }

                        // no recomanar si ja l'escolta
                        if (usuariObjectiu.teEscoltat(artistId)) {
                            continue;
                        }
                        // evitar duplicats
                        try {
                            artistesAfegits.consultar(artistId);
                        } catch (ElementNoTrobat e) {
                            artistesAfegits.inserir(artistId, true);
                            try {
                                Artista artista = artistes.consultar(artistId);
                                // retornara maxim 10 recomanacions
                                if (resultat.numElem() >= 10) {
                                    return resultat;
                                }
                                resultat.inserir(artista.getNom(), resultat.numElem());
                            } catch (ElementNoTrobat ex) {
                                // si artista no existeix s'ignora
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultat;
    }
}
