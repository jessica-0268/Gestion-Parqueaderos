package co.edu.autonoma.gestionParqueaderos.util;

import java.util.Locale;

public final class PlacaUtil {

    private PlacaUtil() {
    }

    public static String normalizar(String placa) {
        return placa == null
                ? null
                : placa.trim().toUpperCase(Locale.ROOT);
    }
}