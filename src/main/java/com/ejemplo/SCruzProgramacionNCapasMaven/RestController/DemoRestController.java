//Hola chat, empezamos con el tema de los Web Services, estamos trabajando con PostMan y creamos un Hola Mundo
//pero nos pidieron mas cosas, ayudame con ello pero tambien quiero entender muy bien el porque de todo
package com.ejemplo.SCruzProgramacionNCapasMaven.RestController;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {

    //Hola Mundo
    @GetMapping("saludo")
    public ResponseEntity HolaMundo() {

        return ResponseEntity.accepted().body("Hola Mundo");

    }

    //SUMA DE a y b con parametros en la URL
    @GetMapping("suma")
    public ResponseEntity<String> Suma(@RequestParam int a, @RequestParam int b) {
        int resultado = a + b;
        return ResponseEntity.accepted().body("El resultado de la suma es: " + resultado);
    }

    //Suma de n valores que lleguen por el cuerpo de la peticion
    @PostMapping("sumaN")
    public ResponseEntity<String> SunaN(@RequestBody List<Integer> lista) {

        int suma = 0;
        for (Integer numero : lista) {

            suma += numero;

        }

        return ResponseEntity.accepted().body("La suma de " + lista + " es igual a: " + suma + "");
    }

    //Actuzalizacion de dato n de una lista de elemetos recuperada de la peticion (remplazar por un 0)
    //en caso de que la posicion indicada sea mayor a la del arreglo enviar un badrequest y retonar la lista tal cual
    @PatchMapping("actualizar")
    public ResponseEntity<String> Actualizar(@RequestBody Map<String, Object> numeros) {

        List<Integer> lista = (List<Integer>) numeros.get("numeros");
        int posicion = (int) numeros.get("posicion");

        if (posicion >= lista.size()) {
            return ResponseEntity.badRequest().body("Esa posicion no se encuentra en la lista");
        }
        lista.set(posicion, 0);
        return ResponseEntity.accepted().body("La lista actualizada es: " + lista);
    }

}
