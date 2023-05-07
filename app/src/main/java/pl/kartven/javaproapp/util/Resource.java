package pl.kartven.javaproapp.util;

public abstract class Resource<T> {
    private final T data;
    private final String message;

    private Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public abstract boolean isSuccess();

    public static class Success<T> extends Resource<T> {
        public Success(T data) {
            super(data, null);
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }

    public static class Error<T> extends Resource<T> {
        public Error(String message, T data) {
            super(data, message);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }
    }

    /*public static class Loading<T> extends Resource<T> {
        public Loading(T data) {
            super(data, null);
        }
    }*/
}

