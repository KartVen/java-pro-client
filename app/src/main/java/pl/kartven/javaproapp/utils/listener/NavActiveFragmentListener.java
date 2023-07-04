package pl.kartven.javaproapp.utils.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import io.vavr.control.Option;

public interface NavActiveFragmentListener {
    default Option<Fragment> getActiveFragment(AppCompatActivity parent, int navFragmentElementId){
        return Option.of(parent.getSupportFragmentManager().findFragmentById(navFragmentElementId))
                .filter(f -> f instanceof NavHostFragment)
                .map(f -> f.getChildFragmentManager().getFragments())
                .map(l -> l.get(0));
    }
}
