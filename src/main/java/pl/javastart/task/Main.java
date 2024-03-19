package pl.javastart.task;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        LocalDateTime dateTime = DateTimeUtils.parseInputDate(input);

        DateTimeUtils.displayTimeInDifferentTimeZones(dateTime);

    }
}
