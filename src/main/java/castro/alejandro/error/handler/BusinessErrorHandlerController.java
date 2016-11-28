package castro.alejandro.error.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import castro.alejandro.error.ErrorViewDTO;
import castro.alejandro.error.hierarchy.BusinessException;

@ControllerAdvice
public class BusinessErrorHandlerController {
	
	@ExceptionHandler(value = {BusinessException.class})
    public @ResponseBody ErrorViewDTO exceptionHandlerController(HttpServletRequest request, BusinessException ext) {
        return new ErrorViewDTO(ext);
    }
	
}
