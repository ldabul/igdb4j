package com.igdb.json;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Genre extends GeneralIgdbJsonModel {
    private static final long serialVersionUID = 1L;

    private List<Long> games;

}
