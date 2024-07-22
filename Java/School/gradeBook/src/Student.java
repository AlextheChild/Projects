import java.util.*;
public class Student {
    String name;
    int yog;
    ArrayList<StudentCourse> StudentCourses = new ArrayList<StudentCourse>();
    public Student(String name, int yog){
        this.name = name;
        this.yog = yog;
        this.StudentCourses = new ArrayList<StudentCourse>();
    }
}