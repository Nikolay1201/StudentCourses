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
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.exception.NotAllowedException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.util.Identifiable;
import by.epam.training.studentcourses.util.entity.User;

public abstract class DeleteEntityByIdCommand<T extends Identifiable> implements Command {

	private static Logger log = LogManager.getLogger(DeleteEntityByIdCommand.class);
	protected static final EntityParser parser = EntityParserImpl.getInstance();
	private final EntityCRUDService<T> service;

	public DeleteEntityByIdCommand(EntityCRUDService<T> service) {
		this.service = service;
	}

	protected abstract List<T> parseEntities(Map paramsMap) throws InvalidRequestException;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ControllerException, IOException {
		List<T> entitiesList = parseEntities(request.getParameterMap());
		if (!entitiesList.isEmpty())
			log.debug("new entity: {}", entitiesList.get(0));
		try {
			service.deleteById((User) request.getSession().getAttribute(ContextParams.Session.USER),
					entitiesList.get(0).getId());
		} catch (by.epam.training.studentcourses.service.exception.NotAllowedException e) {
			throw new NotAllowedException(e);
		} catch (by.epam.training.studentcourses.service.exception.InvalidRequestException e) {
			throw new InvalidRequestException(e);
		} catch (InternalServiceException e) {
			throw new InternalControllerException(e);
		}
		return null;
	}

}
