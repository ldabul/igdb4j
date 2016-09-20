package com.igdb.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;

import com.igdb.json.Game;
import com.igdb.json.Platform;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IgdbClientImpl extends AbstractJsonClient implements IgdbClient {
    @Resource
    private Environment env;

    @Override
    protected boolean isOffline() {
        return env.getProperty("igdb.offline",
                               Boolean.class,
                               Boolean.FALSE);
    }

    @Override
    protected HttpHeaders createHeaders() {
        Map<String, String> h = new HashMap<>();
        h.put("X-Mashape-Key",
              env.getProperty("igdb.key"));
        h.put("Accept",
              MimeTypeUtils.APPLICATION_JSON_VALUE);
        return createHeaders(h);
    }

    private String getApiURL(String context, String params) {
        return StringUtils.join(Arrays.asList(env.getProperty("igdb.url"),
                                              context,
                                              params),
                                CONTEXT_SEPARATOR);
    }

    @Override
    public List<Game> getGames(GamesReq req) {
        return super.getList(getApiURL("games",
                                       req.toPath()),
                             createRequest(),
                             Game.class);
    }

    @Override
    public List<Platform> getPlatforms(PlatformsReq req) {
        return super.getList(getApiURL("platforms",
                                       req.toPath()),
                             createRequest(),
                             Platform.class);
    }
}
