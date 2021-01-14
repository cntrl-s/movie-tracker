package org.eldi.movietracker;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.eldi.movietracker.exception.InvalidAPIKeyException;
import org.eldi.movietracker.util.JDBCUtil;
import org.eldi.movietracker.util.UserPreferences;
import org.eldi.movietracker.web.MovieController;
import org.eldi.movietracker.web.StaticServlet;
import org.eldi.movietracker.web.ThymeleafTemplateProcessor;

import java.io.File;

public class MovieApplication {
    public static void main(String[] args) throws LifecycleException {
        JDBCUtil.INSTANCE.createTableIfNotExists();

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        File docBase = new File("src/main/webapp");
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        Wrapper movieController = tomcat.addServlet(context, "movie-controller", new MovieController());
        movieController.setLoadOnStartup(1);
        movieController.addMapping("/");

        Wrapper staticServlet = tomcat.addServlet(context, "static-servlet", new StaticServlet());
        staticServlet.setLoadOnStartup(1);
        staticServlet.addMapping("/static/*");

        Wrapper thymeleafProcessor = tomcat.addServlet(context, "thymeleaf-processor", new ThymeleafTemplateProcessor());
        thymeleafProcessor.addMapping("*.html");

        tomcat.start();
    }
}
