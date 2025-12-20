
# Comparator & Comparable in Java 🆚

## 📑 Navigation

- [Overview & Differences 🧐](#overview)
- [Comparable Interface 🏷️](#comparable)
- [Comparator Interface 🧑‍⚖️](#comparator)

<details>
<summary id="overview"><strong>Overview & Differences 🧐</strong></summary>

Java provides two interfaces for sorting objects: <b>Comparable</b> and <b>Comparator</b>.

| Feature         | Comparable 🏷️ | Comparator 🧑‍⚖️ |
|-----------------|:-------------:|:---------------:|
| Package         | java.lang      | java.util       |
| Method          | compareTo()    | compare()       |
| Sorting logic   | In the class   | Outside class   |
| Multiple orders | ❌             | ✅              |
| Used for        | Natural order  | Custom order    |

**When to use?**
- Use <b>Comparable</b> for default/natural ordering (e.g., String, Integer).
- Use <b>Comparator</b> for custom or multiple sorting strategies.

</details>

<details>
<summary id="comparable"><strong>Comparable Interface 🏷️</strong></summary>

🔗Reference code: [ComparableExample](./ComparableExample.java)

The <b>Comparable</b> interface allows a class to define its natural ordering by implementing the `compareTo()` method.

- 🧮 <b>Built-in Comparable:</b> Classes like <code>Integer</code> have natural ordering by default, so you don't need to implement <code>Comparable</code> yourself.
- 🔑 <b>Natural Order Sorting:</b> The <code>compareTo()</code> method provides the default sorting key for the class.
- 🏷️ <b>Single Sorting Key:</b> Only one way to sort (natural order) can be defined within the class.
- 🏗️ <b>Implementation Location:</b> Sorting logic is implemented inside the class itself.
```java
public record Student(int id, String name, int age) implements Comparable<Student>{
   @Override
   public int compareTo(Student other) {
      return Integer.compare(this.age, other.age); // Natural order: by age ASC
   }
}
```

**Usage Example:**

```java
List<Student> students = new ArrayList<>();
students.add(Student.builder().id(1).name("Sid").age(24).build());
students.add(Student.builder().id(2).name("Raj").age(22).build());
students.add(Student.builder().id(3).name("Viju").age(25).build());

List<Student> sortedStudentBasedOnAgeAsc = students.stream().sorted().toList();
result : [Student[id=2, name=Raj, age=22], Student[id=1, name=Sid, age=24], Student[id=3, name=Viju, age=25]]

```

> ℹ️ <b>Note:</b>  Only one natural order can be defined per class.

</details>

<details>
<summary id="comparator"><strong>Comparator Interface 🧑‍⚖️</strong></summary>

🔗Reference code: [ComparatorExample](./ComparatorExample.java)

The <b>Comparator</b> interface allows you to define multiple custom sorting strategies outside the class.

```java
List<Student> students = new ArrayList<>();
students.add(Student.builder().id(1).name("Sid").age(24).build());
students.add(Student.builder().id(2).name("Raj").age(22).build());
students.add(Student.builder().id(3).name("Viju").age(25).build());

// Sort by age ASC using AgeComparator
List<Student> sortedStudentsAsc = students.stream().sorted(new AgeComparator()).toList();
System.out.println("Sorted by age ASC: " + sortedStudentsAsc);

// Sort by age DESC using reversed comparator
List<Student> sortedStudentsDesc = students.stream().sorted(new AgeComparator().reversed()).toList();
System.out.println("Sorted by age DESC: " + sortedStudentsDesc);

// Sort by name ASC using lambda
List<Student> sortedStudentsNamesAsc = students.stream().sorted((s1, s2) -> s1.name().compareTo(s2.name())).toList();
System.out.println("Sorted by name ASC: " + sortedStudentsNamesAsc);

// Sort by name ASC using Comparator.comparing
List<Student> sortedStudentsNamesAsc2 = students.stream().sorted(Comparator.comparing(Student::name)).toList();
System.out.println("Sorted by name ASC (method ref): " + sortedStudentsNamesAsc2);


class AgeComparator implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o1.age() - o2.age(); //ASC order sort or u can use the
//        return Integer.compare(o1.age(), o2.age());
    }
    //and it provides the reversed method to reverse it or u can use the o2.age() - o1.age(); that also works fine
}
```

</details>


