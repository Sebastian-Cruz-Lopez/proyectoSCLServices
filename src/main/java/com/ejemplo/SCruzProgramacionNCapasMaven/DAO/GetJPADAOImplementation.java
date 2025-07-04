package com.ejemplo.SCruzProgramacionNCapasMaven.DAO;

import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Colonia;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Estado;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Municipio;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Pais;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Roll;
import com.ejemplo.SCruzProgramacionNCapasMaven.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GetJPADAOImplementation implements IGetJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetRoll() {
        Result result = new Result();
        try {
            TypedQuery<Roll> rollsQuery = entityManager.createQuery(
                    "FROM Roll", Roll.class);

            List<Roll> rolls = rollsQuery.getResultList();
            result.objects = new ArrayList<>();

            if (!rolls.isEmpty() && rolls != null) {
                for (Roll rollJPA : rolls) {
                    Roll roll = rollJPA;
                    result.objects.add(roll);
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

    @Override
    public Result GetPais() {
        Result result = new Result();
        try {
            TypedQuery<Pais> paisesQuery = entityManager.createQuery(
                    "FROM Pais", Pais.class);

            List<Pais> paises = paisesQuery.getResultList();
            result.objects = new ArrayList<>();

            if (!paises.isEmpty() && paises != null) {
                for (Pais paisJPA : paises) {
                    Pais pais = paisJPA;
                    result.objects.add(pais);
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

    @Override
    public Result GetEstado(int idPais) {
        Result result = new Result();
        try {
            TypedQuery<Estado> estadosQuery = entityManager.createQuery("FROM Estado WHERE Pais.idPais = :idPais", Estado.class);
            estadosQuery.setParameter("idPais", idPais);

            List<Estado> estados = estadosQuery.getResultList();
            result.objects = new ArrayList<>();

            if (!estados.isEmpty() && estados != null) {
                for (Estado estadoJPA : estados) {
                    Estado estado = new Estado();
                    estado = estadoJPA;
                    result.objects.add(estado);
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

    @Override
    public Result GetMunicipio(int idEstado) {
        Result result = new Result();
        try {
            TypedQuery<Municipio> municipiosQuery = entityManager.createQuery(
                    "FROM Municipio WHERE Estado.idEstado = :idEstado", Municipio.class);
            municipiosQuery.setParameter("idEstado", idEstado);

            List<Municipio> municipios = municipiosQuery.getResultList();
            result.objects = new ArrayList<>();

            if (!municipios.isEmpty() && municipios != null) {
                for (Municipio municipioJPA : municipios) {
                    Municipio municipio = municipioJPA;
                    result.objects.add(municipio);
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

    @Override
    public Result GetColonia(int idMunicipio) {
        Result result = new Result();
        try {
            TypedQuery<Colonia> coloniasQuery = entityManager.createQuery(
                    "FROM Colonia WHERE Municipio.idMunicipio = :idMunicipio", Colonia.class);
            coloniasQuery.setParameter("idMunicipio", idMunicipio);

            List<Colonia> colonias = coloniasQuery.getResultList();
            result.objects = new ArrayList<>();

            if (!colonias.isEmpty() && colonias != null) {
                for (Colonia coloniaJPA : colonias) {
                    Colonia colonia = coloniaJPA;
                    result.objects.add(colonia);
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

//    @Override
//    public Result GetColoniaByCP(String codigoPostal) {
//        Result result = new Result();
//        try {
//            List<Colonia> colonias = entityManager.createQuery(
//                    "SELECT c FROM Colonia c WHERE c.codigoPostal = :cp", Colonia.class)
//                    .setParameter("cp", codigoPostal)
//                    .getResultList();
//
//            result.objects = new ArrayList<>(colonias);
//            result.correct = true;
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getMessage();
//            result.ex = ex;
//        }
//        return result;
//    }
}
