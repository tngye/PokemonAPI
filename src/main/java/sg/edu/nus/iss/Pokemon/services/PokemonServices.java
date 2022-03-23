package sg.edu.nus.iss.Pokemon.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class PokemonServices {
    public List<String> findPokemon(String pokemon_name) throws IOException {
        String url = UriComponentsBuilder
                .fromUriString("https://pokeapi.co/api/v2/pokemon/")
                // .queryParam("pokemon_name", pokemon_name)
                .path(pokemon_name)
                .toUriString();

        System.out.println(">>>> url: " + url);
        RequestEntity req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        // check for 404 cause if search a word that is not in api will 404
        if (resp.getStatusCodeValue() >= 400) {

        }

        JsonObject data = null;

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            data = reader.readObject();
        }

        data = data.getJsonObject("sprites");
        data = data.getJsonObject("versions");
        data = data.getJsonObject("generation-i");
        data = data.getJsonObject("red-blue");

        List<String> pokeImg = new ArrayList<String>();
        Iterator<JsonValue> itr = data.values().iterator();
        while (itr.hasNext()) {
            // System.out.println(">>>> pokeImg: " + itr.next());
            String itrNext = itr.next().toString();
            if (itrNext != null) {
                itrNext = itrNext.replaceAll("\"", "");
                pokeImg.add(itrNext);
            }
        }
        System.out.println(">>>> pokeImg: " + pokeImg);

        return pokeImg;
    }
}
