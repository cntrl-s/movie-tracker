package org.eldi.movietracker.web;

import org.eldi.movietracker.exception.InvalidAPIKeyException;
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

    private Connection connection;
    private MovieService movieService;
    private MovieRepository movieRepository;
    private UserPreferences userPreferences;
    private APIQueries apiQueries;

    public void init() {
        connection = JDBCUtil.INSTANCE.getConnection();
        movieService = new MovieServiceImpl(JacksonUtil.OBJECT_MAPPER);
        movieRepository = new H2MovieRepository(connection, new H2RatingsRepository(connection));
        userPreferences = new UserPreferences();
        apiQueries = new APIQueries(userPreferences);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.equalsIgnoreCase("/") && userPreferences.getAPIKey().equals("default")) {
            request.getRequestDispatcher("apikey.html").forward(request, response);
        } else if (requestURI.equalsIgnoreCase("/")) {
            request.getRequestDispatcher("home.html").forward(request, response);
        } else if (requestURI.equalsIgnoreCase("/search")) {
            String query = request.getParameter("query");
            String apiUrl = apiQueries.getSearchQuery(query);
            List<SearchResult> results = movieService.search(apiUrl);

            request.setAttribute("results", results);
            request.getRequestDispatcher("search.html").forward(request, response);
        } else if (requestURI.startsWith("/movies")) {
            int currentPage = 1;
            String reqCurrentPage = request.getParameter("current-page");
            if(reqCurrentPage != null) {
                currentPage = Integer.parseInt(reqCurrentPage);
            }

            int pageSize = 5;
            List<Movie> movies = movieRepository.findAllByPage(pageSize, currentPage);
            int recordsSize = movieRepository.getRecordsSize();
            int totalPages = (int) Math.ceil(recordsSize * 1.0 / pageSize);

            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("movies.html").forward(request, response);
        } else if (requestURI.equalsIgnoreCase("/search-movies")) {
            String title = request.getParameter("movie-query");
            List<Movie> movieResults = movieRepository.findByTitle(title);

            if (movieResults.size() >= 1) {
                request.setAttribute("movieResults", movieResults);
                request.getRequestDispatcher("movie-search.html").forward(request, response);
            } else {
                response.getWriter().print("No movies found");
                //TODO ratings view
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.equalsIgnoreCase("/save")) {
            String imdbID = request.getParameter("imdb-id");

            if (imdbID != null) {
                String url = apiQueries.getImdbSearchQuery(imdbID);
                Movie movie = movieService.getMovie(url);

                movieRepository.save(movie);
            } else {
                response.getWriter().print("Invalid imdb id");
            }
        } else if (requestURI.equalsIgnoreCase("/submit-apikey")) {
            String apiKey = request.getParameter("api-key");
            try {
                userPreferences.setAPIKey(apiKey);
                response.sendRedirect("/");
            } catch (InvalidAPIKeyException e) {
                response.getWriter().print("API Key invalid");
                e.printStackTrace();
            }
        }
    }
}
