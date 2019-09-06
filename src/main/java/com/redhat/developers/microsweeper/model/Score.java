package com.redhat.developers.microsweeper.model;

public class Score {

    public String name;
    public String level;
    public int time;
    public boolean success;

    public Score() {
    }

    public Score(String name, String level, int time, boolean success) {
        this.name = name;
        this.level = level;
        this.time = time;
        this.success = success;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return name + "/" + level + "/" + time + "/" + success;
    }
}
