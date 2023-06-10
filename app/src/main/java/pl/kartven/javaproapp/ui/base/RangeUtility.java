package pl.kartven.javaproapp.ui.base;

import java.util.List;

import pl.kartven.javaproapp.util.Resource;

public interface RangeUtility<T> {
    Resource<List<T>> getRangeData();
}
