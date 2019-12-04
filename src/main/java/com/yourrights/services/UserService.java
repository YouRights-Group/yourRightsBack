package com.yourrights.services;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.yourrights.beans.User;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.UserException;
import com.yourrights.repository.UserRepository;
import com.yourrights.repository.beans.UserEntity;
import com.yourrights.utils.SecurityContextUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JavaMailSender mailSender;

    public void saveUser(User user) {

	UserEntity userExisted = repository.findByEmail(user.getEmail());
	if (userExisted != null) {
	    throw new UserException(Constants.ERROR, Constants.USER_EXISTED, "User already exists");
	}

	UserEntity userEntity = new UserEntity();
	userEntity.setEmail(user.getEmail());
	userEntity.setPassword(user.getPassword());

	repository.save(userEntity);

    }

    public void login(User user, String token) {
	UserEntity userExisted = repository.findByEmail(user.getEmail());
	if (userExisted == null) {
	    throw new UserException(Constants.ERROR, Constants.USER_NOT_FOUND, "User not found");
	} else {
	    if (!userExisted.getPassword().equals(user.getPassword())) {
		throw new UserException(Constants.ERROR, Constants.USER_WRONG_PASSWORD, "User wrong password");
	    }
	}

	userExisted.setToken(token);
	repository.save(userExisted);
    }

    public UserEntity getUser(String token) {
	return repository.findByToken(token);
    }

    public void regeneratePasswords() throws MessagingException {
	UserEntity user = SecurityContextUtils.getUser();
	MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        helper.setTo(new InternetAddress(user.getEmail()));
        helper.setReplyTo("yourrights@yourrights.com");
        helper.setFrom("yourrights@yourrights.com");
        String subject = "Restaurar sesión";
        Pattern p1 = Pattern.compile("\\r");
        Pattern p2 = Pattern.compile("\\n");
        p1.matcher(subject).replaceAll("");
        p2.matcher(subject).replaceAll("");
        helper.setSubject(subject);
        helper.setText("Pulsa en este enlace para actualizar tu constraseña", true);
        // helper.addInline(ExportConstants.NAME_HEADER_IMAGE, new
        // ClassPathResource(ExportConstants.NAME_HEADER_IMAGE));
        mailSender.send(message);
	
    }

}
