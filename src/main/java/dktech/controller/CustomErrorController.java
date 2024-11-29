
package dktech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		Object errorTypeObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
		String errorType = errorTypeObj != null ? errorTypeObj.toString() : "N/A";

		logger.debug("Status Code: {}", statusCode);
		logger.debug("Error Message: {}", errorMessage);
		logger.debug("Error Type: {}", errorType);

		modelAndView.addObject("statusCode", statusCode != null ? statusCode : 500);
		modelAndView.addObject("errorMessage", errorMessage != null ? errorMessage : "N/A");
		modelAndView.addObject("errorType", errorType);

		return modelAndView;
	}
}
