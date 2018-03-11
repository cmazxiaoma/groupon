package com.cmazxiaoma.framework.spring.web.servlet.mvc.support;

import com.cmazxiaoma.framework.base.exception.BusinessException;
import com.cmazxiaoma.framework.base.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * RoadHandlerExceptionResolver
 */
public class RoadHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(RoadHandlerExceptionResolver.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		try {
			 if (ex instanceof BusinessException) {
				return handleBusiness((BusinessException)ex, request, response, handler);
			} else if (ex instanceof DataAccessException) {
				return handleDataAccess((DataAccessException) ex, request, response, handler);
			} else if (ex instanceof NoSuchRequestHandlingMethodException) {
				return handleNoSuchRequestHandlingMethod((NoSuchRequestHandlingMethodException) ex, request, response, handler);
			} else if (ex instanceof HttpRequestMethodNotSupportedException) {
				return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, request, response, handler);
			} else if (ex instanceof HttpMediaTypeNotSupportedException) {
				return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, request, response, handler);
			} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
				return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, request, response, handler);
			} else if (ex instanceof MissingServletRequestParameterException) {
				return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, request, response, handler);
			} else if (ex instanceof ServletRequestBindingException) {
				return handleServletRequestBindingException((ServletRequestBindingException) ex, request, response, handler);
			} else if (ex instanceof ConversionNotSupportedException) {
				return handleConversionNotSupported((ConversionNotSupportedException) ex, request, response, handler);
			} else if (ex instanceof TypeMismatchException) {
				return handleTypeMismatch((TypeMismatchException) ex, request, response, handler);
			} else if (ex instanceof HttpMessageNotReadableException) {
				return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, request, response, handler);
			} else if (ex instanceof HttpMessageNotWritableException) {
				return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, request, response, handler);
			} else if (ex instanceof MethodArgumentNotValidException) {
				return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex, request, response, handler);
			} else if (ex instanceof MissingServletRequestPartException) {
				return handleMissingServletRequestPartException((MissingServletRequestPartException) ex, request, response, handler);
			} else if (ex instanceof BindException) {
				return handleBindException((BindException) ex, request, response, handler);
			}
		} catch (Exception handlerException) {
			logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
		}
		return null;
	}
	
	protected ModelAndView handleBusiness(BusinessException ex, 
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		logger.warn(ex.getMessage(), ex);
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return new ModelAndView();
	}
	
	protected ModelAndView handleDataAccess(DataAccessException ex, 
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		logger.warn(ex.getMessage(), ex);
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return new ModelAndView();
	}
	
	protected ModelAndView handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		logger.warn(ex.getMessage(), ex);
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		return new ModelAndView();
	}
	
	protected ModelAndView handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		logger.warn(ex.getMessage(), ex);
		String[] supportedMethods = ex.getSupportedMethods();
		if (supportedMethods != null) {
			response.setHeader("Allow", StringUtils.arrayToDelimitedString(supportedMethods, ", "));
		}
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, ex.getMessage());
		return new ModelAndView();
	}
	
	protected ModelAndView handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			response.setHeader("Accept", MediaType.toString(mediaTypes));
		}
		return new ModelAndView();
	}
	
	protected ModelAndView handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
		return new ModelAndView();
	}
	
	protected ModelAndView handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		return new ModelAndView();
	}
	
	protected ModelAndView handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return new ModelAndView();
	}
	
	protected ModelAndView handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		sendServerError(ex, request, response);
		return new ModelAndView();
	}
	
	protected void sendServerError(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("javax.servlet.error.exception", ex);
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	protected ModelAndView handleTypeMismatch(TypeMismatchException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return new ModelAndView();
	}
	
	protected ModelAndView handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return new ModelAndView();
	}
	
	protected ModelAndView handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		sendServerError(ex, request, response);
		return new ModelAndView();
	}
	
	protected ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
 		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return new ModelAndView();
	}
	
	protected ModelAndView handleMissingServletRequestPartException(MissingServletRequestPartException ex,
			HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
		return new ModelAndView();
	}
	
	protected ModelAndView handleBindException(BindException ex, HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return new ModelAndView();
	}

}
