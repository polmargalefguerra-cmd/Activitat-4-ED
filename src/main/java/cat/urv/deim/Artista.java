package cat.urv.deim;

import java.util.Objects;

public class Artista implements Comparable<Artista> {

    // ID artista
    private final String artistId;

    // nom artista
    private final String nom;

    public Artista(String artistId, String nom) {
        this.artistId = artistId;
        this.nom = nom;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public int compareTo(Artista altre) {
        return nom.compareTo(altre.nom);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Artista artista)) {
            return false;
        }

        return Objects.equals(artistId, artista.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "artistId='" + artistId + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
