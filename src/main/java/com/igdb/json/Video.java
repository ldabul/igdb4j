package com.igdb.json;

import lombok.Data;

@Data
public class Video implements GeneralJsonModel {
    private static final long serialVersionUID = 1L;

    private String name;
    private String videoId;
}
