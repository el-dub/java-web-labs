package com.kpi;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        String dir = inputDirName();

        ExecutorService pool = Executors.newCachedThreadPool();
        FileChanger changer = new FileChanger(Paths.get(dir), pool);
        Future<List<String>> res = pool.submit(changer);

        try {
            System.out.println(res.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    private static String inputDirName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Directory -> ");
        return sc.next();
    }
}
