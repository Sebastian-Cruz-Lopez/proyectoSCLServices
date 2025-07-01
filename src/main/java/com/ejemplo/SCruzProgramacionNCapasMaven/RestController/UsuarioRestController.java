
package com.ejemplo.SCruzProgramacionNCapasMaven.RestController;

import com.ejemplo.SCruzProgramacionNCapasMaven.DAO.UsuarioJPADAOImplementation;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarioapi") 
public class UsuarioRestController {
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        
        try{
            Result result = usuarioJPADAOImplementation.GetAll();
            
            if (result.correct) {
                if (result.objects.size() == 0) {
                    
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vacio");
                    
                } else{
                    return ResponseEntity.ok().body(result);
                }
            } else{
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(result);
            }
            
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getLocalizedMessage());
        }
        
    }
    
}
