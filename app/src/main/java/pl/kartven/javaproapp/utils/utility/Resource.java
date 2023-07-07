package pl.kartven.javaproapp.utils.utility;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        public Success(@NonNull T data) {
            super(data, null);
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        @Nullable
        public String getMessage() {
            return super.getMessage();
        }
    }

    public static class Error<T> extends Resource<T> {
        public Error(String message, @Nullable T data) {
            super(data, message);
        }

        public Error(String message) {
            super(null, message);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        @Nullable
        public T getData() {
            return super.getData();
        }
    }

    public static class Loading<T> extends Resource<T> {
        public Loading(@NonNull T data) {
            super(data, null);
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        @Nullable
        public String getMessage() {
            return super.getMessage();
        }
    }
}

