package pl.javastart.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateTimeUtils {
    public static void displayTimeInDifferentTimeZones(LocalDateTime dateTime) {
        DateTimeFormatter dtfWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (dateTime != null && !dateTime.equals(LocalDateTime.MIN)) {
            ZonedDateTime zonedLocal = dateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime zonedUtc = zonedLocal.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime zonedLondon = zonedLocal.withZoneSameInstant(ZoneId.of("Europe/London"));
            ZonedDateTime zonedLosAngeles = zonedLocal.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
            ZonedDateTime zonedSydney = zonedLocal.withZoneSameInstant(ZoneId.of("Australia/Sydney"));

            System.out.println("Czas lokalny: " + dtfWithTime.format(zonedLocal));
            System.out.println("UTC: " + dtfWithTime.format(zonedUtc));
            System.out.println("Londyn: " + dtfWithTime.format(zonedLondon));
            System.out.println("Los Angeles: " + dtfWithTime.format(zonedLosAngeles));
            System.out.println("Sydney: " + dtfWithTime.format(zonedSydney));
        } else {
            ZonedDateTime zonedLocal = LocalDateTime.now().atZone(ZoneId.systemDefault());
            System.out.println("Czas lokalny: " + dtfWithTime.format(zonedLocal));
        }

    }

    public static LocalDateTime parseInputDate(String input) {
        // Sprawdzamy, czy wejściowy format pasuje do wzorca zależnego od czasu
        if (input.matches("t([+-]\\d+[yMdhms])+")) {
            return adjustDateTime(input);
        }

        // W przeciwnym razie, korzystamy z oryginalnej logiki parsowania daty
        List<DateTimeFormatter> formatters = new ArrayList<>();
        formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        formatters.add(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        LocalDateTime dateTime = null;
        for (DateTimeFormatter formatter : formatters) {
            try {
                if (input.contains(" ")) {
                    return LocalDateTime.parse(input, formatter);
                }
                if (input.contains("-")) {
                    return LocalDate.parse(input, formatter).atStartOfDay();
                }
            } catch (Exception ignored) {
                // Ignorujemy wyjątek, próbujemy kolejny formatter
            }
        }
        return dateTime;
    }

    private static LocalDateTime adjustDateTime(String input) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        String[] parts = input.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        for (int i = 1; i < parts.length; i += 2) {
            int value = Integer.parseInt(parts[i]);
            switch (parts[i + 1]) {
                case "y":
                    year += value;
                    break;
                case "M":
                    month += value;
                    break;
                case "d":
                    day += value;
                    break;
                case "h":
                    hour += value;
                    break;
                case "m":
                    minute += value;
                    break;
                case "s":
                    second += value;
                    break;
                default:
                    throw new IllegalArgumentException("Nieznany format czasu: " + parts[i + 1]);
            }
        }

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
