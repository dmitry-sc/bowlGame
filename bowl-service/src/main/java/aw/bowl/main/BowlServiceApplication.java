package aw.bowl.main;

import aw.bowl.main.model.Game;
import aw.bowl.main.model.Player;
import aw.bowl.main.services.BowlTransportService;
import aw.bowl.main.services.GameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableCircuitBreaker
public class BowlServiceApplication {
    @Autowired
    private BowlTransportService bowlTransportService;
    @Autowired
    private GameHelper gameHelper;

    public static void main(String[] args) {
        SpringApplication.run(BowlServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner runner() {

        return strings -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to Bowl game! Plz remember the main goal, input hacks not the point! ;)");
            //TODO: check games list and iterate
            //TODO: can restore connection of not completed games
            System.out.println("Starting a new game...");
            Game game = bowlTransportService.startGame();
            System.out.println("How many players will play (max is " + gameHelper.getNames().length + ") ?");
            int playersCount = scanner.nextInt();
            if (playersCount > gameHelper.getNames().length) playersCount = gameHelper.getNames().length;
            gameHelper.initiatePlayers(playersCount, game);
            //let's rock
            int round = 0;
            do {
                Integer pins = gameHelper.getPins(game.getType());
                for (Player player : gameHelper.getPlayers()) {
                    System.out.println(" > #" + player.getPlayerId() + ", " + player.getName() + " rolls: " + pins);
                    if (bowlTransportService.roll(game.getGameId(), game.getType(), player.getPlayerId(), pins)) {
                        pins = gameHelper.getPins(game.getType() - pins);
                        System.out.println(" > " + player.getName() + " rolls again, now it`s: " + pins);
                        bowlTransportService.roll(game.getGameId(), game.getType(), player.getPlayerId(), pins);
                    }
                }
                System.out.println("\n>>> Scoring for round " + ++round + " <<<");
                for (Player player : gameHelper.getPlayers()) {
                    System.out.println(player.getName() + ":" + bowlTransportService.getPlayerScore(game.getGameId(), player.getPlayerId()));
                }
                System.out.println();
                game.setActive(!bowlTransportService.isGameEnded(game.getGameId()));
            } while (game.isActive());
            Player winner = gameHelper.getWinnerPlayer(game.getGameId());
            System.out.println("\n>>> Let`s congratulate our winner " + winner.getName() + " <<<");
            System.out.println("Throws of a player: " + winner.getThrowz() + "\n");
            System.out.println("Game #" + game.getGameId() + " finished");
        };
    }
}
