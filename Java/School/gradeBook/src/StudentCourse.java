import java.util.*;
public class StudentCourse{
    String name;
    char block;
    ArrayList<Integer> grades = new ArrayList<Integer>();
    public StudentCourse(String name, char block){
        this.name = name;
        this.block = block;
        this.grades = new ArrayList<Integer>();
    }
}