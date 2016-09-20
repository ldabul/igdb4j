package com.igdb.json;

import lombok.Data;

@Data
public class GeneralIgdbJsonModel implements GeneralJsonModel {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String slug;
    private String url;
    private Long createdAt;
    private Long updatedAt;
}
