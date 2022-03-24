package com.cs991_group_project.objects;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONHandler {

    public JSONHandler() {

    }

    /**
     * Generates a JSON file to store the current users data
     * @param file The file to store the data in
     * @param user The CurrentUser object to convert to JSON
     */
    public void saveUserDataFile(File file, CurrentUser user) {

        try {

            JSONObject userJSON = new JSONObject();
            userJSON.put("uName", user.getUName());
            userJSON.put("email", user.getEmail());
            userJSON.put("joinDate", user.getJoinDate());

            JSONArray languagesJSON = new JSONArray();

            for (Language language : user.getLanguages()) {
                JSONObject languageJSON = new JSONObject();
                languageJSON.put("langName", language.getLangName());

                JSONArray lessonsJSON = new JSONArray();
                for (Lesson lesson : language.getLessons()) {
                    JSONObject lessonJSON = new JSONObject();
                    lessonJSON.put("lessonNum", lesson.getLessonNum());
                    lessonJSON.put("lessonComplete", lesson.isLessonComplete());
                    lessonJSON.put("lessonScore", lesson.getLessonScore());
                    lessonsJSON.put(lessonJSON);
                }

                languageJSON.put("lessons", lessonsJSON);
                languagesJSON.put(languageJSON);
            }

            userJSON.put("languages", languagesJSON);

            FileWriter fw = new FileWriter(file);
            fw.write(userJSON.toString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads data for the current user from a JSON file
     * @param file The file to load data from
     * @return The generated CurrentUser object
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public CurrentUser loadUserDataFile(File file){

        JSONObject userJson;
        CurrentUser user = new CurrentUser();

        try {
            String jsonParse = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));

            userJson = new JSONObject(jsonParse);

            user.setUName((String) userJson.get("uName"));
            user.setEmail((String) userJson.get("email"));
            user.setJoinDate((String) userJson.get("joinDate"));

            JSONArray newLanguagesJson = (JSONArray) userJson.get("languages");
            if (newLanguagesJson.length() > 0) {
                for (int i = 0; i < newLanguagesJson.length(); i++){
                Language newLanguage = new Language();
                JSONObject languageJson = (JSONObject) newLanguagesJson.get(i);
                newLanguage.setLangName((String) languageJson.get("langName"));

                JSONArray newLessonsJson = (JSONArray) languageJson.get("lessons");

                for (int x = 0; x < newLessonsJson.length(); x++) {
                    Lesson newLesson = new Lesson();
                    JSONObject newLessonJson = (JSONObject) newLessonsJson.get(x);

                    newLesson.setLessonNum((int) newLessonJson.get("lessonNum"));
                    newLesson.setLessonScore((int) newLessonJson.get("lessonScore"));
                    newLesson.setLessonComplete((boolean) newLessonJson.get("lessonComplete"));
                    newLanguage.addLesson(newLesson);
                }

                    user.addLanguage(newLanguage);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Sets up the a file using assets
     * @param file The file to save the data in
     * @param line The String line of an asset in JSON format
     */
    public void setupFileFromAssets(File file, String line) {
        try {
            JSONObject json = new JSONObject(line);

            FileWriter fw = new FileWriter(file);
            fw.write(json.toString());
            fw.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the data from the users file used during login
     * @param file The file to load data from
     * @return A generated list of users
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public StoredUserList loadUsersFile(File file){
        StoredUserList userList = new StoredUserList();

        try {
            String jsonParse = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));
            JSONObject usersJson = new JSONObject(jsonParse);
            JSONArray userJsonArray = (JSONArray) usersJson.get("users");

            for (int i = 0; i < userJsonArray.length(); i++){
                JSONObject currentUser = (JSONObject) userJsonArray.get(i);
                User user = new User();
                user.setEmail((String) currentUser.get("email"));
                user.setPassword((String) currentUser.get("password"));
                user.setUName((String) currentUser.get("name"));
                user.setAccessLevel((String) currentUser.get("access"));
                userList.addUser(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    /**
     * Saves the current list of users to file during login
     * @param file The file to save data to
     * @param userList  The list of users to save
     */
    public void saveUsersFile(File file, StoredUserList userList){

        try {
            JSONObject usersJson = new JSONObject();
            JSONArray userJsonArray = new JSONArray();

            for (User user : userList.getStoredUsersList()) {
                JSONObject currentUser = new JSONObject();
                currentUser.put("email", user.getEmail());
                currentUser.put("password", user.getPassword());
                currentUser.put("name", user.getUName());
                currentUser.put("access", user.getAccessLevel());

                userJsonArray.put(currentUser);
            }

            usersJson.put("users", userJsonArray);

            FileWriter fw = new FileWriter(file);
            fw.write(usersJson.toString());
            fw.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current sessions user login data to file
     * @param file The file to save the data to
     * @param user The current session user
     */
    public void saveCurrentUserToFile(File file, User user) {

        try {
            JSONObject json = new JSONObject();
            json.put("email", user.getEmail());
            json.put("password", user.getPassword());
            json.put("name", user.getUName());
            json.put("access", user.getAccessLevel());

            FileWriter fw = new FileWriter(file);
            fw.write(json.toString());
            fw.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the current sessions user data from file
     * @param file The file to load data from
     * @return A generated User object for the current user
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public User loadCurrentUserFromFile(File file) {

        User user = new User();

        try{
            String jsonParse = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));
            JSONObject userJson = new JSONObject(jsonParse);

            user.setEmail((String) userJson.get("email"));
            user.setPassword((String) userJson.get("password"));
            user.setUName((String) userJson.get("name"));
            user.setAccessLevel((String) userJson.get("access"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Language loadLanguage(File file) {

        Language language = new Language();
        JSONObject langJson;

        try{
            String jsonParse = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));

            langJson = new JSONObject(jsonParse);

            language.setLangName((String) langJson.get("langName"));
            JSONArray lessonsJson = (JSONArray) langJson.get("lessons");


            for (int i = 0; i < lessonsJson.length(); i++){
                Lesson lesson = new Lesson();
                JSONObject lessonJson = (JSONObject) lessonsJson.get(i);
                lesson.setLessonNum((int) lessonJson.get("lessonNum"));

                JSONArray questionsJson = (JSONArray) lessonJson.get("questions");

                for (int x = 0; x < questionsJson.length(); x++) {
                    Question question = new Question();
                    JSONObject questionJson = (JSONObject) questionsJson.get(x);

                    question.setQNum((int) questionJson.get("qNum"));
                    question.setPictureFile((String) questionJson.get("pictureFile"));
                    question.setQText((String) questionJson.get("qText"));
                    question.setOptA((String) questionJson.get("optA"));
                    question.setOptB((String) questionJson.get("optB"));
                    question.setOptC((String) questionJson.get("optC"));
                    question.setOptD((String) questionJson.get("optD"));
                    question.setAnswer((String) questionJson.get("answer"));
                    lesson.addQuestion(question);
                }

                language.addLesson(lesson);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return language;
    }
}
