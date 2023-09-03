package common;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.System.lineSeparator;

@Slf4j
public class HistoryHandler {

    public void writeHistoryToFile(Calculator calculator, String result, String timeZone) {
        var now = ZonedDateTime.now(ZoneId.of(timeZone));
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (FileWriter writer = new FileWriter("calculation_history.txt", true)) {
            writer.write(String.format("%s, %s, result: %s %n", now.format(formatter), calculator.toString(), result));
        } catch (IOException e) {
            log.error("An error occurred while writing to the file", e);
        }
    }

    public String readHistoryFromFile() {
        var sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("calculation_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(lineSeparator());
            }
        } catch (IOException e) {
            log.error("An error occurred while reading from the file", e);
        }
        return sb.toString();
    }
}
