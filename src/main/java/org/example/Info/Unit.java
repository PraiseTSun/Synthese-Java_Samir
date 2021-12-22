package org.example.Info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Unit {
    private String name;
    private String[] allAges;
    private String unitBatiment;
    private String[] unitCost;
    private String buildingTime;
    private int hitPoint;
    private int visibility;
    private String[] civilisations;

    public Unit() {
        this.name = null;
        this.allAges = null;
        this.unitBatiment = null;
        this.unitCost = null;
        this.buildingTime = null;
        this.hitPoint = 0;
        this.visibility = 0;
        this.civilisations = null;
    }

    public Unit(String name, String[] allAges, String unitBatiment, String[] unitCost, String buildingTime, int hitPoint, int visibility, String[] civilisations) {
        this.name = name;
        this.allAges = allAges;
        this.unitBatiment = unitBatiment;
        this.unitCost = unitCost;
        this.buildingTime = buildingTime;
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

    public String[] getAllAges() {
        return allAges;
    }

    public void setAllAges(String[] allAges) {
        this.allAges = allAges;
    }

    public String getUnitBatiment() {
        return unitBatiment;
    }

    public void setUnitBatiment(String unitBatiment) {
        this.unitBatiment = unitBatiment;
    }

    public String[] getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String[] unitCost) {
        this.unitCost = unitCost;
    }

    public String getBuildingTime() {
        return buildingTime;
    }

    public void setBuildingTime(String buildingTime) {
        this.buildingTime = buildingTime;
    }

    public int getLivingPoint() {
        return hitPoint;
    }

    public void setLivingPoint(int livingPoint) {
        this.hitPoint = livingPoint;
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
        return name + "\t" + String.join(" ",allAges) + "\t" + unitBatiment + "\t" + String.join(" ", unitCost) + "\t" + buildingTime + "\t" + hitPoint + "\t" + visibility + "\t" + String.join(" ", civilisations) + "\n";
    }

    @Override
    public String toString() {
        return name + "\n------------------------\n" +
                "- Ages: " + String.join(" | ",getAgesString(allAges)) + "\n" +
                "- UnitBatiment: " + unitBatiment + "\n" +
                "- UnitCost: " + String.join(" | ", unitCost) + "\n" +
                "- BuildingTime: " + buildingTime + "\n" +
                "- HitPoint: " + hitPoint + "\n" +
                "- Visibility: " + visibility + "\n" +
                "- Civilisations: " + String.join(" | ", civilisations) +
                "\n\n";
    }
}
