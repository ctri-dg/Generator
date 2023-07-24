package org.ctridg.Models;

public class Association {
    private String name;
    private String from;
    private String to;
    private String fromMultiplicity;
    private String toMultiplicity;

    public Association(String name, String from, String to, String fromMultiplicity, String toMultiplicity) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.fromMultiplicity = fromMultiplicity;
        this.toMultiplicity = toMultiplicity;
    }

    public Association() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromMultiplicity() {
        return fromMultiplicity;
    }

    public void setFromMultiplicity(String fromMultiplicity) {
        this.fromMultiplicity = fromMultiplicity;
    }

    public String getToMultiplicity() {
        return toMultiplicity;
    }

    public void setToMultiplicity(String toMultiplicity) {
        this.toMultiplicity = toMultiplicity;
    }

    @Override
    public String toString() {
        return "Association{" +
                "name='" + name + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", fromMultiplicity='" + fromMultiplicity + '\'' +
                ", toMultiplicity='" + toMultiplicity + '\'' +
                '}';
    }
}
