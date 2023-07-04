package pl.kartven.javaproapp.data;

import java.util.ArrayList;
import java.util.List;

import pl.kartven.javaproapp.data.model.api.request.LoginDto;
import pl.kartven.javaproapp.data.model.api.request.RegisterDto;
import pl.kartven.javaproapp.data.model.api.request.TopicReqApi;
import pl.kartven.javaproapp.data.model.domain.AuthDomain;
import pl.kartven.javaproapp.data.model.domain.CodeDomain;
import pl.kartven.javaproapp.data.model.domain.LinkDomain;
import pl.kartven.javaproapp.data.model.domain.QuestionDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDetailsDomain;
import pl.kartven.javaproapp.data.model.domain.QuizDomain;
import pl.kartven.javaproapp.data.model.domain.SectionDomain;
import pl.kartven.javaproapp.data.model.domain.SlideDomain;
import pl.kartven.javaproapp.data.model.domain.TopicDomain;
import pl.kartven.javaproapp.utils.utility.Resource;
import pl.kartven.javaproapp.utils.utility.Resource.Success;

public class MainRepositoryMock extends MainRepository {
    @Override
    public Resource<List<TopicDomain>> getTopics() {
        ArrayList<TopicDomain> mock = new ArrayList<>();

        mock.add(new TopicDomain(1L, "Technologia Java, JVM. Platformy.", "Język Java. Klasy, pakiety, moduły."));
        mock.add(new TopicDomain(2L, "Java SE. Język Java, podstawy, OOP.", "Klasy, pola, metody, obiekty."));
        mock.add(new TopicDomain(3L, "Typy sparametryzowane. Biblioteki AWT, Swing.", "Budowa GUI."));
        mock.add(new TopicDomain(4L, "Interfejsy, typy, implementacja. AWT, Swing.", "Obsługa zdarzeń cz. 1"));
        mock.add(new TopicDomain(5L, "Klasy i interfejsy zagnieżdżone. AWT, Swing.", "Obsługa zdarzeń cz. 2"));
        mock.add(new TopicDomain(6L, "Wyrażenia Lambda. JavaFX cz. 1, budowa aplikacji.", null));
        mock.add(new TopicDomain(7L, "Java FX cz. 2 – CSS, FXML. Aplikacje MVC, hybrydowe.", null));
        mock.add(new TopicDomain(8L, "Java Stream API. Java Embedded. Java Android API. Budowa aplikacji.", null));
        mock.add(new TopicDomain(9L, "Java SE&EE–JDBC. Java Persistence Spring + Hibernate. Java Cloud", null));
        mock.add(new TopicDomain(10L, "JEE, Enterprise App. EJB. Web Services JAX-WS, JAX-RS.", null));

        return topics = new Success<>(mock);
    }

    @Override
    public Resource<AuthDomain> getAuthData(LoginDto loginDto) {
        return authData = new Success<>(new AuthDomain(
                1L, "kartven",
                "krystian.kielbasa@hotmail.com",
                "",
                ""
        ));
    }

    @Override
    public Resource<AuthDomain> getAuthData(RegisterDto registerDto) {
        return getAuthData((LoginDto) registerDto);
    }

    @Override
    public Resource<List<TopicDomain>> getMyTopics() {
        ArrayList<TopicDomain> mock = new ArrayList<>();

        mock.add(new TopicDomain(8L, "Java Stream API. Java Embedded. Java Android API. Budowa aplikacji.", null));
        mock.add(new TopicDomain(9L, "Java SE&EE–JDBC. Java Persistence Spring + Hibernate. Java Cloud", null));
        mock.add(new TopicDomain(10L, "JEE, Enterprise App. EJB. Web Services JAX-WS, JAX-RS.", null));

        return myTopics = new Success<>(mock);
    }

    @Override
    public Resource<List<SectionDomain>> getSectionsOfTopic(Long id) {
        ArrayList<SectionDomain> mock = new ArrayList<>();

        mock.add(new SectionDomain(1L, "Wprowadzenie"));
        mock.add(new SectionDomain(2L, "Podstawy programowwania"));

        return sectionsOfTopics = new Success<>(mock);
    }

    @Override
    public Resource<List<QuizDomain>> getQuizzesOfTopic(Long id) {
        ArrayList<QuizDomain> mock = new ArrayList<>();

        mock.add(new QuizDomain(1L, "Quiz wprowadzający", "Quiz z podstaw programowania"));
        mock.add(new QuizDomain(1L, "Quiz testowy", "Quiz do sprawdzenia wiedzy"));

        return quizzesOfTopic = new Success<>(mock);
    }

    @Override
    public Resource<List<CodeDomain>> getCodesOfSection(Long id) {
        ArrayList<CodeDomain> mock = new ArrayList<>();

        mock.add(new CodeDomain(1L, "Kod1.java", "public class Main { /* kod */ }"));
        mock.add(new CodeDomain(2L, "Kod2.java", "<html><head></head><body>/* kod */</body></html>"));

        return codesOfSection = new Success<>(mock);
    }

    @Override
    public Resource<List<QuestionDomain>> getQuestionsOfQuiz(Long id) {
        ArrayList<QuestionDomain> mock = new ArrayList<>();

        ArrayList<QuestionDomain.Answer> q1 = new ArrayList<>();
        q1.add(new QuestionDomain.Answer(1L, "Symboliczne oznaczenie miejsca w pamięci.", true));
        q1.add(new QuestionDomain.Answer(1L, "Instrukcja warunkowa w Javie.", false));
        q1.add(new QuestionDomain.Answer(1L, "Zbiór danych przechowywanych w pamięci.", true));
        q1.add(new QuestionDomain.Answer(1L, "Wyświetlanie tekstu na ekranie.", false));

        mock.add(new QuestionDomain(1L, "Co to jest zmienna?", q1));

        ArrayList<QuestionDomain.Answer> q2 = new ArrayList<>();
        q2.add(new QuestionDomain.Answer(1L, "start, stop, pause, reset.", false));
        q2.add(new QuestionDomain.Answer(1L, "int, double, boolean, char itd.", true));
        q2.add(new QuestionDomain.Answer(1L, "if, else, for, while.", true));
        q2.add(new QuestionDomain.Answer(1L, "true, false, null.", false));

        mock.add(new QuestionDomain(1L, "Podstawowe typy danych?", q2));

        return questionsOfQuiz = new Success<>(mock);
    }

    @Override
    public Resource<QuizDetailsDomain> getQuizDetails(Long id) {
        QuizDetailsDomain mock = new QuizDetailsDomain(
                1L, "Quiz wprowadzający", "Quiz z podstaw programowania",
                new QuizDetailsDomain.Topic(1L, "Technologia Java, JVM. Platformy."),
                3L, new QuizDetailsDomain.UserApi(1L, "example_name")
        );

        return quizDetails = new Success<>(mock);
    }

    @Override
    public Resource<List<LinkDomain>> getLinksOfSection(Long id) {
        ArrayList<LinkDomain> mock = new ArrayList<>();

        mock.add(new LinkDomain(1L, "Link 1", "http://example.com/link1"));
        mock.add(new LinkDomain(2L, "Link 2", "http://example.com/link2"));

        return linksOfSection = new Success<>(mock);
    }

    @Override
    public Resource<List<SlideDomain>> getSlidesOfTopic(Long topicId, Integer page, Integer size) {
        return slidesOfTopic;
    }

    @Override
    public Resource<Void> postTopic(TopicReqApi topicReqApi) {
        return new Success<>(null);
    }
}
