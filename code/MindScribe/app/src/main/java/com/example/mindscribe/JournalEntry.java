package com.example.mindscribe;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
public class JournalEntry {
    private int m_year, m_month, m_dayOfMonth; //variables to store date
    String entry; //variable to store entry
    private int userID;

    //constructor
    public JournalEntry(int year, int month, int dayOfMonth, String entry, int userId) {
        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
        this.entry = entry;
        this.userID = userId;
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

    public void setUserID(int userID){
        this.userID = userID;
    }

    public int getUserID(){
        return userID;
    }

}
