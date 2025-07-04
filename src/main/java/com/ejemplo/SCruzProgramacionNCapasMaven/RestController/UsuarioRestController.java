package com.ejemplo.SCruzProgramacionNCapasMaven.RestController;

import com.ejemplo.SCruzProgramacionNCapasMaven.DAO.GetJPADAOImplementation;
import com.ejemplo.SCruzProgramacionNCapasMaven.DAO.UsuarioJPADAOImplementation;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Colonia;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Direccion;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Result;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.ResultValidaDatos;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.ResultValidaDatos;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Roll;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Usuario;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.UsuarioDireccion;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/usuarioapi")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Autowired
    private GetJPADAOImplementation getJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetAll() {

        try {
            Result result = usuarioJPADAOImplementation.GetAll();

            if (result.correct) {
                if (result.objects.size() == 0) {

                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vacio");

                } else {
                    return ResponseEntity.ok().body(result);
                }
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getLocalizedMessage());
        }

    }

    @PostMapping("/adduser")
    public ResponseEntity Add(@RequestBody UsuarioDireccion usuarioDireccion) {

        try {

            Result result = usuarioJPADAOImplementation.Add(usuarioDireccion);

            if (result.correct) {
                return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(result);
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getLocalizedMessage());
        }

    }

    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity DeleteUsuario(@PathVariable int idUsuario) {

        try {

            Result result = usuarioJPADAOImplementation.DeleteUsuario(idUsuario);

            if (result.correct) {

                return ResponseEntity.ok("Usuario Eliminado");

            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getLocalizedMessage());
        }

    }

    @DeleteMapping("/{idUsuario}/deletedireccion/{idDireccion}")
    public ResponseEntity DeleteDireccion(@PathVariable int idUsuario, @PathVariable int idDireccion) {
        try {
            Result result = usuarioJPADAOImplementation.DeleteDireccion(idUsuario, idDireccion);

            if (result.correct) {
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
        }
    }

    @GetMapping("/getbyid/{idUsuario}")
    public ResponseEntity GetByid(@PathVariable int idUsuario) {

        try {

            Result result = usuarioJPADAOImplementation.GetByid(idUsuario);

            if (result.correct) {

                return ResponseEntity.ok().body(result);

            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getLocalizedMessage());
        }

    }

    @GetMapping("/getdireccionbyid/{idDireccion}")
    public ResponseEntity<?> GetDireccionById(@PathVariable int idDireccion) {
        Result result = usuarioJPADAOImplementation.GetDireccionByid(idDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result.object);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.errorMessage);
        }
    }

    @PostMapping("/adddireccion")
    public ResponseEntity AddDireccion(@RequestBody UsuarioDireccion usuarioDireccion) {

        try {

            Result result = usuarioJPADAOImplementation.AddDireccion(usuarioDireccion);

            if (result.correct) {
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(result);
            }

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getLocalizedMessage());

        }

    }

    @PatchMapping("/updateusuario")
    public ResponseEntity<Result> UpdateUsuario(@RequestBody UsuarioDireccion usuarioDireccion) {
        Result result = usuarioJPADAOImplementation.UpdateUsario(usuarioDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PutMapping("/{idUsuario}/updatedireccion/{idDireccion}")
    public ResponseEntity<Result> updateDireccion(@PathVariable int idUsuario, @PathVariable int idDireccion,
            @RequestBody Direccion direccionJSON) {

        Result result = usuarioJPADAOImplementation.UpdateDireccion(idUsuario, idDireccion, direccionJSON);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/rolls")
    public ResponseEntity<?> GetRoll() {
        Result result = getJPADAOImplementation.GetRoll();
        if (result.correct) {
            return ResponseEntity.ok(result.objects);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.errorMessage);
        }
    }

    @GetMapping("/paises")
    public ResponseEntity<?> GetPais() {
        Result result = getJPADAOImplementation.GetPais();
        if (result.correct) {
            return ResponseEntity.ok(result.objects);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.errorMessage);
        }
    }

    @GetMapping("/estados/{idPais}")
    public ResponseEntity<?> GetEstado(@PathVariable int idPais) {
        Result result = getJPADAOImplementation.GetEstado(idPais);
        if (result.correct) {
            return ResponseEntity.ok(result.objects);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.errorMessage);
        }
    }

    @GetMapping("/municipios/{idEstado}")
    public ResponseEntity<?> GetMunicipio(@PathVariable int idEstado) {
        Result result = getJPADAOImplementation.GetMunicipio(idEstado);
        if (result.correct) {
            return ResponseEntity.ok(result.objects);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.errorMessage);
        }
    }

    @GetMapping("/colonias/{idMunicipio}")
    public ResponseEntity<?> GetColonia(@PathVariable int idMunicipio) {
        Result result = getJPADAOImplementation.GetColonia(idMunicipio);
        if (result.correct) {
            return ResponseEntity.ok(result.objects);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.errorMessage);
        }
    }

    private static final Path UPLOAD_DIR = Paths.get("uploads");
    private static final Path LOG_DIR = Paths.get("logs");
    private static final Path LOG_FILE = LOG_DIR.resolve("archivo.log");
    private static final String SECRET_KEY = "1234567890abcdef";

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(UPLOAD_DIR);
        Files.createDirectories(LOG_DIR);
        if (Files.notExists(LOG_FILE)) {
            Files.createFile(LOG_FILE);
        }
    }

    @PostMapping("/archivo/cargar")
    public ResponseEntity<Result> cargarArchivo(@RequestParam MultipartFile file) {
        Result result = new Result();
        String observaciones = "";
        boolean estatus;

        try {
            String nombre = Objects.requireNonNull(file.getOriginalFilename()).replace("\\s+", "_");
            Path destino = UPLOAD_DIR.resolve(nombre).toAbsolutePath();
            file.transferTo(destino);

            byte[] contenido = Files.readAllBytes(destino);
            List<String> errores = validar(contenido, nombre);

            if (errores.isEmpty()) {
                estatus = true;
            } else {
                estatus = false;
                observaciones = String.join(";", errores);
            }

            String llave = cifrarAES(destino.toString());
            escribirLog(llave, estatus, observaciones);

            if (estatus) {
                result.correct = true;
                result.object = List.of(llave);
            } else {
                result.correct = false;
                result.errorMessage = observaciones;
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getMessage();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/archivo/procesar")
    public ResponseEntity<Result> procesarArchivo(@RequestBody Map<String, String> request) throws Exception {
        Result result = new Result();
        String llave = request.get("llave");

        if (llave == null || llave.isEmpty()) {
            result.correct = false;
            result.errorMessage = "Llave no proporcionada";
            return ResponseEntity.badRequest().body(result);
        }

        try {
            List<String> lineas = Files.readAllLines(LOG_FILE);
            Optional<String> lineaEncontrada = lineas.stream()
                    .filter(l -> l.startsWith(llave + " |"))
                    .findFirst();

            if (lineaEncontrada.isEmpty()) {
                result.correct = false;
                result.errorMessage = "Llave no encontrada en el log";
                return ResponseEntity.ok(result);
            }

            String[] parts = lineaEncontrada.get().split("\\|");
            boolean status = Boolean.parseBoolean(parts[1].trim());
            String observacion = parts[3];

            if (!status) {
                result.correct = false;
                result.errorMessage = "Archivo inválido: " + observacion;
                return ResponseEntity.ok(result);
            }

            if (observacion.contains("Procesado Correctamente")) {
                result.correct = false;
                result.errorMessage = "Este archivo ya fue procesado anteriormente.";
                return ResponseEntity.ok(result);
            }

            // Se descifra la llave AES
            String pathArchivo = descifrarAES(llave);

            // Lectura del archivo y proceso
            System.out.println("Procesando archivo en: " + pathArchivo);

            // Actualizacion del log 
            marcarComoProcesado(llave);

            result.correct = true;
            result.mensaje = "Archivo procesado correctamente";

        } catch (IOException e) {
            result.correct = false;
            result.errorMessage = e.getMessage();
        }

        return ResponseEntity.ok(result);
    }

    private List<String> validar(byte[] contenido, String nombre) {
        List<String> errores = new ArrayList<>();
        if (contenido.length == 0) {
            errores.add("Archivo vacío");
        }
        if (!nombre.toLowerCase().endsWith(".txt")) {
            errores.add("Extensión no permitida");
        }
        return errores;
    }

    private String cifrarAES(String strToEncrypt) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        final SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    private String descifrarAES(String strToDecrypt) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        final SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }

    private void escribirLog(String llave, boolean status, String observacion) throws IOException {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String linea = String.join(" | ",
                llave,
                String.valueOf(status),
                fecha,
                observacion
        );
        Files.write(
                LOG_FILE,
                List.of(linea),
                StandardOpenOption.APPEND
        );
    }

    private void marcarComoProcesado(String llave) throws IOException {
        List<String> lines = Files.readAllLines(LOG_FILE);
        List<String> nuevasLineas = new ArrayList<>();

        for (String l : lines) {
            if (l.startsWith(llave + " |") && !l.contains("procesado")) {
                String actualizado = l.trim() + " (procesado)";
                nuevasLineas.add(actualizado);
            } else {
                nuevasLineas.add(l);
            }
        }

        Files.write(LOG_FILE, nuevasLineas, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
