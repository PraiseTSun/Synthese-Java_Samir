package org.example.Info;

import java.util.Arrays;

public class UnitsSection {
    private String name;
    private Unit[] results;


    public UnitsSection() {
        this.name = null;
        this.results = null;
    }

    public UnitsSection(String name, Unit[] results) {
        this.name = name;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit[] getResults() {
        return results;
    }

    public void setResults(Unit[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        String info = "************************************************\n" + name +
                "\n************************************************\n";

        for(Unit unit : results)
            info += unit.toString();

        return info;
    }
}
