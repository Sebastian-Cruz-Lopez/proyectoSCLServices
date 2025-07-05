package com.ejemplo.SCruzProgramacionNCapasMaven.DAO;

import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Direccion;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Result;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.UsuarioDireccion;

public interface IUsuarioJPADAO {

    Result Add(UsuarioDireccion usuarioDireccion);

    Result DeleteUsuario(int idUsuario);

    Result DeleteDireccion(int idUsuario, int idDireccion);
    
    Result UpdateUsario(UsuarioDireccion usuarioDireccion);
    
    Result UpdateDireccion(int idUsuario, int idDireccion, Direccion direccionJSON);
    
    Result AddDireccion(UsuarioDireccion usuarioDireccion); 
    
    Result GetAll();
    
    Result GetByid(int idUsuario);
    
    Result GetDireccionByid(int idDireccion);
    
    Result GetColoniaByCP(String codigoPostal);

}
