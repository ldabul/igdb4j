package com.igdb.json;

import lombok.Data;

@Data
public class ReleaseDate implements GeneralJsonModel {
    private static final long serialVersionUID = 1L;

    private Long category;
    private Long platform;
    private Long date;
    private Long region;
}
