package edu.wsu.model;

public class NestorRunnerSingleton {
    private static NestorRunner nestorRunner;

    public static NestorRunner getInstance() {
        if (nestorRunner == null) {
            nestorRunner = new NestorRunner();
        }
        return nestorRunner;
    }
}
