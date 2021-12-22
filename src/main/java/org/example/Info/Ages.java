package org.example.Info;

import java.util.Arrays;

public class Ages {
    private String name;
    private String[] ages;

    public Ages() {
        this.name = null;
        this.ages = null;
    }

    public Ages(String name, String[] ages) {
        this.name = name;
        this.ages = ages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAges() {
        return ages;
    }

    public void setAges(String[] ages) {
        this.ages = ages;
    }

    public boolean hasAge(String nb){
        for(String age : ages)
            if(age.equals(nb))
                return true;
        return false;
    }
    @Override
    public String toString() {
        String info =  name + ":\n-----------------------------------\n";
        for(int i = 0; i < ages.length; i++) {
            switch (ages[i]) {
                case "1": info += "- Dark Age\n"; break;
                case "2": info += "- Feudal Age\n"; break;
                case "3": info += "- Castle Age\n"; break;
                case "4": info += "- Imperial Age\n"; break;
            }
        }
        return info + "\n";
    }
}
