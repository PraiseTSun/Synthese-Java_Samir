package org.example.Info;

import java.util.Arrays;

public class AgesSection {
    private String name;
    private Ages[] results;

    public AgesSection() {
        this.name = null;
        this.results = null;
    }

    public AgesSection(String name, Ages[] results) {
        this.name = name;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ages[] getResults() {
        return results;
    }

    public void setResults(Ages[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        String info ="\n***********************************************************\n" +
                name +
                "\n***********************************************************\n";

        for(Ages ages : results)
            info += ages.toString();

        return info;
    }

    public String toString(String nb){
        String info ="\n***********************************************************\n" +
                name +
                "\n***********************************************************\n";

        for(Ages ages : results)
            if(ages.hasAge(nb))
                info += ages.toString();

        return info;
    }
}
