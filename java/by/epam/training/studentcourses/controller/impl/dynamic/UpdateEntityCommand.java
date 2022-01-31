package by.epam.training.studentcourses.controller.impl.dynamic;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.studentcourses.controller.Command;
import by.epam.training.studentcourses.controller.EntityParser;
import by.epam.training.studentcourses.controller.constant.ContextParams;
import by.epam.training.studentcourses.controller.constant.ErrorMessages;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.service.exception.ServiceException;
import by.epam.training.studentcourses.util.entity.User;

public abstract class UpdateEntityCommand<T> implements Command {

	private static Logger log = LogManager.getLogger(UpdateEntityCommand.class);
	private final EntityCRUDService<T> service;
	protected static final EntityParser parser = EntityParserImpl.getInstance();

	public UpdateEntityCommand(EntityCRUDService<T> service) {
		this.service = service;
	}

	protected abstract List<T> parseEntities(Map paramsMap) throws InvalidRequestException;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		List<T> entitiesList = parseEntities(request.getParameterMap());
		if (!entitiesList.isEmpty())
			log.debug("update entity: {}", entitiesList.get(0));
		try {
			service.update((User) request.getSession().getAttribute(ContextParams.Session.USER), entitiesList);
			return null;
		} catch (InvalidEntityException e) {
			response.setStatus(422);
			request.setAttribute(ContextParams.Session.ERROR_MESSAGE, ErrorMessages.EntityCRUD.INVALID_PARAMETERS);
		} catch (NotAllowedException e) {
			throw new by.epam.training.studentcourses.controller.exception.NotAllowedException(e);
		} catch (InternalServiceException e) {
			throw new InternalControllerException(e);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return JspPaths.ERROR;
	}

}
