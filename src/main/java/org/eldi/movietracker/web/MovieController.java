package org.eldi.movietracker.web;

import org.eldi.movietracker.service.MovieService;
import org.eldi.movietracker.service.MovieServiceImpl;
import org.eldi.movietracker.model.Movie;
import org.eldi.movietracker.model.SearchResult;
import org.eldi.movietracker.repository.H2MovieRepository;
import org.eldi.movietracker.repository.H2RatingsRepository;
import org.eldi.movietracker.repository.MovieRepository;
import org.eldi.movietracker.util.APIQueries;
import org.eldi.movietracker.util.JDBCUtil;
import org.eldi.movietracker.util.JacksonUtil;
import org.eldi.movietracker.util.UserPreferences;

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
    private Connection connection = JDBCUtil.INSTANCE.getConnection();
    private MovieService movieService = new MovieServiceImpl(JacksonUtil.OBJECT_MAPPER);
    private MovieRepository movieRepository = new H2MovieRepository(connection, new H2RatingsRepository(connection));
    private UserPreferences userPreferences = new UserPreferences();
    private APIQueries apiQueries = new APIQueries(userPreferences);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.equalsIgnoreCase("/")) {
            request.getRequestDispatcher("home.html").forward(request, response);
        } else if (requestURI.equalsIgnoreCase("/search")) {
            String query = request.getParameter("query");
            String apiUrl = apiQueries.getSearchQuery(query);
            List<SearchResult> results = movieService.search(apiUrl);

            request.setAttribute("results", results);
            request.getRequestDispatcher("search.html").forward(request, response);
        } else if (requestURI.equalsIgnoreCase("/movies")) {
            List<Movie> movies = movieRepository.findAll();
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("movies.html").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equalsIgnoreCase("/save")) {
            String imdbID = request.getParameter("imdb-id");

            if (imdbID != null) {
                String url = apiQueries.getImdbSearchQuery(imdbID);
                Movie movie = movieService.getMovie(url);

                movieRepository.save(movie);
            } else {
                // TODO write to view
                System.out.println("Invalid imdb id");
            }
        }
    }
}
