import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day2 {

  private static final String RED = "red";
  private static final String BLUE = "blue";
  private static final String GREEN = "green";


  public static void main(String[] args) throws IOException, URISyntaxException {
    List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(Day2.class.getClassLoader().getResource("input_day2.txt")).toURI()));
    System.out.printf("Game 1 result: %s\n", game1(lines));
    System.out.printf("Game 2 result: %s\n", game2(lines));
  }
  private static int game1(List<String> lines) {
    List<Game> games = lines.stream().map(Day2::parseGame).toList();
    return games.stream()
            .filter(game -> game.subGames().stream()
                    .allMatch(subGame -> subGame.red() <= 12 && subGame.green() <= 13 && subGame.blue() <= 14))
            .mapToInt(Game::game)
            .sum();
  }

  private static int game2(List<String> lines) {
    List<Game> games = lines.stream().map(Day2::parseGame).toList();
    return games.stream()
            .mapToInt(game -> {
              int red = game.subGames().stream().mapToInt(SubGame::red).max().orElse(1);
              int green = game.subGames().stream().mapToInt(SubGame::green).max().orElse(1);
              int blue = game.subGames().stream().mapToInt(SubGame::blue).max().orElse(1);
              return red * green * blue;
            })
            .sum();
  }

  private static Game parseGame(String line) {
    String[] game = line.split(":");
    int gameNumber = Integer.parseInt(game[0].replace("Game ", ""));
    List<SubGame> subGames = Arrays.stream(game[1].split(";")).map(Day2::parseSubGame).collect(Collectors.toList());
    return new Game(gameNumber, subGames);
  }

  private static SubGame parseSubGame(String line) {
    String[] cubes = line.split(",");
    int red = getColorCount(cubes, RED);
    int blue = getColorCount(cubes, BLUE);
    int green = getColorCount(cubes, GREEN);
    return new SubGame(red, blue, green);
  }

  private static int getColorCount(String[] cubes, String color) {
    return Arrays.stream(cubes)
            .filter(cube -> cube.trim().split(" ")[1].equals(color))
            .mapToInt(cube -> Integer.parseInt(cube.trim().split(" ")[0]))
            .sum();
  }

  record Game(int game, List<SubGame> subGames) {}

  record SubGame(int red, int blue, int green){}
}
