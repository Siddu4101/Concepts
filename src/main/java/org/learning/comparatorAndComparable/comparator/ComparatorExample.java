package org.learning.comparatorAndComparable.comparator;

import lombok.extern.slf4j.Slf4j;
import org.learning.comparatorAndComparable.utils.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class ComparatorExample {

    public static void main(String[] args) {
        log.info("Comparator provides a externalized compare method which can be multiple(on name, on age, on id etc..) like A v/s B");
        log.info("for custom objects we need to provide the compare method for existing classes like Integer they have default natural ordering compare methods");
        log.info("we need a Comparator Type which provides the implementation for compare method u can create as many possible comparator to sort based on a field");
        log.info("else u can provide the implementation via lambda which is more easy and avoids the boiler plate code");
        log.info("This is out side class implementation so it can be multiple (on different fields)");
        List<Student> students = new ArrayList<>();

        students.add(Student.builder().id(1).name("Sid").age(24).build());
        students.add(Student.builder().id(2).name("Raj").age(22).build());
        students.add(Student.builder().id(3).name("Viju").age(25).build());

        log.info("comparison based on age used for sorting");
        log.info("students before sort {}", students);
        List<Student> sortedStudentsAsc = students.stream().sorted(new AgeComparator()).toList();
        log.info("Sorted students based  on there age ASC{}", sortedStudentsAsc);

        List<Student> sortedStudentsDsc = students.stream().sorted(new AgeComparator().reversed()).toList();
        log.info("Sorted students based  on there age DSC{}", sortedStudentsDsc);

        log.info("comparison based on name used for sorting");

        List<Student> sortedStudentsNamesAsc = students.stream().sorted((s1,s2)-> s1.name().compareTo(s2.name())).toList();
        List<Student> sortedStudentsNamesAsc2 = students.stream().sorted(Comparator.comparing(Student::name)).toList();
        log.info("Sorted students based  on there name ASC{} or {}", sortedStudentsNamesAsc, sortedStudentsNamesAsc2);
    }
}

class AgeComparator implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o1.age() - o2.age(); //ASC order sort or u can use the
//        return Integer.compare(o1.age(), o2.age());
    }
    //and it provides the reversed method to reverse it or u can use the o2.age() - o1.age(); that also works fine
}
