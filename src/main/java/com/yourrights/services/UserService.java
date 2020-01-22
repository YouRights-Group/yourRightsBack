package com.yourrights.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.yourrights.beans.RegeneratePWDBean;
import com.yourrights.beans.User;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.UserException;
import com.yourrights.repository.UserRepository;
import com.yourrights.repository.beans.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EncryptService encryptService;

    @Value("${config.security.secretKey}")
    private String secretKey;
    @Value("${config.security.encryptPwd}")
    private String encryptPwd;
    @Value("${config.security.encryptSalt}")
    private String encryptSalt;

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

    public String forgotPassword(String email) throws MessagingException {

	MimeMessage message = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
	String token = passwordHash(email);
	helper.setTo(new InternetAddress(email));
	helper.setReplyTo("yourrights@yourrights.com");
	helper.setFrom("yourrights@yourrights.com");
	String subject = "Restaurar sesión";
	Pattern p1 = Pattern.compile("\\r");
	Pattern p2 = Pattern.compile("\\n");
	p1.matcher(subject).replaceAll("");
	p2.matcher(subject).replaceAll("");
	helper.setSubject(subject);
	helper.setText("Pulsa en este enlace para actualizar tu constraseña: \n http://yourRighst/forgotPassword.html/"
		+ token, true);

	mailSender.send(message);
	return "OK";
    }

    private String passwordHash(String email) {
	return encryptService.encrypt(email);
    }

    public String getJWTToken(String username) {

	List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

	String token = Jwts.builder().setId("softtekJWT").setSubject(username)
		.claim("authorities",
			grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 1200000))
		.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

	return token;
    }

    public void regeneratePassword(RegeneratePWDBean regeneratePwdBean) {
	String email = encryptService.decrypt(regeneratePwdBean.getToken());
	UserEntity userEntity = repository.findByEmail(email);
	userEntity.setPassword(regeneratePwdBean.getNewPassword());
	repository.save(userEntity);
    }

}
