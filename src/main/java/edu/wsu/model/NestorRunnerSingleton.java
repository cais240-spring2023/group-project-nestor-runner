package edu.wsu.model;

public class NestorRunnerSingleton {
    private static NestorRunnerImpl model;

    public static NestorRunnerImpl getInstance() {
        if(model == null) {
            model = new NestorRunnerImpl();
        }
        return model;
    }
}
