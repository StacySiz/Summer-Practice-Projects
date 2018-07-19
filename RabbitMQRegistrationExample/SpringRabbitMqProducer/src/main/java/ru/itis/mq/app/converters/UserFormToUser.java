package ru.itis.mq.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.itis.mq.app.commands.RegistrationForm;
import ru.itis.mq.app.domain.User;


@Component
public class UserFormToUser implements Converter<RegistrationForm, User> {

    @Override
    public User convert(RegistrationForm registrationForm) {
        User user = new User();
        if (registrationForm.getId() != null && !StringUtils.isEmpty(registrationForm.getId())) {
            user.setId(registrationForm.getId());
        }
        user.setFirstName(registrationForm.getFirstName());
        user.setEmail(registrationForm.getEmail());
        user.setLogin(registrationForm.getLogin());
        user.setPassword(registrationForm.getPassword());
        user.setPhone(registrationForm.getPhone());
        return user;
    }
}
