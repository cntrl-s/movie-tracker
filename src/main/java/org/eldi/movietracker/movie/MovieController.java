package org.eldi.movietracker.movie;

import org.eldi.movietracker.util.APIQueries;
import org.eldi.movietracker.util.JDBCUtil;
import org.eldi.movietracker.util.JacksonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(urlPatterns = "/*")
public class MovieController extends HttpServlet {
    // init() ?
    private Connection connection = JDBCUtil.getConnection();
    private MovieService movieService = new MovieServiceImpl(JacksonUtil.OBJECT_MAPPER);
    private MovieRepository movieRepository = new H2MovieRepository(connection, new H2RatingsRepository(connection));

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.equalsIgnoreCase("/")) {
            request.getRequestDispatcher("home.html").forward(request, response);
        } else if (requestURI.equalsIgnoreCase("/search")) {
            String query = request.getParameter("query");
            String apiUrl = APIQueries.getSearchQuery(query);
            List<SearchResult> results = movieService.search(apiUrl);

            request.setAttribute("results", results);
            request.getRequestDispatcher("search.html").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equalsIgnoreCase("/save")) {
            String imdbID = request.getParameter("imdb-id");

            // TODO isValid() ?
            if (imdbID != null) {
                String url = APIQueries.getImdbSearchQuery(imdbID);
                Movie movie = movieService.getMovie(url);

                movieRepository.save(movie);
            } else {
                // TODO write to view
                System.out.println("Invalid imdb id");
            }
        }
    }
}
