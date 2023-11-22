package com.example.mindscribe;

public class JournalEntry {
    private int m_year, m_month, m_dayOfMonth; //variables to store date
    String entry; //variable to store entry

    //constructor
    public JournalEntry(int year, int month, int dayOfMonth, String entry){
        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
        this.entry = entry;
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

    public void setEntry(String entry){
        this.entry = entry;
    }
}
