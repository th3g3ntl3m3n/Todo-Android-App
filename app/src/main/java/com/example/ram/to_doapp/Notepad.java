package com.example.ram.to_doapp;

public class Notepad {
    String subject;
    int ID;

    public Notepad(){

    }

    public Notepad(String subject, int id){
        this.subject = subject;
        this.ID = id;
    }

    public Notepad(String subject) {
        this.subject = subject;
    }

    public int getId(){
        return this.ID;
    }

    public void setId(int id){
        this.ID = id;
    }

    public String getSubject(){
        return this.subject;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }
}