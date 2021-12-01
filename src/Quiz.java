import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Date;

/**
 * Project 4 Group 57 - Quiz Class
 * <p>
 * Quiz class creates quiz objects containing arraylists
 * of answers and Question objects; quizzes can be randomized
 * and each student submission is timestamped. Also allows
 * student to take a quiz, and as they are taken they are added into
 * a takenquizzes arraylist in Course class that allows the teacher
 * and student to view submissions.
 *
 * @author Jade Gu, section L18
 * @version 11/14/2021
 */
public class Quiz implements java.io.Serializable {
    private ArrayList<Question> questions;
    private String quizName;
    private boolean randomization;
    private ArrayList<String> answers;
    private String username;
    private String timestamp;

    public Quiz(ArrayList<Question> questions,
                String quizName, boolean randomization) {
        this.questions = new ArrayList<Question>();
        this.quizName = quizName;
        this.randomization = randomization;
    }

    public Quiz(ArrayList<Question> questions,
                String quizName, ArrayList<String> answers,
                String username, String timestamp) {
        this.questions = new ArrayList<Question>();
        this.quizName = quizName;
        this.answers = new ArrayList<String>();
        this.username = username;
        this.timestamp = timestamp;

    }

    /*
    empty quiz constructor for
    constructing new quiz object in method takeQuiz()
     */
    public Quiz() {
        this.questions = new ArrayList<Question>();
        this.quizName = "";
        this.answers = new ArrayList<String>();
        this.username = "";
        this.timestamp = "";
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public boolean isRandomization() {
        return randomization;
    }

    public void setRandomization(boolean randomization) {
        this.randomization = randomization;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        Date date = new Date();
        timestamp = String.format(formatter.format(date));
        return timestamp;
    }

    public String getTime() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Quiz takeQuiz(String name, Scanner scan) {
        Quiz quiz = new Quiz(); //create new empty quiz object
        if (isRandomization()) { //if randomization
            ArrayList<Question> shuffledQuestions = getQuestions();
            Collections.shuffle(shuffledQuestions);
            for (int i = 0; i < shuffledQuestions.size(); i++) {
                Collections.shuffle(shuffledQuestions.get(i).getChoicesArray());
            }

            System.out.println(getQuizName());
            quiz.setQuizName(getQuizName());
            quiz.setUsername(name); //populate quiz object with designated username
            String currentChoice = "";
            for (int j = 0; j < shuffledQuestions.size(); j++) {
                System.out.printf("Question %d: %s", j + 1, shuffledQuestions.get(j).toString());
                System.out.println("Submit answer as file?");
                System.out.println("Enter yes or no:");
                currentChoice = scan.nextLine();
                while (!currentChoice.equalsIgnoreCase("yes") &&
                        !currentChoice.equalsIgnoreCase("no")) {
                    System.out.println("Invalid Selection, try again\n");
                    System.out.printf("Question %d: %s", j + 1, questions.get(j).toString());
                    System.out.println("Submit answer as file?");
                    System.out.println("Enter yes or no:");
                    currentChoice = scan.nextLine();
                }
                if (currentChoice.equalsIgnoreCase("yes")) {
                    System.out.println("Please enter file name");
                    currentChoice = shuffledQuestions.get(j).readStudentAnswer(scan.nextLine());
                    while (currentChoice == null || Integer.parseInt(currentChoice) >
                            shuffledQuestions.get(j).getChoicesArray().size() ||
                            Integer.parseInt(currentChoice) <= 0) {
                        System.out.println("Please choose a valid file");
                        System.out.println("Please enter file name");
                        currentChoice = shuffledQuestions.get(j).readStudentAnswer(scan.nextLine());
                    }
                    shuffledQuestions.get(j).setStudentAnswer(currentChoice);
                    answers.add(currentChoice);
                } else {
                    System.out.println("Please input your choice");
                    String currentAnswer = scan.nextLine();
                    boolean isInt = currentAnswer.matches("[0-9]+");
                    while (Integer.parseInt(currentAnswer) > shuffledQuestions.get(j).getChoicesArray().size() ||
                            Integer.parseInt(currentAnswer) <= 0 || !isInt) {
                        System.out.println("Please choose a valid choice");
                        currentAnswer = scan.nextLine();
                        isInt = currentAnswer.matches("[0-9]+");
                    }
                    shuffledQuestions.get(j).setStudentAnswer(currentAnswer);
                    answers.add(currentAnswer);
                }
                quiz.setAnswers(answers); //populate empty quiz object with answer arraylist
            }
            quiz.setTimestamp(getTimestamp());
            return quiz;
        } else { //repeat for non randomized quiz
            System.out.println(getQuizName());
            quiz.setQuizName(getQuizName());
            quiz.setUsername(name);
            String currentChoice = "";
            for (int j = 0; j < questions.size(); j++) {
                System.out.printf("Question %d: %s", j + 1, questions.get(j).toString());
                System.out.println("Submit answer as file?");
                System.out.println("Enter yes or no:");
                currentChoice = scan.nextLine();
                while (!currentChoice.equalsIgnoreCase("yes") &&
                        !currentChoice.equalsIgnoreCase("no")) {
                    System.out.println("Invalid Selection, try again\n");
                    System.out.printf("Question %d: %s", j + 1, questions.get(j).toString());
                    System.out.println("Submit answer as file?");
                    System.out.println("Enter yes or no:");
                    currentChoice = scan.nextLine();
                }
                if (currentChoice.equalsIgnoreCase("yes")) {
                    System.out.println("Please enter file name");
                    currentChoice = questions.get(j).readStudentAnswer(scan.nextLine());
                    while (currentChoice == null || Integer.parseInt(currentChoice) >
                            questions.get(j).getChoicesArray().size() ||
                            Integer.parseInt(currentChoice) <= 0) {
                        System.out.println("Please choose a valid file");
                        System.out.println("Please enter file name");
                        currentChoice = questions.get(j).readStudentAnswer(scan.nextLine());
                    }
                    questions.get(j).setStudentAnswer(currentChoice);
                    answers.add(currentChoice);
                } else {
                    System.out.println("Please input your choice");
                    String currentAnswer = scan.nextLine();
                    boolean isInt = currentAnswer.matches("[0-9]+");
                    while (Integer.parseInt(currentAnswer) > questions.get(j).getChoicesArray().size()
                            || Integer.parseInt(currentAnswer) <= 0 || !isInt) {
                        System.out.println("Please choose a valid choice");
                        currentAnswer = scan.nextLine();
                        isInt = currentAnswer.matches("[0-9]+");
                    }
                    questions.get(j).setStudentAnswer(currentAnswer);
                    answers.add(currentAnswer);
                }
                quiz.setAnswers(answers);
            }
            quiz.setTimestamp(getTimestamp());
            //add timestamp to empty quiz object
            return quiz;
        }
    }

    public static Quiz readTeacherQuestions(String filename, Scanner scanner) {
        //creation of new quiz object via reading from file
        Quiz newQuiz = new Quiz();
        int createAnotherChoice = 0;
        int createAnotherQuestion = 0;
        int questRand = 0;
        String question;
        String choice;
        String answer;
        boolean flag;

        ArrayList<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = "";
            int count = 0;
            while ((line = br.readLine()) != null) {
                ArrayList<String> choices = new ArrayList<>();
                count++;
                if (count == 1) {
                    newQuiz.setQuizName(line);
                } else {
                    question = line;

                    do {
                        System.out.println("Please enter your question's answer choice:");
                        choice = scanner.nextLine();
                        choices.add(choice);
                        System.out.println("Would you like to add another answer choice? \n1. Yes \n2. No");
                        createAnotherChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (createAnotherChoice == 1) {
                            flag = true;
                        } else if (createAnotherChoice == 2) {
                            flag = false;
                        } else {
                            System.out.println("Please enter a valid number.");
                            flag = false;
                        }
                    } while (flag);
                    System.out.println("Please enter the correct answer for your question:");
                    answer = scanner.nextLine();

                    Question questionObj = new Question(question, choices, newQuiz.getQuizName(), answer);
                    questions.add(questionObj);
                    System.out.println("Next question");
                }
            }
        } catch (Exception e) {
            System.out.println("File Not Found");
            return null;
        }
        do {
            System.out.println("Do you want to randomize the order of your quiz's " +
                    "questions? \n1. Yes \n2. No");
            questRand = scanner.nextInt();
            scanner.nextLine();
            if (questRand == 1) {
                newQuiz.setQuestions(questions);
                newQuiz.setRandomization(true);
            } else if (questRand == 2) {
                newQuiz.setQuestions(questions);
                newQuiz.setRandomization(false);
            } else {
                System.out.println("Please enter a valid number.");
            }
        } while (questRand != 1 && questRand != 2);
        return newQuiz;
    }

}
