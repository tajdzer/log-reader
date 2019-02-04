package com.emil.logreader.creator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileCreator {

    private static final int howManyLogs = 2;
//    private static final int howManyLogs = 1_000;
//    private static final int howManyLogs = 1_000_000;

    public static void main(String... args) {
        Path path = Paths.get("src/test/resources/test_files/2_events.log");
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {

//            {"id":"scsmbstgra", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495212}

            for (int i = 0; i < howManyLogs; i++) {
                String line = "{\"id\":\"" + "index_" + i + "\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":" + new Date().getTime() + "}" + System.lineSeparator();
                writer.write(line);
            }

            TimeUnit.SECONDS.sleep(1);

            for (int i = 0; i < howManyLogs; i++) {
                String line = "{\"id\":\"" + "index_" + i + "\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":" + new Date().getTime() + "}" + System.lineSeparator();
                writer.write(line);
            }

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
