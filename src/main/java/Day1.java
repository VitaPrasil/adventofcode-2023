import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day1 {

  private static final List<Number> NUMBERS = List.of(
          new Number("one1one","one"),
          new Number("two2two","two"),
          new Number("three3three","three"),
          new Number("four4four","four"),
          new Number("five5five","five"),
          new Number("six6six","six"),
          new Number("seven7seven","seven"),
          new Number("eight8eight","eight"),
          new Number("nine9nine","nine")
  );

  public static void main(String[] args) throws IOException, URISyntaxException {
    List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(Day1.class.getClassLoader().getResource("input_day1.txt")).toURI()));
    //System.out.printf("Game 1 result: %s\n", game1(lines));
    System.out.printf("Game 2 result: %s\n", game2(lines));
  }
  private static int game1(List<String> lines) {
    return lines.stream()
            .mapToInt(Day1::findNumber).sum();
  }

  private static int findNumber(String line) {
    String reducedLine = line.chars()
            .filter(ch -> ch >= '0' && ch <= '9')
            .mapToObj(Character::toString)
            .collect(Collectors.joining());

    return Integer.parseInt(Character.toString(reducedLine.charAt(0)) + Character.toString(reducedLine.charAt(reducedLine.length() - 1)));
  }

  private static int game2(List<String> lines) {
    return lines.stream().mapToInt(line -> findNumber(replaceStrNumbers(line))).sum();
  }

  private static String replaceStrNumbers(String line) {
    Number firstNumberToReplace = null;
    Number lastNumberToReplace = null;
    int firstNumberIndex = Integer.MAX_VALUE;
    int lastNumberIndex = Integer.MIN_VALUE;
    for(Number number: NUMBERS) {
      if (line.contains(number.numberStr()) && line.indexOf(number.numberStr()) < firstNumberIndex) {
        firstNumberToReplace = number;
        firstNumberIndex = line.indexOf(number.numberStr());
      }
      if (line.contains(number.numberStr()) && line.lastIndexOf(number.numberStr()) > lastNumberIndex) {
        lastNumberToReplace = number;
        lastNumberIndex = line.lastIndexOf(number.numberStr());
      }
    }

    if (firstNumberToReplace != null) {
      line = line.replace(firstNumberToReplace.numberStr(), firstNumberToReplace.number());
    }

    if (lastNumberToReplace != null) {

      line = line.substring(0, line.lastIndexOf(lastNumberToReplace.numberStr()) - 1) +
              line.substring(line.lastIndexOf(lastNumberToReplace.numberStr())).replace(lastNumberToReplace.numberStr(), lastNumberToReplace.number());
    }

    return line;
  }

  record Number(String number, String numberStr){}
}
