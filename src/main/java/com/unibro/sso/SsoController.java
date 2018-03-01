/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.sso;

import com.unibro.main.Application;
import com.unibro.user.*;
import com.unibro.utils.Global;
import com.unibro.utils.RequiredException;
import com.unibro.utils.ResultData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author THOND
 */
@RestController
public class SsoController {

    Logger logger = LogManager.getLogger(this.getClass().getName());

    @ExceptionHandler(UserDAOException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultData daoError(HttpServletRequest request, UserDAOException exception) {
        ResultData error = new ResultData();
        error.status = exception.getHttpCode();
        error.message = exception.getLocalizedMessage();
        error.error = exception.getLocalizedMessage();
        error.path = request.getRequestURL().toString();
        error.exception = "UserDAOException";
        return error;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData missingParamError(HttpServletRequest request, Exception exception) {
        ResultData error = new ResultData();
        error.status = HttpStatus.BAD_REQUEST.value();
        error.message = exception.getLocalizedMessage();
        error.error = exception.getLocalizedMessage();
        error.path = request.getRequestURL().toString();
        error.exception = "MissingServletRequestParameterException";
        return error;
    }

    @ExceptionHandler(RequiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData requiredError(HttpServletRequest request, Exception exception) {
        ResultData error = new ResultData();
        error.status = HttpStatus.BAD_REQUEST.value();
        error.message = exception.getLocalizedMessage();
        error.error = exception.getLocalizedMessage();
        error.path = request.getRequestURL().toString();
        error.exception = "RequiredException";
        return error;
    }

    @RequestMapping(value = "/api/sso/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> login(HttpServletRequest request, @RequestBody() BasicUser user) {
        logger.info(request.getRequestURI());

        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.error = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        User u = dao.getObjectByUserField("username", user.getUsername());
        if (u == null) {
            dao.setExceptionCode(HttpStatus.NOT_FOUND);
            data.status = HttpStatus.NOT_FOUND.value();
            data.message = "Fail";
            data.exception = "";
            data.error = "User not found";
        } else {
            if (!u.getPassword_hash().equals(user.getPassword_hash())) {
                dao.setExceptionCode(HttpStatus.FORBIDDEN);
                data.status = HttpStatus.FORBIDDEN.value();
                data.message = "Fail";
                data.exception = "";
                data.error = "Authentication fail";
            } else {
                dao.setExceptionCode(HttpStatus.OK);
                data.status = HttpStatus.OK.value();
                data.message = "Success";
                data.error = "";
                data.exception = "";
                if (u.checkValidSession()) {
                    logger.info("Valid session");
                    data.result = u.getLogin_token();
                } else {
                    logger.info("In Valid session");
                    Long expired_time = System.currentTimeMillis() + Integer.valueOf(Global.getConfigValue("app.expired_time"));
                    logger.info("User:" + u.toJsonString());
                    String token = Jwts.builder()
                            .setSubject(u.toJsonToken())
                            .setExpiration(new Date(expired_time))
                            .signWith(SignatureAlgorithm.HS512, Global.getConfigValue("app.secret"))
                            .compact();
                    u.setLogin_token(token);
                    u.setExpired_time(expired_time);
                    u.setLast_login(new java.util.Date());
                    dao.updateObject(u);
                    data.result = token;
                }
            }
        }
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/sso/login_token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> login(HttpServletRequest request, @RequestParam(value = "token", required = true) String token) {
        logger.info(request.getRequestURI());
        logger.info("Token value:" + token);
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.error = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        User u = dao.getObjectByUserField("login_token", token);
        if (u == null) {
            dao.setExceptionCode(HttpStatus.NOT_FOUND);
            data.status = HttpStatus.NOT_FOUND.value();
            data.message = "Fail";
            data.exception = "";
            data.error = "User not found";
        } else {
            if (!u.checkValidSession()) {
                dao.setExceptionCode(HttpStatus.LOCKED);
                data.status = HttpStatus.LOCKED.value();
                data.message = "Token Expired. Please login again";
                data.exception = "";
                data.error = "Authentication fail. Token Expired";
            } else {
                dao.setExceptionCode(HttpStatus.OK);
                data.status = HttpStatus.OK.value();
                data.message = "Success";
                data.error = "";
                data.exception = "";
                data.result = u.getLogin_token();
            }
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

}
