package ru.cft.resource;

public class Resource {
    private final long id;
    public Resource() {
        id = ResourceIdGenerator.generateId();
    }

    public long getId() {
        return this.id;
    }

    private static class ResourceIdGenerator {
        private static long id = 0;
        public static long generateId() {
            return id++;
        }
    }
}
