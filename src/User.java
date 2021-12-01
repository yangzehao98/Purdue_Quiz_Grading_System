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
    private static final String INPUT_NAME_MESSAGE = "Input your username:";
    // massage to input username
    private static final String INPUT_PASSWORD_MESSAGE = "Input your password:";
    // message to input password
    private static final String INPUT_TEACHER_MESSAGE = "Are you a teacher(yes or no):";
    // massage to input role

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

    public boolean register(Scanner sc) throws IOException {
        // input username, password and role
        System.out.println(INPUT_NAME_MESSAGE);
        String name = sc.nextLine();
        // check wrong username
        while (name == null || name.isBlank()) {
            System.out.println("Please input valid username");
            userName = sc.nextLine();
        }

        System.out.println(INPUT_PASSWORD_MESSAGE);
        String pass = sc.nextLine();
        // check wrong password
        while (pass == null || pass.isBlank()) {
            System.out.println("Please input valid password");
            pass = sc.nextLine();
        }

        System.out.println(INPUT_TEACHER_MESSAGE);
        String temp = sc.nextLine();
        // check wrong role
        while (!temp.equals("yes") && !temp.equals("no")) {
            System.out.println("Please input yes or no");
            temp = sc.nextLine();
        }

        boolean teach = temp.equals("yes");
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

    public boolean login(Scanner sc) {
        // allow user to login
        // input username, password and role

        System.out.println(INPUT_NAME_MESSAGE);
        String name = sc.nextLine();
        // check wrong username
        while (name == null || name.isBlank()) {
            System.out.println("Please input valid username");
            name = sc.nextLine();
        }

        System.out.println(INPUT_PASSWORD_MESSAGE);
        String pass = sc.nextLine();
        // check wrong password
        while (pass == null || pass.isBlank()) {
            System.out.println("Please input valid password");
            pass = sc.nextLine();
        }

        System.out.println(INPUT_TEACHER_MESSAGE);
        String temp = sc.nextLine();
        // check wrong role
        while (!temp.equals("yes") && !temp.equals("no")) {
            System.out.println("Please input yes or no");
            temp = sc.nextLine();
        }

        boolean teach = temp.equals("yes");
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
                    System.out.println("login success");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Account does not exist");
        }
        System.out.println("Please input information correctly");
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

    public boolean editAccount(Scanner sc) throws IOException {
        // edit account
        System.out.println("1. Edit username\n2. Edit password");
        int editChoice = sc.nextInt();
        sc.nextLine();
        // choose menu
        while (editChoice != 1 && editChoice != 2) {
            System.out.println("Please input valid number");
            System.out.println("1. Edit username\n2. Edit password");
            editChoice = sc.nextInt();
        }
        BufferedReader br = new BufferedReader(new FileReader("User.txt"));
        boolean flag = true;
        // edit username
        if (editChoice == 1) {
            // input new username
            System.out.println("Please input new username");
            String newName = sc.nextLine();
            // check if the new username is valid
            while (newName == null || newName.isBlank()) {
                System.out.println("Please input valid password");
                newName = sc.nextLine();
            }
            //check if the new username is unique
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(",");
                if (s[0].equals(newName)) {
                    System.out.println("Account exist");
                    return false;
                }
            }
            //update user information
            setUserName(newName);
            deleteAccount();
            String info = getUserName() + "," + getPassword() + "," + getTeacher();
            BufferedWriter bw = new BufferedWriter(new FileWriter("User.txt", true));
            bw.write(info);
            bw.newLine();
            bw.flush();
            bw.close();
            // edit password

        } else {
            // input new password
            System.out.println("Please input new password");
            String newPassword = sc.nextLine();
            // check if the new password is valid
            while (newPassword == null || newPassword.isBlank()) {
                System.out.println("Please input valid password");
                newPassword = sc.nextLine();
            }
            // update user information
            setPassword(newPassword);
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
