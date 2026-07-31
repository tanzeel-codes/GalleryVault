package com.tanzeel.galleryvault.download;

public class ProcessResult {

    private final int exitCode;
    private final String output;

    public ProcessResult(int exitCode, String output) {
        this.exitCode = exitCode;
        this.output = output;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getOutput() {
        return output;
    }
}