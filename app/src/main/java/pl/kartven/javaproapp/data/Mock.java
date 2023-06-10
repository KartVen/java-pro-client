package pl.kartven.javaproapp.data;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.data.model.domain.TopicDomain;

public class Mock {
    private Mock() {
    }

    public static List<TopicDomain> getTopics(){
        ArrayList<TopicDomain> list = new ArrayList<>();
        list.add(new TopicDomain(1L, "Technologia Java, JVM. Platformy.", "Język Java. Klasy, pakiety, moduły."));
        list.add(new TopicDomain(2L, "Java SE. Język Java, podstawy, OOP.", "Klasy, pola, metody, obiekty."));
        list.add(new TopicDomain(3L, "Typy sparametryzowane. Biblioteki AWT, Swing.", "Budowa GUI."));
        list.add(new TopicDomain(4L, "Interfejsy, typy, implementacja. AWT, Swing.", "Obsługa zdarzeń cz. 1"));
        list.add(new TopicDomain(5L, "Klasy i interfejsy zagnieżdżone. AWT, Swing.", "Obsługa zdarzeń cz. 2"));
        list.add(new TopicDomain(6L, "Wyrażenia Lambda. JavaFX cz. 1, budowa aplikacji.", null));
        list.add(new TopicDomain(7L, "Java FX cz. 2 – CSS, FXML. Aplikacje MVC, hybrydowe.", null));
        list.add(new TopicDomain(8L, "Java Stream API. Java Embedded. Java Android API. Budowa aplikacji.", null));
        list.add(new TopicDomain(9L, "Java SE&EE–JDBC. Java Persistence Spring + Hibernate. Java Cloud", null));
        list.add(new TopicDomain(10L, "JEE, Enterprise App. EJB. Web Services JAX-WS, JAX-RS.", null));
        return list;
    }
}
