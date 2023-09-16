package br.com.fiap.projetodbe.models;

public record Token(
    String token,
    String type,
    String prefix,
    Long id
) {}
