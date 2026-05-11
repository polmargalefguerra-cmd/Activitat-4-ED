package cat.urv.deim;

import java.util.Objects;
import cat.urv.deim.exceptions.ElementNoTrobat;

public class Usuari implements Comparable<Usuari> {

    // ID de last.fm (sha1)
    private final String userId;

    // dades perfil
    private final String sexe;
    private final String pais;
    private int edat;

    // artistId -> reproduccions
    private TADHashMap<String, Integer> escoltes;

    public Usuari(String userId, String sexe, String pais, int edat) {
        this.userId = userId;
        this.sexe = sexe;
        this.pais = pais;
        this.edat = edat;

        // pots canviar la mida inicial
        this.escoltes = new HashingIndirecte<>(100);
    }

    public String getUserId() {
        return userId;
    }

    public String getSexe() {
        return sexe;
    }

    public String getPais() {
        return pais;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public TADHashMap<String, Integer> getEscoltes() {
        return escoltes;
    }

    /**
     * Afegeix o actualitza les reproduccions d'un artista
     */
    public void afegirEscolta(String artistId, int reproduccions) {

        try {
            int actuals = escoltes.consultar(artistId);
            escoltes.inserir(artistId, actuals + reproduccions);

        } catch (ElementNoTrobat e) {

            try {
                escoltes.inserir(artistId, reproduccions);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Comprova si l'usuari ja escolta un artista
     */
    public boolean teEscoltat(String artistId) {

        try {
            escoltes.consultar(artistId);
            return true;

        } catch (ElementNoTrobat e) {
            return false;
        }
    }

    /**
     * Retorna les reproduccions d'un artista
     */
    public int getReproduccions(String artistId) {

        try {
            return escoltes.consultar(artistId);

        } catch (ElementNoTrobat e) {
            return 0;
        }
    }

    /**
     * Retorna l'artista més escoltat
     */
    public String getArtistaPreferit() {

        TADLlista<String> claus = escoltes.obtenirClaus();

        String millorArtista = null;
        int maxEscoltes = -1;

        for (int i = 0; i < claus.numElem(); i++) {

            try {

                String artistId = claus.consultar(i);
                int reproduccions = escoltes.consultar(artistId);

                if (reproduccions > maxEscoltes) {
                    maxEscoltes = reproduccions;
                    millorArtista = artistId;
                }

            } catch (Exception e) {
            }
        }

        return millorArtista;
    }

    @Override
    public int compareTo(Usuari altre) {
        return userId.compareTo(altre.userId);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Usuari usuari)) {
            return false;
        }

        return Objects.equals(userId, usuari.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {

        return "Usuari{" +
                "userId='" + userId + '\'' +
                ", sexe='" + sexe + '\'' +
                ", pais='" + pais + '\'' +
                ", edat=" + edat +
                '}';
    }
}