package com.igdb.json;

import lombok.Data;

@Data
public class AlternativeName implements GeneralJsonModel {
    private static final long serialVersionUID = 1L;

    private String name;
}
