package com.problemsolving.geektrust.familytree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class MiscUtils {
    public static List<String> getInput(String inputFilePath) throws IOException {
        return Files.readAllLines(Paths.get(inputFilePath));
    }
}
