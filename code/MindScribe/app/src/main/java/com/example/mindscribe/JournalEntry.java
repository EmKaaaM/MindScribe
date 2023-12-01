package com.example.mindscribe;

public class JournalEntry {
    private int m_year, m_month, m_dayOfMonth, m_userID; //variables to store date
    private String m_mood, m_keywords;
    String m_entry; //variable to store entry

    //constructor
    public JournalEntry(int year, int month, int dayOfMonth, String entry, int userId, String mood, String keywords) {
        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
        m_entry = entry;
        m_userID = userId;
        m_mood = mood;
        m_keywords = keywords;
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
        return m_entry;
    }

    public String getKeywords() {
        return m_keywords;
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

    public void setKeywords(String keywords) {
        m_keywords = keywords;
    }

    public void setEntry(String entry){
        m_entry = entry;
    }

    public void setUserID(int userID){
        m_userID = userID;
    }

    public int getUserID(){
        return m_userID;
    }

}
