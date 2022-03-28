package com.policequestions;

import java.util.List;

public class Group {
    private int id;
    private String group;
    private List<Question> questions;

    public Group() {
    }

    public Group(int id, String group, List<Question> questions) {
        this.id = id;
        this.group = group;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
