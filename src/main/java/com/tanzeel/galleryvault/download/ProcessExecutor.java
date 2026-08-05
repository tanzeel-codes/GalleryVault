package com.tanzeel.galleryvault.download;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class ProcessExecutor {

    public ProcessResult execute(List<String> command, ProcessOutputListener listener) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(command);           //Create the command (can throw IOException)

        builder.redirectErrorStream(true);

        Process process = builder.start();                                     //Runs the command (can throw InterruptedException)

        String output = readOutput(process.getInputStream(), listener);

        int exitCode = process.waitFor();                                           // Wait for the command to finish and return its "status"

        return new ProcessResult(exitCode, output);

    }

    private String readOutput(InputStream stream, ProcessOutputListener listener) throws IOException {

        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;

            while((line = reader.readLine()) != null) {

                if(listener != null) {
                    listener.onOutput(line);
                }

                output.append(line)
                        .append(System.lineSeparator());

            }
        }

        return output.toString();
    }


}
