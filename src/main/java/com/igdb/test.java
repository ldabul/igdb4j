package com.igdb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igdb.client.GamesReq;
import com.igdb.client.IgdbClient;
import com.igdb.client.PlatformsReq;
import com.igdb.config.IgdbConfig;
import com.igdb.json.Game;
import com.igdb.json.Platform;

public class test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(IgdbConfig.class);
        IgdbClient client = ctx.getBean(IgdbClient.class);
        ObjectMapper om = ctx.getBean(ObjectMapper.class);

        List<Platform> platforms = client.getPlatforms(PlatformsReq.of());
        System.out.println(platforms.getClass());
        System.out.println(platforms.get(0)
                        .getClass());
        System.out.println(platforms.size());
        System.out.println(platforms);
        Platform platform = platforms.stream()
                        .filter(p -> 121 == p.getId())
                        .findFirst()
                        .orElse(null);

        GamesReq req = GamesReq.of()
                        .ids(platform.getGames());
        //                        .filter(FilterItem.eq("id",
        //                                              7346));
        System.out.println(req.toPath());
        List<Game> games = client.getGames(req);
        System.out.println(games.getClass());
        System.out.println(games.get(0)
                        .getClass());
        System.out.println(games.size());
        System.out.println(games);

        try {
            Game g = om.readValue(new File("C:/projects2/igdb4j/src/main/resources/games.json"),
                                  Game.class);
            System.out.println(g);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
