# MindScribe
### Team 13 SQA

The MindScribe app is a mental health journalling app where users are able to create and edit daily journal entries on how they are feeling and are able to express what they wish through writing them. Each day they will be able to record their mood and the app will track how they are feeling over time. There will be a login system so regardless of the device the app runs on, they will be able to access and record journals to the same account.

This Project consists of 3 main project folders, Application, API and Database. Together these form the functionality of the Mind Scribe app.
The Application project is primarily the front end of the app where the UI and general functionality will be. This is done in Java.
The Database project is the database which stores all the user data for the journals as well as the login information. Done in PostgreSQL.
The API is what connects the Application to the Database and allows the transfer of data into the User interface. It is a REST API.

## Getting Started
1. Install NodeJS (https://nodejs.org/en/)
2. In code/API run npm install
3. In code/API create a file called .env and paste the following into it:
   CONNECTION_STRING='postgresql://mindscribe:rwrmcts2rbuRhKLl9qQAlQ@chilly-centaur-11576.8nj.cockroachlabs.cloud:26257/defaultdb?sslmode=verify-full'
   PORT=3001
   JWT_SECRET='secret'
4. In code/API run npm start
5. Create an account on the app and remember credentials
6. Login to the app with the credentials
7. Start journalling!