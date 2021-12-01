import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Project 4 Group 57 - Main Class
 * <p>
 * Main class uses Course, User, Quiz, and Question classes to create a Quiz tool
 *
 * @author Jade Gu, Evans Tang, Yuxi He, Evan Glenn, section L18
 * @version 11/14/2021
 */
public class Main extends Quiz implements Serializable {
    private static final String WELCOME_MESSAGE = "Welcome to the online quiz tool!";
    private static final String SIGN_IN_MENU = "1. Sign In\n2. Create an Account\n3. Exit quiz tool";
    private static final String TEACHER_USER_MENU = "1. Choose/Create Course\n2. Edit Account\n" +
            "3. Delete Account\n4. Exit quiz tool";
    private static final String TEACHER_USER_MENU_2 = "1. Create Course\n2. Choose Existing Course\n" +
            "3. Go back to previous menu";
    private static final String STUDENT_USER_MENU = "1. View Courses\n2. Edit Account\n" +
            "3. Delete Account\n4. Exit quiz tool";
    private static final String TEACHER_MENU = "1. Create Quiz\n2. Edit Quiz" +
            "\n3. Delete Quiz \n4. View Taken Quizzes\n5. Import Quiz\n6. Exit teacher menu";
    private static final String STUDENT_MENU = "1. Take a quiz\n2. View quizzes taken\n3. Exit student menu";
    private static final String ERROR_MESSAGE = "Please enter a valid number.";
    private static final String THANK_YOU_MESSAGE = "Thank you for using our online quiz tool!";
    private static ReadWrite fileObj = new ReadWrite();
    
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String signInChoice = ""; //to navigate through sign in menu
        String userTeacherChoice = ""; //to navigate through user menu as a teacher
        String userTeacherChoice2 = ""; //to deal with course
        String userStudentChoice = ""; //to navigate through user menu as a student
        String teacherChoice = ""; //to navigate through teacher menu
        String studentChoice = ""; //to navigate through student menu
        String quizChoice = "";

        do {
            System.out.println(WELCOME_MESSAGE);
            System.out.println(SIGN_IN_MENU);
            signInChoice = scanner.nextLine();
            //creating an account
            if (signInChoice.equals("2")) {

                User user = new User();
                boolean registerFlag = user.register(scanner);
                while (!registerFlag) {
                    registerFlag = user.register(scanner);
                }
                signInChoice = "1";

                //incorrect input
            } else if (signInChoice.equals("3")) {
                break;
            }
            if (!signInChoice.equals("1") && !signInChoice.equals("2") && !signInChoice.equals("3")) {
                System.out.println(ERROR_MESSAGE);
            }
        } while (!signInChoice.equals("1") && !signInChoice.equals("2") && !signInChoice.equals("3"));

