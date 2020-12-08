package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day1CSCS {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("inputs/2020/1.txt"));
        for (String line : lines) {
            int i = Integer.parseInt(line);
            // TODO your code here
        }
        System.out.println("SOLUTION");
    }
}
