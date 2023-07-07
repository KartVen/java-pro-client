package pl.kartven.javaproapp.utils.listener;

public interface RVItemExpandListener<T, R> {
    R onExpand(T model, int position);
}
