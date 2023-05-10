package pl.kartven.javaproapp;

public final class Constants {

    public static final String BACKEND_API_URL = Api.URL_5.url;

    private enum Api {
        URL_1("http://192.168.0.108:8444"),
        URL_2("http://19.16.13.171:8444"),
        URL_3("http://192.168.43.239:8444"),
        URL_4("http://192.168.8.100:8444"),
        URL_5("http://krystianus.alwaysdata.net");


        private final String url;

        Api(String url){
            this.url = url;
        }
    }
}
