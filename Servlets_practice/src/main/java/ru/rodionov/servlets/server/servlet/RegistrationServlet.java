package ru.rodionov.servlets.server.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.rodionov.servlets.server.dto.CreateUserDTO;
import ru.rodionov.servlets.server.dto.CreateUserDTO.CreateUserDTOBuilder;
import ru.rodionov.servlets.server.exception.ValidationException;
import ru.rodionov.servlets.server.service.UserService;
import ru.rodionov.servlets.server.util.JspHelper;
import ru.rodionov.servlets.server.util.UrlPath;

import java.io.IOException;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", List.of("USER","ADMIN"));
        req.setAttribute("genders", List.of("MALE", "FEMALE"));

        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDTO builder = CreateUserDTO.builder()
                .name(req.getParameter("name"))
                .image(req.getPart("image"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();
        try{
            userService.create(builder);
            resp.sendRedirect("/login");

        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}