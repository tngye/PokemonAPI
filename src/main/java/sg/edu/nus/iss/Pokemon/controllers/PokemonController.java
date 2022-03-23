package sg.edu.nus.iss.Pokemon.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.Pokemon.services.PokemonServices;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    @Autowired 
    public PokemonServices pSvc;

    @GetMapping(path="/search")
    public String getPokemon(@RequestParam String pokemon_name, Model m) throws IOException{
        m.addAttribute("pokemon_name", pokemon_name);
        List<String> pokeImg = pSvc.findPokemon(pokemon_name);
        m.addAttribute("pokeImg", pokeImg);
        return "searchResults";
    }
    
}
