package com.igdb.client;

import java.util.List;

import com.igdb.json.Game;
import com.igdb.json.Platform;

/**
 * @author Ldabul
 */
public interface IgdbClient {
    public static final String CONTEXT_SEPARATOR = "/";
    public static final String QUERY_SEPARATOR = "?";
    public static final String PARAM_SEPARATOR = "&";
    public static final String VALUE_SEPARATOR = ",";

    List<Game> getGames(GamesReq req);

    List<Platform> getPlatforms(PlatformsReq req);
}
