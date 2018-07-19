package ru.itis.mq.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.itis.mq.app.commands.RegistrationForm;
import ru.itis.mq.app.domain.User;


@Component
public class UserToUserForm implements Converter<User, RegistrationForm> {
    @Override
    public RegistrationForm convert(User user) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setId(user.getId());
        registrationForm.setFirstName(user.getFirstName());
        registrationForm.setEmail(user.getEmail());
        registrationForm.setLogin(user.getLogin());
        registrationForm.setPassword(user.getPassword());
        registrationForm.setPhone(user.getPhone());
        return registrationForm;
    }
}
