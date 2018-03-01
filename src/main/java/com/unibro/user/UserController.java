/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibro.user;

import com.unibro.main.Application;
import com.unibro.utils.AuthException;
import com.unibro.utils.RequestFilter;
import com.unibro.utils.RequiredException;
import com.unibro.utils.ResultData;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author THOND
 */
@RestController
public class UserController {

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

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultData authError(HttpServletRequest request, AuthException exception, int httpCode) {
        ResultData error = new ResultData();
        error.status = exception.getHttpCode();
        error.message = exception.getLocalizedMessage();
        error.error = exception.getLocalizedMessage();
        error.path = request.getRequestURL().toString();
        error.exception = "AuthException";
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

    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> getAllObject(HttpServletRequest request) {
        logger.info(request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization"};
        String[] params = {authorization};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        data.result = dao.getAllObjects();
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> getObject(HttpServletRequest request, @PathVariable(value = "id", required = true) Integer id) {
        logger.info(request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization", "id"};
        String[] params = {authorization, String.valueOf(id)};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        data.result = dao.getObject(id);
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> newObject(HttpServletRequest request, @RequestBody() User user) {
        logger.info(request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization"};
        String[] params = {authorization};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        data.result = dao.createObject(user);
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> updateObject(HttpServletRequest request, @RequestBody() User user) {
        logger.info(request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization"};
        String[] params = {authorization};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }

        if (!AuthException.checkValidEndPoint(request)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }

        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        data.result = dao.updateObject(user);
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> deleteObject(HttpServletRequest request, @PathVariable(value = "id", required = true) Integer id) {
        logger.info(request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization", "id"};
        String[] params = {authorization, String.valueOf(id)};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        data.result = dao.deleteObject(id);
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/user/filter/{first}/{pagesize}/{sortfield}/{sortorder}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> filterObject(HttpServletRequest request, @PathVariable(value = "first", required = true) Integer first, @PathVariable(value = "pagesize", required = true) Integer pagesize,
            @PathVariable(value = "sortfield", required = false) String sortfield, @PathVariable(value = "sortorder", required = true) Integer sortorder,
            @RequestBody() List<RequestFilter> filters) {
        logger.info(request.getRequestURI());

        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization", "first", "pagesize", "softfield", "sortorder"};
        String[] params = {authorization, String.valueOf(first), String.valueOf(pagesize), sortfield, String.valueOf(sortorder)};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        List<User> list_data = dao.filterObjects(first, pagesize, sortfield, sortorder, filters);
        data.result = list_data;
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

    @RequestMapping(value = "/api/user/datasize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResultData> filterObject(HttpServletRequest request, @RequestBody() List<RequestFilter> filters) {
        logger.info(request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        String[] param_names = {"Authorization"};
        String[] params = {authorization};
        String exception = RequiredException.checkRequired(param_names, params);
        logger.info("Exception:" + exception);
        if (exception.length() > 0) {
            throw new RequiredException(exception);
        }
        if (!AuthException.checkAuth(authorization)) {
            throw new AuthException(authorization, HttpStatus.FORBIDDEN.value());
        }
        ResultData data = new ResultData();
        data.status = HttpStatus.OK.value();
        data.message = "Success";
        data.exception = "";
        data.path = request.getRequestURL().toString();
        UserDAOImpl dao = (UserDAOImpl) Application.context.getBean("userDAO");
        dao.setExceptionCode(HttpStatus.OK);
        dao.setExceptionMsg("");
        int size = dao.filterObjectsSize(filters);
        data.result = size;
        data.status = dao.getExceptionCode().value();
        data.error = dao.getExceptionMsg();
        data.exception = dao.getExceptionType();
        if (data.status != 200) {
            data.message = "Error";
        }
        return new ResponseEntity<>(data, HttpStatus.valueOf(data.status));
    }

}
