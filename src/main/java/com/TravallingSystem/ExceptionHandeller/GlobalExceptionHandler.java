package com.TravallingSystem.ExceptionHandeller;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

//@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(UserAlreadyExistsException.class)
//    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
//    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
//    }
    
//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e, Model model) {
//        model.addAttribute("errorMessage", e.getMessage());
//        return "error"; // The name of your error template
//    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
//    }

 @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ModelAndView modelAndView = new ModelAndView("register"); // Redirect to the registration page
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

     @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error"); // Create an error view
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.addObject("errorMessage", "Page not found. Please check the URL.");
        return modelAndView;
    }
}

