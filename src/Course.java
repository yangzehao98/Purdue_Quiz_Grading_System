
import java.util.ArrayList;

/**
 * Project 4 Group 57 - Course Class
 * <p>
 * Course class creates course objects which contain Quiz and Question objects,
 * allowing the saving and manipulation of them in the Main class
 *
 * @author Evan Glenn, section L18
 * @version 11/14/2021
 */

public class Course implements java.io.Serializable {
    private String teacherName;
    private ArrayList<Quiz> quizzes;
    private String courseName;
    private ArrayList<Quiz> takenQuizzes;

    public Course(String teacherName, String courseName, ArrayList<Quiz> quizzes, ArrayList<Quiz> takenQuizzes) {
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.quizzes = new ArrayList<Quiz>();
        this.takenQuizzes = new ArrayList<Quiz>();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    // Formatted output for the Quizzes ArrayList
    public String getQuizzes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < quizzes.size(); i++) {
            sb.append(i + 1).append(". ").append(quizzes.get(i).getQuizName()).append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Quiz> getQuizList() {
        return this.quizzes;
    }

    public ArrayList<Quiz> getTakenQuizList() {
        return this.takenQuizzes;
    }
    // Formatted output for the takenQuizzes ArrayList, this method is used by teachers
    public String getTakenQuizzes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < takenQuizzes.size(); i++) {
            sb.append(takenQuizzes.get(i).getQuizName() + "-" + takenQuizzes.get(i).getUsername() +
                    "\nAnswers: " + takenQuizzes.get(i).getAnswers()
                    + "\nTime of Submission: " + takenQuizzes.get(i).getTime()).append("\n");
        }
        return sb.toString();
    }
    // Formatted output for the takenQuizzes ArrayList, it displays only the quizzes taken by the student user
    public String getStudentTakenQuizzes(int number) {
        StringBuilder sb = new StringBuilder();

        sb.append(takenQuizzes.get(number).getQuizName() + "-" + takenQuizzes.get(number).getUsername() +
                "\nAnswers: " + takenQuizzes.get(number).getAnswers()
                + "\nTime of Submission: " + takenQuizzes.get(number).getTime()).append("\n");

        return sb.toString();
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    public void addTakenQuiz(Quiz takenQuiz) {
        takenQuizzes.add(takenQuiz);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
