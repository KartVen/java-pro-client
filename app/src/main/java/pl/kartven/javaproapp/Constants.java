package pl.kartven.javaproapp;

public final class Constants {

    public static final String BACKEND_API_URL = Api.CO.url;

    private enum Api {
        AK("http://192.168.0.108:8444"),
        CO("http://19.16.13.171:8444"),
        PH("http://192.168.43.239:8444"),
        TP("http://192.168.8.100:8444");

        private final String url;

        Api(String url){
            this.url = url;
        }
    }
}
