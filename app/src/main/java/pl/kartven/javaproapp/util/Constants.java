package pl.kartven.javaproapp.util;

public final class Constants {

    public static final String BACKEND_API_URL = Api.URL_5.url;
    public static final String TOPIC_MODEL_NAME = "topic_model";
    public static final String SECTION_MODEL_NAME = "section_model";
    public static final String MAIN_HIDDEN_STATE = "main_hidden_state";
    public static final String TOPIC_DETAILS_ACTIVE_FRAGMENT_STATE = "topic_details_active_fragment";

    private enum Api {
        URL_1("http://192.168.0.108:8444"),
        URL_2("http://19.16.13.171:8444"),
        URL_3("http://192.168.43.239:8444"),
        URL_4("http://192.168.8.100:8444"),
        URL_5("http://krystianus.alwaysdata.net"),
        URL_6("http://192.168.10.103:8444");


        private final String url;

        Api(String url){
            this.url = url;
        }
    }
}
