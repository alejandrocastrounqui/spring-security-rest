package castro.alejandro.error.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public @ResponseBody String exceptionHandlerController(HttpServletRequest request, Exception ext) {
        return "InvalidArgumentException";
    }
}
