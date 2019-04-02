package pl.mati.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MovieConstroller {

    private MovieRepository movieRepository;

    public MovieConstroller(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/")
    public String allMovies(Model model) {
        List<Movie> allMovies = movieRepository.findAll();
        model.addAttribute("movies", allMovies);
        return "movies"; // - > resources / templates/movies.html
    }

    @GetMapping("/film/{id}")
    public String movieDetails(@PathVariable Long id, Model model) {
        Optional<Movie> optional = movieRepository.findById(id);
        if (optional.isPresent()) {
            Movie movie = optional.get();
            model.addAttribute("movie", movie);
            return "movie";
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/addmovie")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        TargetAudience[] targetAudiences = TargetAudience.values();
        model.addAttribute("targetAudiences", targetAudiences);
        return "addmovieform";
    }

    @PostMapping("/addmovie")
    public String addMovie(Movie movie) {
        movieRepository.save(movie);
        return "redirect:/";
    }

    @GetMapping("/usun/{id}")
    public String deleteMovie(Movie movie) {
        Optional<Movie> optional = movieRepository.findById(movie.getId());
        if (optional.isPresent()) {
            movie = optional.get();
            movieRepository.delete(movie);
            return "redirect:/";
        }
        return "redirect:/";
    }


}
