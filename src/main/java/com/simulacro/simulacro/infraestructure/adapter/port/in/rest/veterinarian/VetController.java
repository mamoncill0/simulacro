package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian;


import com.simulacro.simulacro.domain.model.veterinarian.Vet;
import com.simulacro.simulacro.infraestructure.adapter.port.in.rest.veterinarian.dto.response.VetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
Esto es un adaptador REST porque recibe peticiones desde el exterior que seria lo que manda el usuario.
Se llama adaptador porque adapta los protocolos HTTP a nuestro codigo Java.
*/


//Haremos directamente en el controller la logica, eso significa que No llamaremos al Service, ya que haremos t0do el trabajo desde aqui, para probar
@RestController
@RequestMapping("/")
public class VetController {

    //Aqui hacemos el microservicio que llamaremos para observar si esta disponible un veterinario o no
    //Empezamos definiendo la ruta
    @PostMapping("/avalability")

    //Hacemos un ResponseEntity que actua como un Object que va a recibir un valor mas adelante
    //El RequestBody significa que va a recibir los parametros en el body osea el JSON de postman
    public ResponseEntity<?> checkAvalability(@RequestBody Vet request){

        //Aqui hacemos lo que deberia ir en el Service que es la logica
        //Estamos diciendo que si alguno de esos datos esta nulo o vacio
        //Nos salga el mensaje que de faltan campos y nos dice que campos deberian ir
        if (request.getNameVeterinarian() == null || request.getNameVeterinarian().isEmpty() ||
                request.getVeterinarianId() == null || request.getVeterinarianId() <= 0){

            //Aca hacemos el HashMap para pasar Clave valor que va a ser un String, Object
            //Ya que pasamos el String que es el mensaje de error y luego el Objeto que normalmente
            //Seria lo que pasamos en el segundo errorResponse.
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("¡Error!", "Faltan campos requeridos");
            errorResponse.put("Campos requeridos", new String[]{"veterinarianId", "nameVeterinarian"});

            //Aca devolvemmos la respuesta que es un bad request y para ello se almacena la respuesta
            //En el ResponseEntity, ponemos status, porque es el estado y el body porque ahi lo imprimira
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        //Aca llamamos a la clase VetResponse y la instaciamos, luego le damos los valores que debe tener
        //Cada dato que declaramos en su respectiva clase
        VetResponse response = new VetResponse(
                "Disponibilidad verificada",
                true,
                request
        );

        //Aca devolvemos la respuesta, sea positiva o negativa
        return ResponseEntity.ok(response);
    }
}
