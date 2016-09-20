package com.igdb.json;

import lombok.Data;

@Data
public class Screenshot implements GeneralJsonModel {
    private static final long serialVersionUID = 1L;

    private String cloudinaryId;
    private Integer width;
    private Integer height;
}
