package com.ejemplo.SCruzProgramacionNCapasMaven.DAO;

import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Colonia;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Direccion;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Estado;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Municipio;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Pais;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Roll;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Usuario;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Result;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Path;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO {

    @Autowired
    private EntityManager entityManager;

    //TERMINADO
    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = usuarioDireccion.Usuario;

            entityManager.persist(usuarioJPA);
            entityManager.flush();

            Direccion direccion = usuarioDireccion.Direccion;
            direccion.Usuario = new Usuario();
            direccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());

            entityManager.persist(direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.objects = null;
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    //TERMINADO
    @Transactional
    @Override
    public Result DeleteUsuario(int idUsuario) {
        Result result = new Result();
        try {
            Query deleteDirecciones = entityManager.createQuery("DELETE FROM Direccion d WHERE d.usuario.idUsuario = :idUsuario");
            deleteDirecciones.setParameter("idUsuario", idUsuario);
            deleteDirecciones.executeUpdate();

            Query deleteUsuario = entityManager.createQuery("DELETE FROM Usuario u WHERE u.idUsuario = :idUsuario");
            deleteUsuario.setParameter("idUsuario", idUsuario);
            deleteUsuario.executeUpdate();

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    //FALTA AGREGARLO A LA VISTA
    @Transactional
    @Override
    public Result DeleteDireccion(int idUsuario, int idDireccion) {
        Result result = new Result();
        try {
            entityManager.find(Direccion.class, idDireccion);

            Query deleteDireccion = entityManager.createQuery("DELETE FROM Direccion d WHERE d.idDireccion = :idDireccion");
            deleteDireccion.setParameter("idDireccion", idDireccion);
            deleteDireccion.executeUpdate();

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    //FALTA AGREGARLO A VISTA
    @Transactional
    @Override
    public Result UpdateUsario(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuario = usuarioDireccion.Usuario;

            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "Datos del usuario inválidos.";
                return result;
            }

            entityManager.merge(usuario);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    //FALTA AGREGARLO A VISTA
    @Transactional
    @Override
    public Result UpdateDireccion(int idUsuario, int idDireccion, Direccion direccionJSON) {
        Result result = new Result();

        try {
            Direccion direccion = entityManager.find(Direccion.class, idDireccion);

            direccion.setCalle(direccionJSON.getCalle());
            direccion.setNumeroInterior(direccionJSON.getNumeroInterior());
            direccion.setNumeroExterior(direccionJSON.getNumeroExterior());

            if (direccionJSON.Colonia != null) {
                direccion.Colonia = entityManager.getReference(Colonia.class, direccionJSON.Colonia.getIdColonia());
            }

            entityManager.merge(direccion);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    //TERMINADO
    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {

            TypedQuery<Usuario> usuariosQuery = entityManager.createQuery("FROM Usuario ORDER BY idUsuario ASC", Usuario.class
            );

            List<Usuario> usuarioJPA = usuariosQuery.getResultList();

            for (Usuario usuario : usuarioJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = usuario;

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.idUsuario = :idUsuario", Direccion.class
                );
                direccionesQuery.setParameter("idUsuario", usuario.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = direccionesJPA;

                }

                result.objects.add(usuarioDireccion);

            }
            result.correct = true;

        } catch (Exception ex) {
            result.object = null;
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    //FALTA AGREGARLO A LA VISTA
    @Override
    public Result GetByid(int idUsuario) {
        Result result = new Result();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);

            if (usuarioJPA == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado con el ID: " + idUsuario;
                return result;
            }

            usuarioJPA.getDirecciones().size(); // forza el fetch

            result.correct = true;
            result.object = usuarioJPA; // regresa la entidad JPA directamente

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    //FALTA AGREGAR A VISTA
    @Transactional
    @Override
    public Result AddDireccion(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuario = entityManager.find(Usuario.class, usuarioDireccion.Usuario.getIdUsuario());

            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                return result;
            }

            Direccion direccion = usuarioDireccion.Direccion;
            direccion.Usuario = usuario;
            entityManager.persist(direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    //FALTA AGREGARLO A VISTA
    @Transactional
    @Override
    public Result GetDireccionByid(int idDireccion) {
        Result result = new Result();

        try {
            Direccion direccionJPA = entityManager.find(Direccion.class, idDireccion);

            direccionJPA.Colonia.getNombre();
            direccionJPA.Colonia.Municipio.getNombre();
            direccionJPA.Colonia.Municipio.Estado.getNombre();
            direccionJPA.Colonia.Municipio.Estado.Pais.getNombre();

            result.correct = true;
            result.object = direccionJPA;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetColoniaByCP(String codigoPostal) {
        Result result = new Result();
        result.objects = new ArrayList<>(); 
        try {
            TypedQuery<Direccion> direccionQuery = entityManager.createQuery(
                    "FROM Direccion d WHERE d.Colonia.CodigoPostal = :codigoPostal", Direccion.class);
            direccionQuery.setParameter("codigoPostal", codigoPostal);

            List<Direccion> direcciones = direccionQuery.getResultList();
            if (direcciones != null) {
                for (Direccion direccionesJPA : direcciones) {
                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia = direccionesJPA.Colonia;
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio = direccionesJPA.Colonia.Municipio;
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado = direccionesJPA.Colonia.Municipio.Estado;
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais = direccionesJPA.Colonia.Municipio.Estado.Pais;

                    result.objects.add(direccion);
                }
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
