package com.kpi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FileChanger implements Callable<List<String>> {
    private Path path;
    private ExecutorService pool;

    public FileChanger(Path path, ExecutorService pool) {
        this.path = path;
        this.pool = pool;
    }

    private void swapFirstAndLast(Path file) {
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8)
                    .stream()
                    .map(line -> {
                        String[] words = line.split(" ");
                        String firstWord = words[0];
                        words[0] = words[words.length - 1];
                        words[words.length - 1] = firstWord;
                        return Arrays.stream(words).collect(Collectors.joining(" "));
                    })
                    .collect(Collectors.toList());
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> call() throws Exception {
        List<String> fileNames = new ArrayList<>();
        try {
            List<Path> files = Files.list(path).collect(Collectors.toList());
            ArrayList<Future<List<String>>> result = new ArrayList<>();

            for (Path f : files) {
                if (Files.isDirectory(f)) {
                    FileChanger changer = new FileChanger(f, pool);
                    Future<List<String>> rez = pool.submit(changer);
                    result.add(rez);
                } else {
                    if (f.toString().endsWith(".txt")) {
                        swapFirstAndLast(f);
                        fileNames.add(f.getFileName().toString());
                    }
                }
            }
            for (Future<List<String>> rez : result) {
                fileNames.addAll(rez.get());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return fileNames;
    }
}
