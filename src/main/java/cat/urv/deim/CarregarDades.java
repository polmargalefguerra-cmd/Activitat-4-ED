package cat.urv.deim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class CarregarDades {

    private TADHashMap<String, Usuari> usuaris;

    private TADHashMap<String, Artista> artistes;

    public CarregarDades(int midaUsuaris, int midaArtistes) {
        usuaris = new HashingIndirecte<>(midaUsuaris);
        artistes = new HashingIndirecte<>(midaArtistes);
    }

    public TADHashMap<String, Usuari> getUsuaris() {
        return usuaris;
    }

    public TADHashMap<String, Artista> getArtistes() {
        return artistes;
    }

    /*
     * Carregar fitxer: usersha1-profile.tsv
     *
     * Format:
     * userId \t gender \t age \t country \t signup
     */
    public void carregarPerfils(String fitxer) {
        try (BufferedReader br = new BufferedReader(new FileReader(fitxer))) {
            String linia;

            while ((linia = br.readLine()) != null) {

                String[] parts = linia.split("\t");

                // per si de cas format incorrecte
                if (parts.length < 5) {
                    continue;
                }

                String userId = parts[0];

                String sexe = parts[1];
                if (sexe.isEmpty()) {
                    sexe = "desconegut";
                }

                // Es -1 si edat desconeguda
                int edat = -1;

                if (!parts[2].isEmpty()) {

                    try {
                        edat = Integer.parseInt(parts[2]);

                    } catch (NumberFormatException e) {
                        // Es -1 si edat desconeguda
                        edat = -1;
                    }
                }

                String pais = parts[3];

                if (pais.isEmpty()) {
                    pais = "desconegut";
                }

                Usuari usuari = new Usuari(userId, sexe, pais, edat);

                usuaris.inserir(userId, usuari);
            }

            System.out.println("Perfils carregats correctament.");

        } catch (IOException e) {
            System.out.println("Error carregant perfils.");
        }
    }

    /*
     * Carregar fitxer: usersha1-artmbid-artname-plays.tsv
     *
     * Format:
     * userId \t artistId \t artistName \t plays
     */
    public void carregarEscoltes(String fitxer) {
        try (BufferedReader br = new BufferedReader(new FileReader(fitxer))) {

            String linia;

            while ((linia = br.readLine()) != null) {

                String[] parts = linia.split("\t");

                // per si de cas format incorrecte
                if (parts.length < 4) {
                    continue;
                }

                String userId = parts[0];

                String artistId = parts[1];

                // alguns artistes no tenen MBID, llavors descarto la linea per controlar
                // possibles errors
                if (artistId == null || artistId.isBlank()) {
                    continue; // descartar linea
                }

                String nomArtista = parts[2];

                int reproduccions = 0;
                try {
                    reproduccions = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    reproduccions = 0;
                }

                // obtenir usuari
                Usuari usuari;

                try {
                    usuari = usuaris.consultar(userId);
                } catch (ElementNoTrobat e) {
                    // si no existeix es crea igual
                    usuari = new Usuari(userId, "desconegut", "desconegut", -1);
                    usuaris.inserir(userId, usuari);

                }

                // afegir artista si no existeix
                try {
                    artistes.consultar(artistId);
                } catch (ElementNoTrobat e) {
                    Artista artista = new Artista(artistId, nomArtista);
                    artistes.inserir(artistId, artista);
                }

                // afegir escolta a l'usuari
                usuari.afegirEscolta(artistId, reproduccions);
            }

            System.out.println("Escoltes carregades correctament.");

        } catch (IOException e) {
            System.out.println("Error carregant escoltes.");
        }
    }
}
