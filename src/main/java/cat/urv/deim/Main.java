package cat.urv.deim;

import cat.urv.deim.exceptions.LlistaBuida;

public class Main {

    public static void main(String[] args) {
        // carregador de dades
        CarregarDades carregador = new CarregarDades(400000, 300000);

        System.out.println("=================================");
        System.out.println("CARREGANT PERFILS...");
        System.out.println("=================================");

        carregador.carregarPerfils("usersha1-profile.tsv");

        System.out.println();

        System.out.println("=================================");
        System.out.println("CARREGANT ESCOLTES...");
        System.out.println("=================================");

        carregador.carregarEscoltes("usersha-artname49271.tsv");

        System.out.println();

        System.out.println("=================================");
        System.out.println("DADES CARREGADES CORRECTAMENT");
        System.out.println("=================================");

        // Crear recomanador
        Recomanador recomanador = new Recomanador(carregador.getUsuaris(), carregador.getArtistes());

        /*
         * IMPORTANT:
         *
         * Canvia aquest userId per un que existeixi
         * al dataset.
         */
        String userId = "00000c289a1829a808ac09c00daf10bc3c4e223b";

        System.out.println();
        System.out.println("=================================");
        System.out.println("RECOMANACIO PER PAIS");
        System.out.println("=================================");

        mostrarRecomanacions(
                recomanador.recomanacio(
                        userId,
                        100,
                        false,
                        -1,
                        true,
                        false));

        System.out.println();
        System.out.println("=================================");
        System.out.println("RECOMANACIO PER EDAT");
        System.out.println("=================================");

        mostrarRecomanacions(
                recomanador.recomanacio(
                        userId,
                        100,
                        false,
                        5,
                        false,
                        false));

        System.out.println();
        System.out.println("=================================");
        System.out.println("RECOMANACIO PER PREFERITS");
        System.out.println("=================================");

        mostrarRecomanacions(
                recomanador.recomanacio(
                        userId,
                        100,
                        false,
                        -1,
                        false,
                        true));

        System.out.println();
        System.out.println("=================================");
        System.out.println("RECOMANACIO PER EDAT + PAIS");
        System.out.println("=================================");

        mostrarRecomanacions(
                recomanador.recomanacio(
                        userId,
                        100,
                        false,
                        5,
                        true,
                        false));

        System.out.println();
        System.out.println("=================================");
        System.out.println("RECOMANACIO COMPLETA");
        System.out.println("=================================");

        mostrarRecomanacions(recomanador.recomanacio(userId, 100, true, 5, true, true));
    }

    // mostrar recomanacions
    public static void mostrarRecomanacions(TADLlista<String> recomanacions) {
        if (recomanacions == null || recomanacions.numElem() == 0) {

            System.out.println("No hi ha recomanacions.");
            return;
        }

        for (int i = 0; i < recomanacions.numElem(); i++) {
            try {
                System.out.println("- " + recomanacions.consultar(i));

            } catch (LlistaBuida e) {
            }
        }
    }
}
