package com.roy.loadfree;

public class UserProfile {
    //public String name;
    private String Reps;
    private String Sets;
    private String Weight;

    public UserProfile(){

    }

    public UserProfile( String userReps, String tuneSets, String tuneWeight) {
        this.Reps = userReps;
        this.Sets = tuneSets;
        this.Weight = tuneWeight;
    }

    public String getWeight() {
        return Weight;
    }

    public String getReps() {
        return Reps;
    }

    public String getSets() {
        return Sets;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public void setReps(String reps) {
        Reps = reps;
    }

    public void setSets(String sets) {
        Sets = sets;
    }
}
