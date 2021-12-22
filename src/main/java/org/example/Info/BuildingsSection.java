package org.example.Info;

import java.util.Arrays;

public class BuildingsSection {
    private String name;
    private Building[] results;

    public BuildingsSection() {
        this.name = null;
        this.results = null;
    }

    public BuildingsSection(String name, Building[] results) {
        this.name = name;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building[] getResults() {
        return results;
    }

    public void setResults(Building[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        String info = "************************************************\n" + name +
                "\n************************************************\n";

        for(Building build : results)
            info += build.toString();

        return info;
    }
}
