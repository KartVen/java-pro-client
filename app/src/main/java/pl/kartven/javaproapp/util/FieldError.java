package pl.kartven.javaproapp.util;

import android.content.Context;

import pl.kartven.javaproapp.R;

public class FieldError {
    private Type type;
    private Object error;

    public FieldError(Type type, Object error) {
        this.type = type;
        this.error = error;
    }

    public FieldError(Type type) {
        this.type = type;
        this.error = null;
    }

    public String getError(Context context) {
        switch(type){
            case REQUIRED:
            case BAD_EMAIL:
                return context.getString(type.getId());
            case MUST_CONTAIN:
            case MINIMUM:
                return String.format(context.getString(type.getId()), this.error);
        };
        return "";
    }

    public enum Type {
        REQUIRED(R.string.required),
        MUST_CONTAIN(R.string.must_contain),
        BAD_EMAIL(R.string.error_bad_format),
        MINIMUM(R.string.error_minimum_size);

        private final int id;

        Type(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
