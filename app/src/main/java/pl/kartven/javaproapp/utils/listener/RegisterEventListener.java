package pl.kartven.javaproapp.utils.listener;

import static pl.kartven.javaproapp.utils.utility.FieldUtils.NicknameValidator;

import androidx.lifecycle.LiveData;

public interface RegisterEventListener extends LoginEventListener {
    NicknameValidator nicknameValidator = new NicknameValidator();

    default void updateNicknameState(String value) {
        nicknameValidator.setCurrentState(value).validate();
    }

    default LiveData<Integer> getNicknameError() {
        return nicknameValidator.validate().getErrorState();
    }

    default boolean isNicknameFieldActived() {
        return nicknameValidator.isActived();
    }
}
