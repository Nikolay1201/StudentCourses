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
import by.epam.training.studentcourses.controller.constant.HttpParams;
import by.epam.training.studentcourses.controller.constant.JspPaths;
import by.epam.training.studentcourses.controller.exception.ControllerException;
import by.epam.training.studentcourses.controller.exception.InternalControllerException;
import by.epam.training.studentcourses.controller.exception.InvalidRequestException;
import by.epam.training.studentcourses.controller.impl.EntityParserImpl;
import by.epam.training.studentcourses.service.EntityCRUDService;
import by.epam.training.studentcourses.service.exception.InternalServiceException;
import by.epam.training.studentcourses.service.exception.InvalidEntitiesException;
import by.epam.training.studentcourses.service.exception.NoSuchEntityException;
import by.epam.training.studentcourses.service.exception.NotAllowedException;
import by.epam.training.studentcourses.util.TableAttr;
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
		if (!entitiesList.isEmpty()) {
			log.debug("update entity: {}", entitiesList.get(0));
		}
		try {
			service.update((User) request.getSession().getAttribute(ContextParams.Session.USER), entitiesList.get(0));
		} catch (InvalidEntitiesException e) {
			StringBuilder errorMessage = new StringBuilder(ErrorMessages.EntityCRUD.INVALID_PARAMETERS);
			errorMessage.append("<br>");
			if (!e.getInvalidAttrsLists().isEmpty()) {
				for (TableAttr attr : e.getInvalidAttrsLists().get(0)) {
					errorMessage.append("- ");
					errorMessage.append(attr.getAttrName());
					errorMessage.append("<br>");
				}
			}
			response.setStatus(HttpParams.StatusCode.SC_UNPROCESSABLE_ENITIY);
			request.setAttribute(ContextParams.Request.ERROR_MESSAGE, errorMessage);
			return JspPaths.ERROR;
		} catch (NotAllowedException e) {
			throw new by.epam.training.studentcourses.controller.exception.NotAllowedException(e);
		} catch (InternalServiceException e) {
			throw new InternalControllerException(e);
		} catch (NoSuchEntityException e) {
			request.setAttribute(ContextParams.Request.ERROR_MESSAGE, e.getMessage());
			return JspPaths.ERROR;
		}
		return null;
	}

}