        //signing in
        User user = new User();
        boolean loginFlag = user.login(scanner);
        while (!loginFlag) {
            System.out.println("1. Try again\n2. Register a new Account");
            String loginChoice = scanner.nextLine();
            if (loginChoice.equals("1")) {
                loginFlag = user.login(scanner);
            } else if (loginChoice.equals("2")) {
                loginFlag = user.register(scanner);
            } else {
                System.out.println("Please input a valid number");
            }
        }
        ArrayList<Course> courses = fileObj.readCourses("courses.txt");
        if (courses == null) {
            courses = new ArrayList<>();
        }
        if (user.getTeacher()) { //teacher portion of control flow
            while (true) {
                System.out.println(TEACHER_USER_MENU);
                userTeacherChoice = scanner.nextLine();
                if (userTeacherChoice.equals("1")) {
                    while (true) {
                        System.out.println(TEACHER_USER_MENU_2);
                        userTeacherChoice2 = scanner.nextLine();
                        if (userTeacherChoice2.equals("1")) { //create course
                            System.out.println("Please input course name: ");
                            String courseName = scanner.nextLine();
                            Course newCourse = new Course(user.getUserName(), courseName,
                                    new ArrayList<Quiz>(), new ArrayList<Quiz>());
                            courses.add(newCourse);
                            fileObj.writeEditedCourses("courses.txt", courses);
                        } else if (userTeacherChoice2.equals("2")) { //choose course and quiz menu
                            if (courses.isEmpty()) {
                                System.out.println("No courses currently exist. Please select option 1" +
                                        " to create a course before proceeding. ");
                            } else {
                                String courseChoiceTeacher = "";
                                int count = 0;
                                for (int i = 0; i < Objects.requireNonNull(courses).size(); i++) {
                                    if (courses.get(i).getTeacherName().equals(user.getUserName())) {
                                        count++;
                                        System.out.printf("%d: %s", i + 1, courses.get(i).getCourseName() + "\n");
                                    }
                                }
                                int courseNumber = -1;

                                System.out.println("\nPick a course number:"); //pick courses
                                courseChoiceTeacher = scanner.nextLine();
                                boolean isInt2 = courseChoiceTeacher.matches("[0-9]+");
                                while (!isInt2 || Integer.parseInt(courseChoiceTeacher) <= 0 ||
                                        Integer.parseInt(courseChoiceTeacher) > courses.size()) {
                                    System.out.println("\nPick a valid course number:"); //pick courses
                                    courseChoiceTeacher = scanner.nextLine();
                                }
                                courseNumber = Integer.parseInt(courseChoiceTeacher) - 1;

                                while (true) {
                                    System.out.println(TEACHER_MENU);
                                    teacherChoice = scanner.nextLine();
                                    if (teacherChoice.equals("1")) { //creating a quiz
                                        Quiz quiz = new Quiz();
                                        String question = "";
                                        String choice = "";
                                        String answer = "";
                                        String createAnotherChoice = "";
                                        String createAnotherQuestion = "";
                                        String questRand = "";

                                        ArrayList<Question> questions = new ArrayList<>();

                                        System.out.println("Please enter the name of your quiz:");
                                        String quizName = scanner.nextLine();
                                        quiz.setQuizName(quizName);


                                        while (true) {
                                            ArrayList<String> choices = new ArrayList<>();
                                            Question questionObj = new Question();
                                            System.out.println("Please enter your question:");
                                            question = scanner.nextLine();
                                            questionObj.setQuestion(question);
                                            questionObj.setQuizName(quizName);
                                            while (true) {
                                                System.out.println("Please enter your question's answer choice:");
                                                choice = scanner.nextLine();
                                                choices.add(choice);
                                                questionObj.setChoices(choices);
                                                System.out.println("Would you like to add another answer choice? " +
                                                        "\n1. Yes \n2. No");
                                                createAnotherChoice = scanner.nextLine();
                                                while (!createAnotherChoice.equals("1") &&
                                                        !createAnotherChoice.equals("2")) {
                                                    System.out.println("Please input correct choice");
                                                    createAnotherChoice = scanner.nextLine();
                                                }
                                                if (createAnotherChoice.equals("2")) {
                                                    System.out.println("Please enter the correct answer " +
                                                            "for your question:");
                                                    answer = scanner.nextLine();
                                                    questionObj.setAnswer(answer);
                                                    questions.add(questionObj);
                                                    break;
                                                }
                                            }
                                            //create another question
                                            //loops until user says no
                                            System.out.println("Would you like to create another question? " +
                                                    "\n1. Yes \n2. No");
                                            createAnotherQuestion = scanner.nextLine();
                                            while (!createAnotherQuestion.equals("1") &&
                                                    !createAnotherQuestion.equals("2")) {
                                                System.out.println("Please input correct choice");
                                                createAnotherQuestion = scanner.nextLine();
                                            }
                                            if (createAnotherQuestion.equals("2")) {
                                                quiz.setQuestions(questions);
                                                break;
                                            }
                                        }

                                        System.out.println("Do you want to randomize the order of your quiz's " +
                                                "questions? \n1. Yes \n2. No");
                                        questRand = scanner.nextLine();
                                        while (!questRand.equals("1") && !questRand.equals("2")) {
                                            System.out.println("Please input correct choice");
                                            questRand = scanner.nextLine();
                                        }
                                        if (questRand.equals("1")) {
                                            quiz.setRandomization(true);
                                        } else {
                                            quiz.setRandomization(false);
                                        }
                                        courses.get(courseNumber).addQuiz(quiz);

                                        fileObj.writeEditedCourses("courses.txt", courses);
                                        System.out.println("Your quiz has been created!");
                                    } else if (teacherChoice.equals("2")) { // editing quiz
                                        System.out.println(courses.get(courseNumber).getQuizzes());
                                        while (true) {
                                            System.out.println("Choose quiz number: ");
                                            quizChoice = scanner.nextLine();
                                            boolean isInt = quizChoice.matches("[0-9]+");
                                            if (Integer.parseInt(quizChoice) >
                                                    courses.get(courseNumber).getQuizList().size()
                                                    || Integer.parseInt(quizChoice) <= 0 || !isInt) {
                                                System.out.println("Invalid input, try again");
                                            } else {
                                                break;
                                            }
                                        }
                                        quizChoice = String.valueOf(Integer.parseInt(quizChoice) - 1);
                                        while (true) {
                                            if (courses.get(courseNumber).getQuizList()
                                                    .get(Integer.parseInt(quizChoice))
                                                    .getQuestions() == null || courses
                                                    .get(courseNumber).getQuizList().get(Integer.parseInt(quizChoice))
                                                    .getQuestions().size() == 0) {
                                                System.out.println("This quiz is empty, please input quiz");
                                                break;
                                            }
                                            for (int i = 0; i < courses.get(courseNumber).getQuizList()
                                                    .get(Integer.parseInt(quizChoice))
                                                    .getQuestions().size(); i++) {
                                                System.out.println(courses.get(courseNumber).getQuizList()
                                                        .get(Integer.parseInt(quizChoice))
                                                        .getQuestions().get(i).toString() + "\nAnswer: " +
                                                        courses.get(courseNumber).getQuizList()
                                                                .get(Integer.parseInt(quizChoice))
                                                                .getQuestions().get(i).getAnswer());
                                            }
                                            String questionChoice = "";
                                            while (true) {
                                                System.out.println("Please enter question number");
                                                questionChoice = scanner.nextLine();
                                                if (Integer.parseInt(questionChoice) > courses.get(courseNumber)
                                                        .getQuizList().get(Integer.parseInt(quizChoice))
                                                        .getQuestions().size() ||
                                                        Integer.parseInt(questionChoice) <= 0) {
                                                    System.out.println("Invalid Input, try again");
                                                } else {
                                                    break;
                                                }
                                            }
                                            questionChoice = String.valueOf(Integer.parseInt(questionChoice) - 1);
                                            scanner.nextLine();
                                            String editChoice = "";
                                            while (true) {
                                                System.out.println("Edit or Delete question? Type Edit or Delete");
                                                editChoice = scanner.nextLine();
                                                if (!editChoice.equalsIgnoreCase("edit") &&
                                                        !editChoice.equalsIgnoreCase("delete")) {
                                                    System.out.println("Invalid Input, try again");
                                                } else {
                                                    break;
                                                }
                                            }

                                            if (editChoice.equalsIgnoreCase("edit")) {
                                                String question = "";
                                                String choice = "";
                                                String answer = "";
                                                String createAnotherChoice = "";

                                                ArrayList<String> choices = new ArrayList<>();
                                                System.out.println("Please enter new Question?");
                                                question = scanner.nextLine();
                                                while (true) {
                                                    System.out.println("Please enter your question's answer choice:");
                                                    choice = scanner.nextLine();
                                                    choices.add(choice);
                                                    System.out.println("Would you like to add another answer choice? " +
                                                            "\n1. Yes \n2. No");
                                                    createAnotherChoice = scanner.nextLine();
                                                    while (!createAnotherChoice.equals("1") &&
                                                            !createAnotherChoice.equals("2")) {
                                                        System.out.println("Please input correct choice");
                                                        createAnotherChoice = scanner.nextLine();
                                                    }
                                                    if (createAnotherChoice.equals("2")) {
                                                        break;
                                                    }
                                                }

                                                System.out.println("Please enter the correct answer for " +
                                                        "your question:");
                                                answer = scanner.nextLine();

                                                Question questionObj = new Question(question, choices, courses
                                                        .get(courseNumber).getQuizList()
                                                        .get(Integer.parseInt(quizChoice)).getQuizName(),
                                                        answer);
                                                courses.get(courseNumber).getQuizList()
                                                        .get(Integer.parseInt(quizChoice)).getQuestions()
                                                        .set(Integer.parseInt(questionChoice), questionObj);
                                                System.out.println("Question edited");
                                            } else {
                                                courses.get(courseNumber).getQuizList()
                                                        .get(Integer.parseInt(quizChoice))
                                                        .getQuestions().remove(Integer.parseInt(questionChoice));
                                                System.out.println("Question deleted");
                                            }
                                            fileObj.writeEditedCourses("courses.txt", courses);
                                            String choice = "";
                                            while (true) {
                                                System.out.println("Edit or delete another question? Yes or no");
                                                choice = scanner.nextLine();
                                                if (!choice.equalsIgnoreCase("yes") &&
                                                        !choice.equalsIgnoreCase("no")) {
                                                    System.out.println("Invalid Input, try again");
                                                } else {
                                                    break;
                                                }
                                            }
                                            if (choice.equalsIgnoreCase("no")) {
                                                break;
                                            }
                                        }
                                    } else if (teacherChoice.equals(3)) { // delete quiz
                                        System.out.println(courses.get(courseNumber).getQuizzes());
                                        while (true) {
                                            System.out.println("Choose quiz number: ");
                                            quizChoice = scanner.nextLine();
                                            boolean isInt = quizChoice.matches("[0-9]+");
                                            if (Integer.parseInt(quizChoice) > courses
                                                    .get(courseNumber).getQuizList().size()
                                                    || Integer.parseInt(quizChoice) <= 0) {
                                                System.out.println("Invalid input, try again");
                                                isInt = quizChoice.matches("[0-9]+");
                                            } else {
                                                break;
                                            }
                                        }
                                        courses.get(courseNumber).getQuizList()
                                                .remove(Integer.parseInt(quizChoice) - 1);
                                        System.out.println("Course deleted");
                                        fileObj.writeEditedCourses("courses.txt", courses);
                                    } else if (teacherChoice.equals("4")) { // view taken quizzes
                                        System.out.println(courses.get(courseNumber).getTakenQuizzes());
                                        System.out.println("Press enter to continue");
                                        String cont = scanner.nextLine();
                                    } else if (teacherChoice.equals("5")) {
                                        System.out.println("Please input name of import file");
                                        String file = scanner.nextLine();
                                        Quiz newQuiz = readTeacherQuestions(file, scanner);
                                        if (newQuiz == null) {
                                            System.out.println("File not found, cannot import quiz");
                                        } else {
                                            courses.get(courseNumber).addQuiz(newQuiz);
                                            fileObj.writeEditedCourses("courses.txt", courses);
                                            System.out.println("Quiz imported");
                                        }
                                    } else if (teacherChoice.equals("6")) {
                                        //exit
                                        break;
                                    } else {
                                        System.out.println(ERROR_MESSAGE);
                                    }
                                }
                            }
                        } else if (userTeacherChoice2.equals("3")) {
                            break;
                        } else { //handles wrong input
                            System.out.println(ERROR_MESSAGE);
                        }
                    }
                } else if (userTeacherChoice.equals("2")) {
                    String oldUsername = user.getUserName();
                    boolean editFlag = user.editAccount(scanner);
                    while (!editFlag) {
                        editFlag = user.editAccount(scanner);
                    }
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getTeacherName().equals(oldUsername)) {
                            courses.get(i).setTeacherName(user.getUserName());
                        }
                    }
                    fileObj.writeEditedCourses("courses.txt", courses);
                } else if (userTeacherChoice.equals("3")) {
                    String oldUsername = user.getUserName();
                    user.deleteAccount();
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getTeacherName().equals(oldUsername)) {
                            courses.remove(i);
                        }
                    }
                    fileObj.writeEditedCourses("courses.txt", courses);
                } else if (userTeacherChoice.equals("4")) {
                    System.out.println(THANK_YOU_MESSAGE);
                    return;
                } else {
                    System.out.println(ERROR_MESSAGE);
                }
            }
        }
        if (!user.getTeacher()) { //student portion of control flow
            while (true) {
                if (courses.isEmpty()) { //makes sure student cannot proceed if no courses exist
                    System.out.println("No courses exist. Please contact your teacher " +
                            "regarding their administrative error. Goodbye!");
                    break;
                }
                System.out.println(STUDENT_USER_MENU);
                userStudentChoice = scanner.nextLine();
                if (userStudentChoice.equals("1")) { //view courses
                    int courseChoice = 0;
                    for (int i = 0; i < Objects.requireNonNull(courses).size(); i++) {
                        System.out.printf("%d: %s\n", i + 1,
                                courses.get(i).getCourseName());
                    }
                    System.out.println("Pick a course number: "); //pick courses
                    courseChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (courseChoice > courses.size() || courseChoice <= 0) {
                        System.out.println("Please enter a valid course number. ");
                        System.out.println("Pick a course number: "); //pick courses
                        courseChoice = scanner.nextInt();
                    }
                    while (true) {
                        System.out.println(STUDENT_MENU);
                        studentChoice = scanner.nextLine();
                        if (studentChoice.equals("1")) { //take a quiz
                            System.out.println("Which quiz would you like to take?");
                            System.out.println(courses.get(courseChoice - 1).getQuizzes());
                            quizChoice = scanner.nextLine();
                            boolean isInt = quizChoice.matches("[0-9]+");
                            while (!isInt || Integer.parseInt(quizChoice) <= 0
                                    || Integer.parseInt(quizChoice) > courses.get(courseChoice - 1)
                                    .getQuizList().size()) {
                                System.out.println("Which quiz would you like to take? Please input valid number");
                                System.out.println(courses.get(courseChoice - 1).getQuizzes());
                                quizChoice = scanner.nextLine();
                                isInt = quizChoice.matches("[0-9]+");
                            }
                            Quiz takenQuiz = courses.get(courseChoice - 1).getQuizList()
                                    .get(Integer.parseInt(quizChoice) - 1).takeQuiz(user.getUserName(), scanner);
                            courses.get(courseChoice - 1).addTakenQuiz(takenQuiz);
                            fileObj.writeEditedCourses("courses.txt", courses);
                            System.out.println("Press enter when you are ready to continue.");
                            scanner.nextLine();
                        } else if (studentChoice.equals("2")) { //view quizzes taken
                            for (int i = 0; i < courses.get(courseChoice - 1).getTakenQuizList().size(); i++) {
                                if (courses.get(courseChoice - 1).getTakenQuizList()
                                        .get(i).getUsername().equals(user.getUserName())) {
                                    System.out.println(courses.get(courseChoice - 1).getStudentTakenQuizzes(i));
                                }
                            }
                            System.out.print("Press enter when you are ready to continue.");
                            //gives time for user to read before reprinting menu
                            scanner.nextLine();
                        } else if (studentChoice.equals("3")) {
                            break;
                        } else {
                            System.out.println(ERROR_MESSAGE);
                        }
                    }
                } else if (userStudentChoice.equals("2")) { //edit student account
                    String oldUsername = user.getUserName();
                    boolean editFlag = user.editAccount(scanner);
                    while (!editFlag) {
                        editFlag = user.editAccount(scanner);
                    }
                    for (int i = 0; i < Objects.requireNonNull(courses).size(); i++) {
                        for (int j = 0; j < courses.get(i).getTakenQuizList().size(); j++) {
                            if (courses.get(i).getTakenQuizList().get(j).getUsername().equals(oldUsername)) {
                                courses.get(i).getTakenQuizList().get(j).setUsername(user.getUserName());
                            }
                        }
                    }
                    fileObj.writeEditedCourses("courses.txt", courses);
                } else if (userStudentChoice.equals("3")) { //delete student account
                    String oldUsername = user.getUserName();
                    user.deleteAccount();
                    for (int i = 0; i < Objects.requireNonNull(courses).size(); i++) {
                        for (int j = 0; j < courses.get(i).getTakenQuizList().size(); j++) {
                            if (courses.get(i).getTakenQuizList().get(j).getUsername().equals(oldUsername)) {
                                courses.get(i).getTakenQuizList().remove(j);
                            }
                        }
                    }
                    fileObj.writeEditedCourses("courses.txt", courses);
                } else if (userStudentChoice.equals("4")) { //exit
                    System.out.println(THANK_YOU_MESSAGE);
                    break;
                } else {
                    System.out.println(ERROR_MESSAGE); //handles wrong input
                }
            }
        }
    }

    // Read course objects from file into array of courses for manipulation in main
    public static ArrayList<Course> readCourses(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        ArrayList<Course> courses = new ArrayList<>();
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename))) {
            Object line = reader.readObject();
            courses = (ArrayList<Course>) line;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No courses exist! Please " +
                    "create a course before proceeding. ");
            return courses;
        }

        return courses;
    }
}
