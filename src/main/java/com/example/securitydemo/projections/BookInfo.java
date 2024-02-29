package com.example.securitydemo.projections;

/**
 * A Projection for the {@link com.example.securitydemo.model.Book} entity
 */
public interface BookInfo {
    Long getId();

    String getName();

    String getRole();
}