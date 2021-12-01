import java.io.*;
import java.util.ArrayList;

public class ReadWrite {

    // Read course objects from file into array of courses for manipulation in main
    public static ArrayList<Course> readCourses(String filename) throws IOException {
        java.io.File file = new java.io.File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        ArrayList<Course> courses = new ArrayList<>();
        try (ObjectInputStream reader = new ObjectInputStream
                (new FileInputStream(filename))) {
            Object line = reader.readObject();
            courses = (ArrayList<Course>) line;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No courses exist! Please " +
                    "create a course before proceeding. ");
            return courses;
        }

        return courses;
    }

    // Writes all edits to the courses array to the course file
    public static void writeEditedCourses(String filename, ArrayList<Course> courses) {
        try (ObjectOutputStream writer = new ObjectOutputStream
                (new FileOutputStream(filename, false))) {
            writer.writeObject(courses);
            writer.flush();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
