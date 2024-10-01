package org.example.kr;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
//import java.lang.module.Configuration;

//public class HibernateUtil {
//    private static StandardServiceRegistry registry;
//    private static SessionFactory sessionFactory;
//
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                // Create registry
//                registry = new StandardServiceRegistryBuilder().configure().build();
//
//                // Create MetadataSources
//                MetadataSources sources = new MetadataSources(registry);
//
//                // Create Metadata
//                Metadata metadata = sources.getMetadataBuilder().build();
//
//                // Create SessionFactory
//                sessionFactory = metadata.getSessionFactoryBuilder().build();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                if (registry != null) {
//                    StandardServiceRegistryBuilder.destroy(registry);
//                }
//            }
//        }
//        return sessionFactory;
//    }
//
//    public static void shutdown() {
//        if (registry != null) {
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }
//}

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null || sessionFactory.isClosed()) {
                    Configuration cfg = new Configuration().configure();

                    // Добавляем маппинг для Student и Course классов
                    cfg.addAnnotatedClass(Student.class);
                    cfg.addAnnotatedClass(Course.class);

                    registry = new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties()).build();

                    try {
                        sessionFactory = cfg.buildSessionFactory(registry);
                    } catch (Throwable ex) {
                        System.err.println("Initial SessionContext creation failed." + ex);
                        throw new ExceptionInInitializerError(ex);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
            registry = null;
        }
    }
}