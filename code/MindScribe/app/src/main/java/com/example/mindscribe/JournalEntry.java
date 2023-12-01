package com.example.mindscribe;

public class JournalEntry {
    private int m_year, m_month, m_dayOfMonth, m_userID; //variables to store date
    private String m_mood;
    String entry; //variable to store entry

    //constructor
    public JournalEntry(int year, int month, int dayOfMonth, String entry, int userId, String mood) {
        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
        this.entry = entry;
        this.m_userID = userId;
        m_mood = mood;
    }

    //getters
    public int getYear(){
        return m_year;
    }

    public int getMonth(){
        return m_month;
    }

    public int getDayOfMonth(){
        return m_dayOfMonth;
    }

    public String getMood() {
        return m_mood;
    }

    public String getEntry(){
        return entry;
    }

    //setters
    public void setYear(int year){
        m_year = year;
    }

    public void setMonth(int month){
        m_month = month;
    }

    public void setDayOfMonth(int dayOfMonth){
        m_dayOfMonth = dayOfMonth;
    }

    public void setMood(String mood) {
        m_mood = mood;
    }

    public void setEntry(String entry){
        this.entry = entry;
    }

    public void setUserID(int userID){
        this.m_userID = userID;
    }

    public int getUserID(){
        return m_userID;
    }

}
