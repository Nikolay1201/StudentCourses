package by.epam.training.studentcourses.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.RequestParams;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.impl.AuthenticationCommand;

public class Invoker {
    private static final Map<String, Command> commandMap = new HashMap<>();
    
    static {
    	commandMap.put(RequestParams.OperationTypes.AUTHENTICATION, new AuthenticationCommand());
    }
    
    public static void execute(String commandName, HttpServletRequest request, HttpServletResponse response)
    		throws ControllerException, IOException {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new InvalidRequestException(ErrorMessages.INTERNAL_ERROR);
        }
        command.execute(request, response);
    }
    
    private Invoker () {}
}
