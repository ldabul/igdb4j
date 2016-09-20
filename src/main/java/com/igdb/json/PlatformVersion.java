package com.igdb.json;

import lombok.Data;

@Data
public class PlatformVersion implements GeneralJsonModel {
    private static final long serialVersionUID = 1L;

    private String name;
    private String slug;
    private String url;
}
