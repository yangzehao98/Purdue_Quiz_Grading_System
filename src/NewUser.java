import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 4 - User
 * @author Purdue CS Yuxi He he647
 * The User class allow users to regist, login and delete accounts.
 * @version October 30, 2021
 */

public class User {
    private String userName; // save username
    private String password; // save password
    private boolean teacher; // save user's role

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public boolean getTeacher() {
        return teacher;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passWord) {
        this.password = passWord;
    }

    // User class for user exist
    public User(String userName, String passWord, boolean teacher) {
        this.userName = userName;
        this.password = passWord;
        this.teacher = teacher;
    }

    // User class for new user
    public User() {
        this.userName = "";
        this.password = "";
        this.teacher = false;
    }

    public boolean register(String name, String pass, boolean teach) throws IOException {

        // if there is no user file, create one
        File file = new File("User.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        //check if the username is unique
        BufferedReader br = new BufferedReader(new FileReader("User.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(",");
            if (s[0].equals(name)) {
                System.out.println("Account exist");
                return false;
            }
        }
        // while username is unique, store it in to the user file
        String info = name + "," + pass + "," + teach;
        BufferedWriter bw = new BufferedWriter(new FileWriter("User.txt", true));
        bw.write(info);
        bw.newLine();
        bw.flush();
        bw.close();
        System.out.println("register successful");

        return true;
    }

    public boolean login(String name, String pass, boolean teach) {
        // check if the account exist
        // check if the password is correct
        try {
            BufferedReader br = new BufferedReader(new FileReader("User.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                if (s[0].equals(name) && s[1].equals(pass)
                        && s[2].equals(String.valueOf(teach))) {
                    setUserName(name);
                    setPassword(pass);
                    setTeacher(teach);
                    return true;
                }
            }
        } catch (IOException ignored) {
        }
        return false;
    }

    public void deleteAccount() throws IOException {
        // delete an account
        ArrayList<String> list = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader("User.txt"));
        String line;

        while ((line = br.readLine()) != null) {
            String[] s = line.split(",");
            // remove the account information
            if (!s[0].equals(getUserName()) && !s[1].equals(getPassword())
                    && !s[2].equals(String.valueOf(getTeacher()))) {
                list.add(line);
            }
        }
        // rewrite the file
        BufferedWriter bw = new BufferedWriter(new FileWriter("User.txt", false));
        for (int i = 0; i < list.size(); i++) {
            String info = list.get(i);
            bw.write(info);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }

    public boolean editAccount(int editChoice, String newInformation) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("User.txt"));
        boolean flag = true;
        // edit username
        if (editChoice == 1) {
            //check if the new username is unique
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                if (s[0].equals(newInformation)) {
                    System.out.println("Account exist");
                    return false;
                }
            }
            //update user information
            setUserName(newInformation);
            deleteAccount();
            String info = getUserName() + "," + getPassword() + "," + getTeacher();
            BufferedWriter bw = new BufferedWriter(new FileWriter("User.txt", true));
            bw.write(info);
            bw.newLine();
            bw.flush();
            bw.close();
            // edit password

        } else {
            // update user information
            setPassword(newInformation);
            deleteAccount();
            String info = getUserName() + "," + getPassword() + "," + getTeacher();
            BufferedWriter bw = new BufferedWriter(new FileWriter("User.txt", true));
            bw.write(info);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        return true;
        // role cannot be edited
    }
}
