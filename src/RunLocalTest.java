import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * RunLocalTest test cases that test Main Class and Question Class.
 * Important!!: The file that stores User information (User.txt) and the file
 * that stores courses (courses.txt) must be empty before running.
 * <p>
 * Purdue University -- CS18000 -- Fall 2021</p>
 *
 * @author Evans Tang L18
 * @version November 16, 2021
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(RunLocalTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Tests ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }


    @Test(timeout = 1000)
    //tests whether a teacher can create an account
    public void testTeacherCreateAccount() {
        // Set the input
        // Separate each input with a newline (\n).
        String input = "2\n" +
                "purduepete\n" +
                "boilerup\n" +
                "yes\n" +
                "purduepete\n" +
                "boilerup\n" +
                "yes\n" +
                "4\n";

        // Pair the input with the expected result
        String expected = "Welcome to the online quiz tool!\n" +
                "1. Sign In\n" +
                "2. Create an Account\n" +
                "3. Exit quiz tool\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "register successful\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "login success\n" +
                "No courses exist! Please create a course before proceeding. \n" +
                "1. Choose/Create Course\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "Thank you for using our online quiz tool!\n\n";

        // Runs the program with the input values
        // Replace TestProgram with the name of the class with the main method
        receiveInput(input);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        assertEquals("Error",
                expected.trim(), stuOut.trim());
    }

    @Test(timeout = 1000)
    //tests whether a student can create an account
    public void testStudentCreateAccount() {
        String input = "2\n" +
                "a\n" +
                "1\n" +
                "no\n" +
                "a\n" +
                "1\n" +
                "no\n";

        String expected = "Welcome to the online quiz tool!\n" +
                "1. Sign In\n" +
                "2. Create an Account\n" +
                "3. Exit quiz tool\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "register successful\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "login success\n" +
                "No courses exist! Please create a course before proceeding. \n" +
                "No courses exist. Please contact your teacher regarding their administrative error. Goodbye!\n\n";

        receiveInput(input);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        assertEquals("Error",
                expected.trim(), stuOut.trim());
    }

    @Test(timeout = 1000)
    //tests the question class method readStudentAnswer()
    public void testQuestionClass() {
        try {
            ArrayList<String> choices = new ArrayList<>();
            Question question = new Question("what color is the sky?", choices, "sky", "b");
            String input = question.readStudentAnswer("QuestionTest.txt");
            String expected = "b";

            // Runs the program with the input values
            receiveInput(input);
            question.readStudentAnswer("QuestionTest.txt");

            // Retrieves the output from the program
            String actual = "b";
            assertEquals(actual, input);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(timeout = 1000)
    //tests whether a student with an existing account can log in
    public void signInStudent() {
        String input = "1\n" +
                "a\n" +
                "1\n" +
                "no\n" + "4\n";
        String expected = "Welcome to the online quiz tool!\n" +
                "1. Sign In\n" +
                "2. Create an Account\n" +
                "3. Exit quiz tool\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "login success\n" +
                "1. View Courses\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "Thank you for using our online quiz tool!\n\n";

        receiveInput(input);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        assertEquals("Error",
                expected.trim(), stuOut.trim());
    }

    @Test(timeout = 1000)
    //tests whether a teacher with an existing account can log in
    public void signInTeacher() {
        String input = "1\n" +
                "purduepete\n" +
                "boilerup\n" +
                "yes\n" +
                "4\n";
        String expected = "Welcome to the online quiz tool!\n" +
                "1. Sign In\n" +
                "2. Create an Account\n" +
                "3. Exit quiz tool\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "login success\n" +
                "1. Choose/Create Course\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "Thank you for using our online quiz tool!\n\n";

        receiveInput(input);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        assertEquals("Error",
                expected.trim(), stuOut.trim());
    }

    @Test(timeout = 1000)
    //tests whether a teacher can create a course and quiz
    public void makeACourseAndQuiz() {
        String input = "1\n" + "purduepete\n" +
                "boilerup\n" + "yes\n" +
                "1\n" + "1\n" + "test\n" +
                "3\n" + "1\n" + "2\n" + "1\n" + "1\n" +
                "testing quiz\n" + "??\n" + "hello\n" +
                "2\n" + "1\n" + "2\n" + "2\n" + "6\n" + "3\n" +
                "4\n";
        String expected = "Welcome to the online quiz tool!\n" +
                "1. Sign In\n" +
                "2. Create an Account\n" +
                "3. Exit quiz tool\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "login success\n" +
                "No courses exist! Please create a course before proceeding. \n" +
                "1. Choose/Create Course\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "1. Create Course\n" +
                "2. Choose Existing Course\n" +
                "3. Go back to previous menu\n" +
                "Please input course name: \n" +
                "1. Create Course\n" +
                "2. Choose Existing Course\n" +
                "3. Go back to previous menu\n" +
                "1. Choose/Create Course\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "1. Create Course\n" +
                "2. Choose Existing Course\n" +
                "3. Go back to previous menu\n" +
                "1: test\n" +
                "\n" +
                "Pick a course number:\n" +
                "1. Create Quiz\n" +
                "2. Edit Quiz\n" +
                "3. Delete Quiz \n" +
                "4. View Taken Quizzes\n" +
                "5. Import Quiz\n" +
                "6. Exit teacher menu\n" +
                "Please enter the name of your quiz:\n" +
                "Please enter your question:\n" +
                "Please enter your question's answer choice:\n" +
                "Would you like to add another answer choice? \n" +
                "1. Yes \n" +
                "2. No\n" +
                "Please enter the correct answer for your question:\n" +
                "Would you like to create another question? \n" +
                "1. Yes \n" +
                "2. No\n" +
                "Do you want to randomize the order of your quiz's questions? \n" +
                "1. Yes \n" +
                "2. No\n" +
                "Your quiz has been created!\n" +
                "1. Create Quiz\n" +
                "2. Edit Quiz\n" +
                "3. Delete Quiz \n" +
                "4. View Taken Quizzes\n" +
                "5. Import Quiz\n" +
                "6. Exit teacher menu\n" +
                "1. Create Course\n" +
                "2. Choose Existing Course\n" +
                "3. Go back to previous menu\n" +
                "1. Choose/Create Course\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "Thank you for using our online quiz tool!\n\n";
        receiveInput(input);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        assertEquals("Error",
                expected.trim(), stuOut.trim());
    }

    @Test(timeout = 1000)
    //tests whether a student can take a quiz in a course
    public void takeAQuiz() {
        String input = "1\n" + "a\n" + "1\n" + "no\n" +
                "1\n" + "1\n" + "1\n" + "1\n" + "no\n" +
                "1\n" + "\n" + "3\n" + "4\n";
        String expected = "Welcome to the online quiz tool!\n" +
                "1. Sign In\n" +
                "2. Create an Account\n" +
                "3. Exit quiz tool\n" +
                "Input your username:\n" +
                "Input your password:\n" +
                "Are you a teacher(yes or no):\n" +
                "login success\n" +
                "1. View Courses\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "1: test\n" +
                "Pick a course number: \n" +
                "1. Take a quiz\n" +
                "2. View quizzes taken\n" +
                "3. Exit student menu\n" +
                "Which quiz would you like to take?\n" +
                "1. testing quiz\n\n" +
                "testing quiz\n" +
                "Question 1: ??\n" +
                "Choices:\n" +
                "1. hello\n" +
                "Submit answer as file?\n" +
                "Enter yes or no:\n" +
                "Please input your choice\n" +
                "Press enter when you are ready to continue.\n" +
                "1. Take a quiz\n" +
                "2. View quizzes taken\n" +
                "3. Exit student menu\n" +
                "1. View Courses\n" +
                "2. Edit Account\n" +
                "3. Delete Account\n" +
                "4. Exit quiz tool\n" +
                "Thank you for using our online quiz tool!\n\n";
        // Runs the program with the input values
        // Replace TestProgram with the name of the class with the main method
        receiveInput(input);
        try {
            Main.main(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieves the output from the program
        String stuOut = getOutput();

        // Trims the output and verifies it is correct.
        assertEquals("Error",
                expected.trim(), stuOut.trim());
    }

}

