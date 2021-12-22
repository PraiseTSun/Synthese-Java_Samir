package org.example.Info;

public class Civilisation {
    private String name;
    private String speciality;
    private String bonus;
    private String uniqueUnity;
    private String uniqueTechnology;


    public Civilisation() {
        this.name = null;
        this.speciality = null;
        this.bonus = null;
        this.uniqueUnity = null;
        this.uniqueTechnology = null;
    }

    public Civilisation(String name, String speciality, String bonus, String uniqueUnity, String uniqueTechnology) {
        this.name = name;
        this.speciality = speciality;
        this.bonus = bonus;
        this.uniqueUnity = uniqueUnity;
        this.uniqueTechnology = uniqueTechnology;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getUniqueUnity() {
        return uniqueUnity;
    }

    public void setUniqueUnity(String uniqueUnity) {
        this.uniqueUnity = uniqueUnity;
    }

    public String getUniqueTechnology() {
        return uniqueTechnology;
    }

    public void setUniqueTechnology(String uniqueTechnology) {
        this.uniqueTechnology = uniqueTechnology;
    }

    public String toData(){
        return name + "\t" + speciality + "\t" + bonus + "\t" + uniqueUnity + "\t" + uniqueTechnology;
    }

    @Override
    public String toString() {
        return name + "\n------------------------\n" +
                "- Speciality: " + speciality + "\n" +
                "- Bonus: " + bonus + "\n" +
                "- UniqueUnity: " + uniqueUnity + "\n" +
                "- UniqueTechnology: " + uniqueTechnology + "\n\n";
    }
}
