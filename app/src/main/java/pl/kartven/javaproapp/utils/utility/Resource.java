package pl.kartven.javaproapp.utils.utility;

import androidx.annotation.Nullable;

public abstract class Resource<T> {
    @Nullable
    private final T data;
    @Nullable
    private final String message;

    private Resource(@Nullable T data, @Nullable String message) {
        this.data = data;
        this.message = message;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
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

        public Error(String message) {
            super(null, message);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }
    }

    public static class Loading<T> extends Resource<T> {
        public Loading(T data) {
            super(data, null);
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }
}

