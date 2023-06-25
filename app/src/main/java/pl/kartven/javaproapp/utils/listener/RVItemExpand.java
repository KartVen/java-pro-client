package pl.kartven.javaproapp.utils.listener;

public interface RVItemExpand<T, R> {
    R onExpand(T model, int position);
}
