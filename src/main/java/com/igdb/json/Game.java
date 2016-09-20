package com.igdb.json;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Game extends GeneralIgdbJsonModel {
    private static final long serialVersionUID = 1L;

    private String summary;
    private String storyline;
    private Long collection;
    private Long hypes;
    private Long category;
    private List<Long> developers;
    private List<Long> publishers;
    private List<Long> keywords;
    private List<Long> themes;
    private List<Long> genres;
    private List<ReleaseDate> releaseDates;
    private List<AlternativeName> alternativeNames;
    private List<Screenshot> screenshots;
    private List<Video> videos;
    private Screenshot cover;
}
