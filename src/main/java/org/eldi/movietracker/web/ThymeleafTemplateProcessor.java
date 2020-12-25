package org.eldi.movietracker.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "*.html")
public class ThymeleafTemplateProcessor extends HttpServlet {

    private TemplateEngine templateEngine;

    public void init() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(this.getServletContext());
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();

        WebContext thContext = new WebContext(request, response, request.getServletContext());

        if (requestURI.equalsIgnoreCase("/home.html")) {
            templateEngine.process("home", thContext, response.getWriter());
        } else if (requestURI.equalsIgnoreCase("/search.html")) {
            templateEngine.process("search", thContext, response.getWriter());
        } else if (requestURI.equalsIgnoreCase("/movies.html")) {
            templateEngine.process("movies", thContext, response.getWriter());
        } else if (requestURI.equalsIgnoreCase("/movie-search.html")) {
            templateEngine.process("movie-search", thContext, response.getWriter());
        }
    }
}
