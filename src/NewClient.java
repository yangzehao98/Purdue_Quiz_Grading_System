import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NewClient implements Runnable {
    Socket socket;
    JFrame signInFrame;
    JFrame teacherUserMenuFrame;
    JFrame teacherUserMenu2Frame;
    JFrame teacherMenuFrame;
    // sign in buttons
    JButton signIn;
    JButton createAccount;
    JButton exitQuizTool;
    // teacher menu buttons
    JButton chooseCreateCourse;
    JButton teacherEditAccount;
    JButton teacherDeleteAccount;
    JButton teacherCreateCourse;
    JButton teacherChooseExistingCourse;
    JButton previousMenu;
    JButton createQuiz;
    JButton editQuiz;
    JButton deleteQuiz;
    JButton viewTakenQuizzes;
    JButton importQuiz;
    JButton exitTeacherMenu;
    String signInContinue;
    Boolean teacher = false;
    Boolean teacherMenu2 = false;
    Boolean showCourse = false;
    Boolean studentMenuBoolean = false;
    //student user menu
    JButton viewCourses;
    JButton studentEditAccount;
    JButton studentDeleteAccount;
    //student menu
    JButton takeQuizButton;
    JButton studentViewTakenQuizzes;
    JButton exitStudentMenu;

    private static final String WELCOME_MESSAGE = "Welcome to the online quiz tool!";
    //private static final String SIGN_IN_MENU = "1. Sign In\n2. Create an Account\n3. Exit quiz tool";
    //private static final String TEACHER_USER_MENU = "1. Choose/Create Course\n2. Edit Account\n" +
    //        "3. Delete Account\n4. Exit quiz tool";
    //private static final String TEACHER_USER_MENU_2 = "1. Create Course\n2. Choose Existing Course\n" +
    //        "3. Go back to previous menu";
    private static final String STUDENT_USER_MENU = "1. View Courses\n2. Edit Account\n" +
            "3. Delete Account\n4. Exit quiz tool";
    private static final String TEACHER_MENU = "1. Create Quiz\n2. Edit Quiz" +
            "\n3. Delete Quiz \n4. View Taken Quizzes\n5. Import Quiz\n6. Exit teacher menu";
    private static final String STUDENT_MENU = "1. Take a quiz\n2. View quizzes taken\n3. Exit student menu";
    private static final String ERROR_MESSAGE = "Please enter a valid number.";
    private static final String THANK_YOU_MESSAGE = "Thank you for using our online quiz tool!";


    public void run() {
        try {
            socket = new Socket("localhost", 2345);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             InputStream is = socket.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(is);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {


            ActionListener action = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //teacher login
                    if (e.getSource() == signIn) {
                        try {
                            writer.write("1");
                            writer.newLine();
                            writer.flush();
                            teacher = false;
                            String password = "";
                            String username = "";
                            String sendLogin = "";
                            JPanel panel = new JPanel(new GridLayout(5, 3));
                            JTextField usernameText = new JTextField(10);
                            JTextField passwordText = new JTextField(10);
                            JTextField teacherText = new JTextField(10);
                            panel.add(new JLabel("Username:"));
                            panel.add(usernameText);
                            panel.add(new JLabel("Password:"));
                            panel.add(passwordText);
                            panel.add(new JLabel("Are you a teacher? (Yes/No):"));
                            panel.add(teacherText);
                            while (true) {
                                int result = JOptionPane.showConfirmDialog(null, panel,
                                        "Please enter login information", JOptionPane.OK_CANCEL_OPTION);
                                if (result == JOptionPane.OK_OPTION) {
                                    username = usernameText.getText();
                                    password = passwordText.getText();
                                    if (username.isEmpty() || password.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Enter your login information!",
                                                "Login",
                                                JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        break;
                                    }
                                    if (teacherText.getText().equalsIgnoreCase("yes")) {
                                        teacher = true;
                                        break;
                                    } else if (teacherText.getText().equalsIgnoreCase("no")) {
                                        teacher = false;
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Enter your login information!",
                                                "Login",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    showThanksMessageDialog();
                                    return;
                                }
                            }
                            sendLogin = username + "," + password + "," + teacher;
                            writer.write(sendLogin);
                            writer.newLine();
                            writer.flush();
                            signInContinue = reader.readLine();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    //teacher create an account
                    if (e.getSource() == createAccount) {
                        try {
                            writer.write("2");
                            writer.newLine();
                            writer.flush();
                            teacher = false;
                            String password = "";
                            String username = "";
                            String sendNewAccount = "";
                            JPanel panel = new JPanel(new GridLayout(5, 3));
                            JTextField usernameText = new JTextField(10);
                            JTextField passwordText = new JTextField(10);
                            JTextField teacherText = new JTextField(10);
                            panel.add(new JLabel("Username:"));
                            panel.add(usernameText);
                            panel.add(new JLabel("Password:"));
                            panel.add(passwordText);
                            panel.add(new JLabel("Are you a teacher? (Yes/No):"));
                            panel.add(teacherText);
                            while (true) {
                                int result = JOptionPane.showConfirmDialog(null, panel,
                                        "Please enter user information", JOptionPane.OK_CANCEL_OPTION);
                                if (result == JOptionPane.OK_OPTION) {
                                    username = usernameText.getText();
                                    password = passwordText.getText();
                                    if (username.isEmpty() || password.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Enter your user information!",
                                                "Login",
                                                JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        break;
                                    }
                                    if (teacherText.getText().equalsIgnoreCase("yes")) {
                                        teacher = true;
                                        break;
                                    } else if (teacherText.getText().equalsIgnoreCase("no")) {
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Enter your login information!",
                                                "Login",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    showThanksMessageDialog();
                                    return;
                                }
                            }
                            sendNewAccount = username + "," + password + "," + teacher;
                            writer.write(sendNewAccount);
                            writer.newLine();
                            writer.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (e.getSource() == exitQuizTool) {
                        try {
                            writer.write("3");
                            writer.newLine();
                            writer.flush();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        showThanksMessageDialog();
                    }
                    if (e.getSource() == teacherEditAccount || e.getSource() == studentEditAccount) {
                        String newUsername = "";
                        String newPassword = "";
                        while (true) {
                            try {
                                writer.write("2");
                                writer.newLine();
                                writer.flush();
                                Object[] options = {"Username", "Password"};
                                Object selectionObject = JOptionPane.showInputDialog(null,
                                        "Choose what to edit:", "Edit Options",
                                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                                String selectionString = selectionObject.toString();
                                if (selectionString.equals("Username")) {
                                    writer.write("1");
                                    writer.newLine();
                                    writer.flush();
                                    while (true) {
                                        newUsername = (String) JOptionPane.showInputDialog(null,
                                                "Please enter your new username!",
                                                "Edit Username", JOptionPane.QUESTION_MESSAGE);
                                        if (newUsername.isEmpty()) {
                                            JOptionPane.showMessageDialog(null,
                                                    "String cannot be empty!",
                                                    "Edit Username Error Message",
                                                    JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            break;
                                        }
                                    }
                                    writer.write(newUsername);
                                    writer.newLine();
                                    writer.flush();
                                } else {
                                    writer.write("2");
                                    writer.newLine();
                                    writer.flush();
                                    while (true) {
                                        newPassword = (String) JOptionPane.showInputDialog(null,
                                                "Please enter your new password!",
                                                "Edit Password", JOptionPane.QUESTION_MESSAGE);
                                        if (newPassword.isEmpty()) {
                                            JOptionPane.showMessageDialog(null,
                                                    "String cannot be empty!",
                                                    "Edit Password Error Message",
                                                    JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            break;
                                        }
                                    }
                                    writer.write(newPassword);
                                    writer.newLine();
                                    writer.flush();
                                }
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                    }
                    if (e.getSource() == teacherDeleteAccount || e.getSource() == studentDeleteAccount) {
                        String deleteAccountChoice = "";
                        Object[] options = {"Yes", "No"};
                        try {
                            writer.write("3");
                            writer.newLine();
                            writer.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                        while (true) {
                            Object selectionObject = JOptionPane.showInputDialog(null,
                                    "Do you want to delete your account?", "Edit Options",
                                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            deleteAccountChoice = selectionObject.toString();
                            if (deleteAccountChoice.equals("Yes")) {
                                try {
                                    writer.write("3");
                                    writer.newLine();
                                    writer.flush();
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    if (e.getSource() == chooseCreateCourse) {
                        teacherMenu2 = true;
                    }
                    if (e.getSource() == teacherCreateCourse) {
                        String courseName;
                        try {
                            writer.write("1");
                            writer.newLine();
                            writer.flush();
                            while (true) {
                                courseName = JOptionPane.showInputDialog(null,
                                        "Enter new course name:", "Create Course",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (courseName.isEmpty()) {
                                    JOptionPane.showMessageDialog(null,
                                            "Please input a course name", "Course Creation Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {
                                    break;
                                }
                            }
                            if (courseName != null) {
                                writer.write(courseName);
                                writer.newLine();
                                writer.flush();
                            }
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    if (e.getSource() == previousMenu) {
                        teacherMenu2 = false;
                        teacherUserMenu2Frame.dispose();
                    }
                    if (e.getSource() == teacherChooseExistingCourse) {
                        String coursesFromServer = "";
                        try {
                            writer.write("1");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            int numCourses = 0;
                            String courses = "";
                            courses = reader.readLine();
                            if (courses.equals("true")) {
                                while (courses != null) {
                                    numCourses++;
                                    coursesFromServer += courses + "\n";
                                    courses = reader.readLine();
                                }

                                int courseChoice = 0;
                                while (true) {
                                    Integer[] dropdown = new Integer[numCourses];
                                    courseChoice = (int) JOptionPane.showInputDialog(null, coursesFromServer, "Course Selection",
                                            JOptionPane.PLAIN_MESSAGE, null, dropdown, null);
                                    if (courseChoice == 0) {
                                        JOptionPane.showMessageDialog(null, "Pick a valid choice!",
                                                "Course Selection Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        break;
                                    }
                                }
                                String courseChoiceToServer = String.valueOf(courseChoice);
                                writer.write(courseChoiceToServer);
                                writer.newLine();
                                writer.flush();
                            } else {
                                teacherEmptyCoursesErrorDialog();
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        showCourse = true;
                    }
                    if (e.getSource() == createQuiz) {
                        try {
                            writer.write("1");
                            writer.newLine();
                            writer.flush();
                        } catch (IOException io){
                            io.printStackTrace();
                        }
                        Quiz quiz = new Quiz();
                        String quizName;
                        String question;
                        String choice;
                        String answer;
                        ArrayList<Question> questions = new ArrayList<>();
                        int questRand;
                        int move;
                        while (true) {
                            quizName = JOptionPane.showInputDialog(null,
                                    "What is your Quiz Name?", "Quiz Creation",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (!quizName.isBlank()) {
                                quiz.setQuizName(quizName);
                                break;
                            }
                        }
                        while (true) {
                            ArrayList<String> choices = new ArrayList<>();
                            Question questionObj = new Question();
                            question = JOptionPane.showInputDialog(null,
                                    "Please input a question", "Quiz Creation",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (!question.isBlank()) {
                                questionObj.setQuestion(question);
                                while (true) {
                                    choice = JOptionPane.showInputDialog(null,
                                            "Please input an answer choice", "Quiz Creation",
                                            JOptionPane.QUESTION_MESSAGE);
                                    if (!choice.isBlank()) {
                                        choices.add(choice);
                                        move = JOptionPane.showConfirmDialog(null,
                                                "Create another answer choice?", "Quiz Creation",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                        if (move == JOptionPane.NO_OPTION) {
                                            questionObj.setChoices(choices);
                                            break;
                                        }
                                    }
                                }
                                while (true) {
                                    answer = JOptionPane.showInputDialog(null,
                                            "What is the correct answer?", "Quiz Creation",
                                            JOptionPane.QUESTION_MESSAGE);
                                    if (!answer.isBlank()) {
                                        questionObj.setAnswer(answer);
                                        break;
                                    }
                                }
                                questions.add(questionObj);
                                move = JOptionPane.showConfirmDialog(null,
                                        "Create another question?", "Quiz Creation",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (move == JOptionPane.NO_OPTION) {
                                    break;
                                }
                            }
                        }
                        quiz.setQuestions(questions);
                        while (true) {
                            questRand = JOptionPane.showConfirmDialog(null,
                                    "Do you want to randomize the quiz?", "Quiz Creation",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (questRand == JOptionPane.YES_OPTION) {
                                quiz.setRandomization(true);
                                break;
                            } else {
                                quiz.setRandomization(false);
                                break;
                            }
                        }
                        try {
                            oos.writeObject(quiz);
                            oos.writeChar('\n');
                            oos.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    if (e.getSource() == editQuiz) {
                          String quizOptions = "";
                        String quizChoice = "";
                        String cont = "";
                        String questionChoice = "";
                        String newQuestion = "";
                        String answerChoice = "";
                        String createAnotherChoice = "";
                        String correctAnswerChoice = "";
                        String makeAnotherEdit = "";
                        boolean editAnotherQuestion = true;

                        int check;
                        try {
                            writer.write("Edit Quiz");
                            writer.newLine();
                            writer.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        while (true) {
                            try {
                                quizOptions = reader.readLine();
                                quizChoice = JOptionPane.showInputDialog(null,
                                        quizOptions + "\nPlease input a quiz number", "Edit Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (!quizChoice.isBlank()) {
                                    check = Integer.parseInt(quizChoice);
                                    writer.write(quizChoice);
                                    writer.newLine();
                                    writer.flush();
                                    break;
                                }
                            } catch (NumberFormatException be) {
                                JOptionPane.showMessageDialog(null, "Please input a number",
                                        "Edit Quiz", JOptionPane.ERROR_MESSAGE);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                cont = reader.readLine();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if (cont.equals("true")) {
                                // if true --> quiz empty, else --> joptionpane that shows quizlist, they choose number
                                try {
                                    String emptyQuiz = reader.readLine();
                                    if (emptyQuiz.equals("true")) {
                                        JOptionPane.showMessageDialog(null, "This quiz is empty!",
                                                "Edit Quiz", JOptionPane.ERROR_MESSAGE);
                                        break;
                                    } else {
                                        while (editAnotherQuestion) {
                                            String quizList = reader.readLine();
                                            questionChoice = JOptionPane.showInputDialog(null,
                                                    quizList + "\nPlease input a question number", "Edit Quiz",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            writer.write(questionChoice);
                                            writer.newLine();
                                            writer.flush();

                                            Object[] options = {"edit", "delete"};
                                            Object selectionObject = JOptionPane.showInputDialog(null,
                                                    "Choose what to edit:", "Edit Quiz Options",
                                                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                                            String editChoice = selectionObject.toString();
                                            writer.write(editChoice);
                                            writer.newLine();
                                            writer.flush();

                                            newQuestion = JOptionPane.showInputDialog(null,
                                                    "Please enter a new question!", "Edit Quiz",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            writer.write(newQuestion);
                                            writer.newLine();
                                            writer.flush();
                                            while (true) {
                                                answerChoice = JOptionPane.showInputDialog(null,
                                                        "Please enter your question's answer choice!", "Edit Quiz",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                writer.write(answerChoice);
                                                writer.newLine();
                                                writer.flush();
                                                createAnotherChoice = JOptionPane.showInputDialog(null,
                                                        "Would you like to add another answer choice? " +
                                                                "\n1. Yes \n2. No", "Edit Quiz",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                writer.write(createAnotherChoice);
                                                writer.newLine();
                                                writer.flush();

                                                String addAnotherChoice = reader.readLine();
                                                if (addAnotherChoice.equals("true")) {
                                                    //create another choice = no
                                                    break;
                                                }
                                            }
                                            correctAnswerChoice = JOptionPane.showInputDialog(null,
                                                    "Please enter the correct answer choice!", "Edit Quiz",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            writer.write(correctAnswerChoice);
                                            writer.newLine();
                                            writer.flush();

                                            makeAnotherEdit = JOptionPane.showInputDialog(null,
                                                    "Edit or delete another question? yes or no", "Edit Quiz",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            writer.write(makeAnotherEdit);
                                            writer.newLine();
                                            writer.flush();
                                            String anotherQuestion = reader.readLine();
                                            if (anotherQuestion.equals("true")) {
                                                //user said "no" to edit/delete another question
                                                editAnotherQuestion = false;
                                            }
                                        }
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Incorrect Quiz Number",
                                        "Edit Quiz", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    if (e.getSource() == deleteQuiz) {
                        String quizzes = "";
                        String quizChoice;
                        int check;
                        String work = "";
                        try {
                            writer.write("3");
                            writer.newLine();
                            writer.flush();
                            quizzes = reader.readLine();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                        while (true) {
                            try {
                                quizChoice = JOptionPane.showInputDialog(null,
                                        quizzes + "\nPlease input a correct quiz number", "Delete Quiz",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (!quizChoice.isBlank()) {
                                    check = Integer.parseInt(quizChoice);
                                    writer.write(quizChoice);
                                    writer.newLine();
                                    writer.flush();
                                    break;
                                }
                            } catch (NumberFormatException ne) {
                                JOptionPane.showMessageDialog(null, "Please input a number",
                                        "Delete Quiz", JOptionPane.ERROR_MESSAGE);
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                        try {
                            work = reader.readLine();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                        if (work.equals("true")) {
                            JOptionPane.showMessageDialog(null, "Quiz Deleted",
                                    "Delete Quiz", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Quiz Number",
                                    "Delete Quiz", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                    if (e.getSource() == viewTakenQuizzes) {
                        String takenQuizzes = "";
                        try {
                            writer.write("3");
                            writer.newLine();
                            writer.flush();
                            takenQuizzes = reader.readLine();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, takenQuizzes, "View Taken Quizzes",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                    if (e.getSource() == importQuiz) {
                        Quiz quiz = new Quiz();
                        String quizName;
                        String question;
                        String choice;
                        String answer;
                        ArrayList<Question> questions = new ArrayList<>();
                        int questRand;
                        int move;
                        String formatQuestions = "";
                        ArrayList<String> splitQuestions = new ArrayList<>();
                        try {
                            writer.write("1");
                            writer.newLine();
                            writer.flush();
                            formatQuestions = reader.readLine();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        splitQuestions = (ArrayList<String>) Arrays.asList(formatQuestions.split("@"));
                        while (true) {
                            quizName = JOptionPane.showInputDialog(null,
                                    "What is your Quiz Name?", "Quiz Import",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (!quizName.isBlank()) {
                                quiz.setQuizName(quizName);
                                break;
                            }
                        }
                        for (int i = 0; i < splitQuestions.size(); i++) {
                            ArrayList<String> choices = new ArrayList<>();
                            Question questionObj = new Question();
                            questionObj.setQuestion(splitQuestions.get(i));
                            while (true) {
                                choice = JOptionPane.showInputDialog(null,
                                        "Please input an answer choice", "Quiz Import",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (!choice.isBlank()) {
                                    choices.add(choice);
                                    move = JOptionPane.showConfirmDialog(null,
                                            "Create another answer choice?", "Quiz Import",
                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    if (move == JOptionPane.NO_OPTION) {
                                        questionObj.setChoices(choices);
                                        break;
                                    }
                                }

                                while (true) {
                                    answer = JOptionPane.showInputDialog(null,
                                            "What is the correct answer?", "Quiz Import",
                                            JOptionPane.QUESTION_MESSAGE);
                                    if (!answer.isBlank()) {
                                        questionObj.setAnswer(answer);
                                        break;
                                    }
                                }
                                questions.add(questionObj);
                            }
                        }
                        quiz.setQuestions(questions);
                        while (true) {
                            questRand = JOptionPane.showConfirmDialog(null,
                                    "Do you want to randomize the quiz?", "Quiz Import",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (questRand == JOptionPane.YES_OPTION) {
                                quiz.setRandomization(true);
                                break;
                            } else {
                                quiz.setRandomization(false);
                                break;
                            }
                        }
                        try {
                            oos.writeObject(quiz);
                            oos.writeChar('\n');
                            oos.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    if (e.getSource() == viewCourses) {
                        String coursesFromServer = "";
                        try {
                            writer.write("1");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            int numCourses = 0;
                            String courses = "";
                            courses = reader.readLine();
                            if (courses.equals("true")) {
                                while (courses != null) {
                                    numCourses++;
                                    coursesFromServer += courses + "\n";
                                    courses = reader.readLine();
                                }

                                int courseChoice = 0;
                                while (true) {
                                    Integer[] dropdown = new Integer[numCourses];
                                    courseChoice = (int) JOptionPane.showInputDialog(null, coursesFromServer, "Course Selection",
                                            JOptionPane.PLAIN_MESSAGE, null, dropdown, null);
                                    if (courseChoice == 0) {
                                        JOptionPane.showMessageDialog(null, "Pick a valid choice!",
                                                "Course Selection Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        break;
                                    }
                                }
                                String courseChoiceToServer = String.valueOf(courseChoice);
                                writer.write(courseChoiceToServer);
                                writer.newLine();
                                writer.flush();
                            } else {
                                studentEmptyCoursesErrorDialog();
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (e.getSource() == takeQuizButton) {
                        String quizListClient = "";
                        int numQuizzes = 0;
                        try {
                            writer.write("Take a quiz");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            String quizListServer = reader.readLine();
                            if (quizListServer.equals("false")) {
                                studentEmptyQuizzesErrorDialog();
                            } else {
                                while (quizListServer != null) {
                                    numQuizzes++;
                                    quizListClient += quizListServer + "\n";
                                    quizListServer = reader.readLine();

                                    int quizChoice = 0;
                                    //display quizzes for client to choose
                                    try {
                                        while (true) {
                                            Integer[] dropdown = new Integer[numQuizzes];
                                            quizChoice = (int) JOptionPane.showInputDialog(null, quizListClient, "Quiz Selection",
                                                    JOptionPane.PLAIN_MESSAGE, null, dropdown, null);
                                            if (quizChoice == 0) {
                                                JOptionPane.showMessageDialog(null, "Pick a valid choice!",
                                                        "Quiz Selection Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                break;
                                            }
                                        }
                                        //send quiz choice to server
                                        String quizChoiceToServer = String.valueOf(quizChoice);
                                        writer.write(quizChoiceToServer);
                                        writer.newLine();
                                        writer.flush();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        Quiz quiz = new Quiz();
                        try {
                            quiz = (Quiz) ois.readObject();
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        int questionCounter = 0;
                        int choiceCounter = 0;
                        String questionChoice = "";
                        String currentQuestion = "";
                        String quizName = quiz.getQuizName();
                        boolean randomization = quiz.isRandomization();
                        Quiz takenQuiz = new Quiz();
                        takenQuiz.setQuizName(quizName);
                        ArrayList<String> answers = new ArrayList<>();

                        if (randomization) {

                            ArrayList<Question> shuffledQuestions = quiz.getQuestions();
                            Collections.shuffle(shuffledQuestions);
                            takenQuiz.setRandomization(true);

                            for (int i = 0; i < shuffledQuestions.size(); i++) {
                                questionCounter++;
                                choiceCounter = quiz.getQuestions().get(i).getChoicesArray().size();
                                Integer[] dropdown = new Integer[choiceCounter];
                                currentQuestion = quiz.getQuestions().get(i).getQuestion() + "\n" + quiz.getQuestions().get(i).getChoicesArray();
                                questionChoice = (String)JOptionPane.showInputDialog(null, currentQuestion, quizName,
                                        JOptionPane.PLAIN_MESSAGE, null, dropdown, null);
                                shuffledQuestions.get(i).setStudentAnswer(questionChoice);
                                answers.add(questionChoice);
                            }
                            takenQuiz.setQuestions(shuffledQuestions);
                            takenQuiz.setTimestamp(quiz.getTimestamp());
                        } else {
                            takenQuiz.setRandomization(false);

                            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                                questionCounter++;
                                choiceCounter = quiz.getQuestions().get(i).getChoicesArray().size();
                                Integer[] dropdown = new Integer[choiceCounter];
                                currentQuestion = quiz.getQuestions().get(i).getQuestion() + "\n" + quiz.getQuestions().get(i).getChoicesArray();
                                questionChoice = (String) JOptionPane.showInputDialog(null, currentQuestion, quizName,
                                        JOptionPane.PLAIN_MESSAGE, null, dropdown, null);
                                quiz.getQuestions().get(i).setStudentAnswer(questionChoice);
                                answers.add(questionChoice);
                            }
                            takenQuiz.setQuestions(quiz.getQuestions());
                            takenQuiz.setTimestamp(quiz.getTimestamp());
                        }
                        quizCompletedDialog();
                        try {
                            oos.writeObject(takenQuiz);
                            oos.writeChar('\n');
                            oos.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (e.getSource() == exitTeacherMenu) {
                        return;
                    }
                    if (e.getSource() ==  exitStudentMenu) {
                        return;
                    }
                }
            };

            //signin frame
            signInFrame = new JFrame("Sign In");
            Container signInContent = signInFrame.getContentPane();
            signInContent.setLayout(new BorderLayout());

            JPanel signInPanel = new JPanel();

            signIn = new JButton("Sign In");
            signIn.addActionListener(action);
            createAccount = new JButton("Create Account");
            createAccount.addActionListener(action);
            exitQuizTool = new JButton("Exit Quiz Tool");
            exitQuizTool.addActionListener(action);

            signInPanel.add(signIn);
            signInPanel.add(createAccount);
            signInPanel.add(exitQuizTool);
            signInContent.add(signInPanel, BorderLayout.CENTER);


            //teacher user menu frame
            teacherUserMenuFrame = new JFrame("Teacher User Menu");
            Container teacherUserMenuContent = teacherUserMenuFrame.getContentPane();
            teacherUserMenuContent.setLayout(new BorderLayout());


            JPanel teacherUserMenuPanel = new JPanel();

            chooseCreateCourse = new JButton("Choose/Create Course");
            chooseCreateCourse.addActionListener(action);
            teacherEditAccount = new JButton("Edit Account");
            teacherEditAccount.addActionListener(action);
            teacherDeleteAccount = new JButton("Delete Account");
            teacherDeleteAccount.addActionListener(action);


            teacherUserMenuPanel.add(chooseCreateCourse);
            teacherUserMenuPanel.add(teacherEditAccount);
            teacherUserMenuPanel.add(teacherDeleteAccount);
            teacherUserMenuContent.add(teacherUserMenuPanel, BorderLayout.CENTER);

            //teacher user menu 2 frame
            teacherUserMenu2Frame = new JFrame("Teacher User Menu 2");
            Container teacherUserMenu2Content = teacherUserMenu2Frame.getContentPane();
            teacherUserMenu2Content.setLayout(new BorderLayout());

            JPanel teacherUserMenu2Panel = new JPanel();

            teacherCreateCourse = new JButton("Create Course");
            teacherCreateCourse.addActionListener(action);
            teacherChooseExistingCourse = new JButton("Choose Existing Course");
            teacherChooseExistingCourse.addActionListener(action);
            previousMenu = new JButton("Go Back to Previous Menu");
            previousMenu.addActionListener(action);

            teacherUserMenu2Panel.add(teacherCreateCourse);
            teacherUserMenu2Panel.add(teacherChooseExistingCourse);
            teacherUserMenu2Panel.add(previousMenu);
            teacherUserMenu2Content.add(teacherUserMenu2Panel, BorderLayout.CENTER);

            //teacher menu frame
            teacherMenuFrame = new JFrame("Teacher Menu");
            Container teacherMenuContent = teacherMenuFrame.getContentPane();
            teacherMenuContent.setLayout(new BorderLayout());

            //teacherMenuFrame.setSize(600, 400);
            //teacherMenuFrame.setLocationRelativeTo(null);
            //teacherMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //teacherMenuFrame.setVisible(true);

            JPanel teacherMenuPanel = new JPanel();

            createQuiz = new JButton("Create Quiz");
            createQuiz.addActionListener(action);
            editQuiz = new JButton("Edit Quiz");
            editQuiz.addActionListener(action);
            deleteQuiz = new JButton("Delete Quiz");
            deleteQuiz.addActionListener(action);
            viewTakenQuizzes = new JButton("View Taken Quizzes");
            viewTakenQuizzes.addActionListener(action);
            importQuiz = new JButton("Import Quiz");
            importQuiz.addActionListener(action);
            exitTeacherMenu = new JButton("Exit Teacher Menu");
            exitTeacherMenu.addActionListener(action);

            teacherMenuPanel.add(createQuiz);
            teacherMenuPanel.add(editQuiz);
            teacherMenuPanel.add(deleteQuiz);
            teacherMenuPanel.add(viewTakenQuizzes);
            teacherMenuPanel.add(importQuiz);
            teacherMenuPanel.add(exitTeacherMenu);
            teacherMenuContent.add(teacherMenuPanel, BorderLayout.CENTER);

            JFrame studentUserMenuFrame = new JFrame("Student User Menu");
            Container studentUserMenuContent = studentUserMenuFrame.getContentPane();
            signInContent.setLayout(new BorderLayout());
            JPanel studentUserMenuPanel = new JPanel();
            viewCourses = new JButton("View Courses");
            viewCourses.addActionListener(action);
            studentEditAccount = new JButton("Edit Account");
            studentEditAccount.addActionListener(action);
            studentDeleteAccount = new JButton("Delete Account");
            studentDeleteAccount.addActionListener(action);

            studentUserMenuPanel.add(viewCourses);
            studentUserMenuPanel.add(studentEditAccount);
            studentUserMenuPanel.add(studentDeleteAccount);
            studentUserMenuContent.add(studentUserMenuPanel, BorderLayout.CENTER);

            JFrame studentMenuFrame = new JFrame("Student Menu");
            Container studentMenuContent = studentMenuFrame.getContentPane();
            JPanel studentMenuPanel = new JPanel();
            takeQuizButton = new JButton("Take a Quiz");
            studentViewTakenQuizzes = new JButton("View Taken Quizzes");
            exitStudentMenu = new JButton("Exit Student Menu");

            studentMenuPanel.add(takeQuizButton);
            studentMenuPanel.add(studentViewTakenQuizzes);
            studentMenuPanel.add(exitStudentMenu);
            studentMenuContent.add(studentMenuPanel, BorderLayout.CENTER);

            signInFrame.setSize(600, 400);
            signInFrame.setLocationRelativeTo(null);
            signInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            signInFrame.setVisible(true);

            if (signInContinue.equals("true")) {
                signInFrame.dispose();
                if (teacher) {
                    // Teacher Menu
                    while (true) {
                        teacherUserMenuFrame.setSize(600, 400);
                        teacherUserMenuFrame.setLocationRelativeTo(null);
                        teacherUserMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        teacherUserMenuFrame.setVisible(true);

                        while (teacherMenu2) {
                            teacherUserMenuFrame.dispose();
                            teacherUserMenu2Frame.setSize(600, 400);
                            teacherUserMenu2Frame.setLocationRelativeTo(null);
                            teacherUserMenu2Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            teacherUserMenu2Frame.setVisible(true);

                            if (showCourse) {
                                teacherUserMenu2Frame.dispose();
                                teacherMenuFrame.setSize(600, 400);
                                teacherMenuFrame.setLocationRelativeTo(null);
                                teacherMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                teacherMenuFrame.setVisible(true);
                            }
                        }
                    }

                } else {
                    // Student Menu
                    while (true) {
                        studentUserMenuFrame.setSize(600, 400);
                        studentUserMenuFrame.setLocationRelativeTo(null);
                        studentUserMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        studentUserMenuFrame.setVisible(true);
                        
                        while (studentMenuBoolean) {
                            studentMenuFrame.setSize(600, 400);
                            studentMenuFrame.setLocationRelativeTo(null);
                            studentMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            studentMenuFrame.setVisible(true);
                        }
                    }
                }
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, WELCOME_MESSAGE,
                "Online Quiz Tool", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void showThanksMessageDialog() {
        JOptionPane.showMessageDialog(null, THANK_YOU_MESSAGE,
                "Online Quiz Tool", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    public static void studentEmptyCoursesErrorDialog() {
        JOptionPane.showMessageDialog(null, "No courses exist." +
                        " Please contact your teacher " +
                        "regarding their administrative error. Goodbye!",
                "Online Quiz Tool", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    public static void studentEmptyQuizzesErrorDialog() {
        JOptionPane.showMessageDialog(null, "No quizzes exist in this course. " +
                        "Please contact your teacher " +
                        "regarding their administrative error. Goodbye!",
                "Online Quiz Tool", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    public static void teacherEmptyCoursesErrorDialog() {
        JOptionPane.showMessageDialog(null, "No courses exist. Please create a course",
                "Online Quiz Tool", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void quizCompletedDialog() {
        JOptionPane.showMessageDialog(null, "Quiz Completed!",
                "Online Quiz Tool", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String []args){
        new NewClient().run();
    }
}
