package org.eldi.movietracker.movie;

import org.apache.catalina.servlets.DefaultServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/static/*")
public class StaticServlet extends DefaultServlet {
}
