package pl.kartven.javaproapp.utils.utility;

public class State<T> {
    private T data;
    private static State<?> instance = null;

    private State() {
    }

    @SuppressWarnings("unchecked")
    public static <T> State<T> getState() {
        if (instance == null) {
            instance = new State<>();
        }
        return (State<T>) instance;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void clear() {
        this.data = null;
    }

    public boolean isExists() {
        return this.data != null;
    }
}
