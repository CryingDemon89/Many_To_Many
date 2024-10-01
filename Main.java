package org.example.kr;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication



public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Add students to courses
            addStudentsToCourses(session);

            // Print courses and students before removal
            System.out.println("Before removal:");
            printCoursesAndStudents(session);

            // Remove student from course
            removeStudentFromCourse(session);

            // Print courses and students after removal
            System.out.println("\nAfter removal:");
            printCoursesAndStudents(session);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            sessionFactory.close();
        }
    }

    private static void addStudentsToCourses(Session session) {
        Course course1 = new Course("Math");
        Course course2 = new Course("Science");
        Course course3 = new Course("History");
        Course course4 = new Course("English");
        Course course5 = new Course("Physics");
        Course course6 = new Course("Chemistry");
        Course course7 = new Course("Biology");
        Course course8 = new Course("Geology");
        Course course9 = new Course("Philosophy");
        Course course10 = new Course("Computer Science");

        Student student1 = new Student("Alice");
        Student student2 = new Student("Bob");
        Student student3 = new Student("Charlie");
        Student student4 = new Student("David");
        Student student5 = new Student("Eve");
        Student student6 = new Student("Frank");
        Student student7 = new Student("Grace");
        Student student8 = new Student("Henry");
        Student student9 = new Student("Ivy");
        Student student10 = new Student("John");


        student1.addCourse(course1);
        student1.addCourse(course2);
        student1.addCourse(course3);
        student1.addCourse(course4);

        student2.addCourse(course2);
        student2.addCourse(course3);
        student2.addCourse(course4);
        student2.addCourse(course5);

        session.saveOrUpdate(student1);
        session.saveOrUpdate(student2);
        session.saveOrUpdate(student3);
        session.saveOrUpdate(student4);
        session.saveOrUpdate(student5);
        session.saveOrUpdate(student6);
        session.saveOrUpdate(student7);
        session.saveOrUpdate(student8);
        session.saveOrUpdate(student9);
        session.saveOrUpdate(student10);

        session.saveOrUpdate(course1);
        session.saveOrUpdate(course2);
        session.saveOrUpdate(course3);
        session.saveOrUpdate(course4);
        session.saveOrUpdate(course5);
        session.saveOrUpdate(course6);
        session.saveOrUpdate(course7);
        session.saveOrUpdate(course8);
        session.saveOrUpdate(course9);
        session.saveOrUpdate(course10);
    }

    private static void removeStudentFromCourse(Session session) {
        // Удаляем студента по имени "Alice" из курса "Math"
        String studentName = "Alice";
        String courseName = "Math";

        // Получаем студента
        Student student = session.createQuery("FROM Student WHERE name = :name", Student.class)
                .setParameter("name", studentName)
                .uniqueResult();

        // Получаем курс
        Course course = session.createQuery("FROM Course WHERE name = :name", Course.class)
                .setParameter("name", courseName)
                .uniqueResult();

        if (student != null && course != null) {
            student.removeCourse(course); // Удаляем курс у студента
            session.saveOrUpdate(student); // Обновляем студента в сессии
            session.saveOrUpdate(course);   // Обновляем курс в сессии
            System.out.println(studentName + " был удален из курса " + courseName);
        } else {
            System.out.println("Студент или курс не найдены.");
        }
    }

    private static void printCoursesAndStudents(Session session) {
        List<Course> courses = session.createQuery("FROM Course", Course.class).list();

        for (Course course : courses) {
            System.out.println("Курс: " + course.getName());
            System.out.println("Записанные студенты:");
            for (Student student : course.getStudents()) {
                System.out.println(" - " + student.getName());
            }
            System.out.println(); // Пустая строка для разделения курсов
        }
    }
}