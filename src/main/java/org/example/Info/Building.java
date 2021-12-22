package org.example.Info;

import java.util.ArrayList;
import java.util.Arrays;

public class Building {
    private String name;
    private String[] ages;
    private String type;
    private String[] cost;
    private String time;
    private int hitPoint;
    private int visibility;
    private String[] civilisations;

    public Building() {
        this.name = null;
        this.ages = null;
        this.type = null;
        this.cost = null;
        this.time = null;
        this.hitPoint = 0;
        this.visibility = 0;
        this.civilisations = null;
    }

    public Building(String name, String[] ages, String type, String[] cost, String time, int hitPoint, int visibility, String[] civilisations) {
        this.name = name;
        this.ages = ages;
        this.type = type;
        this.cost = cost;
        this.time = time;
        this.hitPoint = hitPoint;
        this.visibility = visibility;
        this.civilisations = civilisations;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getCost() {
        return cost;
    }

    public void setCost(String[] cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String[] getCivilisations() {
        return civilisations;
    }

    public void setCivilisations(String[] civilisations) {
        this.civilisations = civilisations;
    }

    private String[] getAgesString(String[] ages){
        String[] age = new String[ages.length];
        for(int i = 0; i < ages.length; i++){
            switch(ages[i]){
                case "1": age[i] = "Dark Age"; break;
                case "2": age[i] = "Feudal Age"; break;
                case "3": age[i] = "Castle Age"; break;
                case "4": age[i] = "Imperial Age"; break;
                default: age[i] = "unknow"; break;
            }
        }
        return age;
    }

    public String toData(){
        return name + "\t" + String.join(" ", ages) + "\t" + type + "\t" + String.join(" ", cost) + "\t" + time + "\t" + hitPoint + "\t" + visibility + "\t" + String.join(" ", civilisations);
    }

    @Override
    public String toString() {
        return name + "\n------------------------\n" +
                "- Ages: " + String.join(" | ",getAgesString(ages)) + "\n" +
                "- Type: " + type + "\n" +
                "- Cost: " + String.join(" | ", cost) + "\n"+
                "- Time: " + time + "\n" +
                "- HitPoint: " + hitPoint + "\n" +
                "- Visibility: " + visibility + "\n" +
                "- Civilisations: " + String.join(" | ", civilisations) + "\n\n";
    }
}

