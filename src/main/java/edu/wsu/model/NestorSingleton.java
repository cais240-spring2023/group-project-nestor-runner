package edu.wsu.model;

public class NestorSingleton {
    private static Nestor model;

    public static Nestor getInstance() {
        if(model == null) {
            model = new Nestor(400);
        }
        return model;
    }
}
