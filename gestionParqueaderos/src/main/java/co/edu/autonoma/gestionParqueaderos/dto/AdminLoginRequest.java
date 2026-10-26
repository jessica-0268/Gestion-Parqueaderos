package co.edu.autonoma.gestionParqueaderos.dto;

public record AdminLoginRequest(
        String usuario,
        String contrasena
) {}