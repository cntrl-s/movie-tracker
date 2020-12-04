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
import java.util.List;

@WebServlet
public class MovieController extends HttpServlet {
    // init() ?
    private MovieServiceImpl service = new MovieServiceImpl(JacksonUtil.OBJECT_MAPPER);
    private MovieRepository repository = new H2MovieRepository(JDBCUtil.Connector.INSTANCE.getConnection());

    // TODO get selected result & fetch info using id & persist
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equalsIgnoreCase("/search")) {
            // TODO get searchQuery from request
            List<SearchResult> results = service.search("");
            // forward results to html
            // Movie muv = movieService.getMovie(selectedMuv)
            // repository.save(muv)
            // repo.save(muv.rating)
            List<SearchResult> searchResults = service.search(APIQueries.getSearchQuery("movie name"));
        }
    }
}
