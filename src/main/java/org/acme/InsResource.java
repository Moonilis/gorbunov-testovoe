package org.acme;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("insproduct")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class InsResource {

    private static final Logger LOGGER = Logger.getLogger(InsProduct.class.getName());

    @Inject
    EntityManager entityManager;

    @Inject
    EntityManagerFactory entityManagerFactory;

    @GET
    public List<InsProduct> get() {
        return entityManager.createNamedQuery("InsProduct.findAll", InsProduct.class)
                .getResultList();
    }

    @GET
    @Path("{id}")
    public InsProduct getSingle(@PathParam("id") Integer id) {
        InsProduct doc = entityManager.find(InsProduct.class, id);
        if (doc == null) {
            throw new WebApplicationException("InsProduct with id " + id + " does not exist.", 404);
        }
        return doc;
    }

    @POST
    @Transactional
    public Response create(InsProduct entity) {
        if ((entity.getId() != null) && (entity.getDocnumber() != null)) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        entityManager.persist(entity);
        return Response.ok(entity).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public InsProduct update(@PathParam("id") Integer id, InsProduct doc) {
        InsProduct entity = entityManager.find(InsProduct.class, id);
        if (entity == null) {
            throw new WebApplicationException("Doc with id " + id + " does not exist.", 404);
        }
        if (doc.getDocnumber() != null)
            entity.setDocnumber(doc.getDocnumber());
        if (doc.getDatestart() != null)
            entity.setDatestart(doc.getDatestart());
        if (doc.getDatestart() != null)
           entity.setDatefinish(doc.getDatefinish());
        if (doc.getDatefinish() != null)
            entity.setInssum(doc.getInssum());
        if (doc.getProdver() != null)
            entity.setProdver(doc.getProdver());

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id) {
        InsProduct entity = entityManager.getReference(InsProduct.class, id);
        if (entity == null) {
            throw new WebApplicationException("Doc with id of " + id + " does not exist.", 404);
        }
        entityManager.remove(entity);
        return Response.status(204).build();
    }

    @POST
    @Path("filter")
    public List<InsProduct> getFiltered(InsFilter filter){
//        LOGGER.info("Begin method");
        EntityManager manager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<InsProduct> cq = cb.createQuery(InsProduct.class);
        Root<InsProduct> root = cq.from(InsProduct.class);
        cq.select(root);

        List<Predicate> criteria = new ArrayList<Predicate>();
        if (filter.getProdver() != null) {
//            LOGGER.info("prodver");
            criteria.add(cb.equal(root.get("prodver"),filter.getProdver()));
        }
        if (filter.getMinsum() != null) {
//            LOGGER.info("minsum");
            criteria.add(cb.greaterThanOrEqualTo(root.get("inssum"), filter.getMinsum()));
        }
        if (filter.getMaxsum() != null) {
//            LOGGER.info("maxsum");
            criteria.add(cb.lessThanOrEqualTo(root.get("inssum"), filter.getMaxsum()));
        }
        if (filter.getDatestart() != null) {
//            LOGGER.info("datestart");
            criteria.add(cb.greaterThanOrEqualTo(root.get("datestart"), filter.getDatestart()));
        }
        if (filter.getDatefinish() != null) {
//            LOGGER.info("datefinish");
            criteria.add(cb.lessThanOrEqualTo(root.get("datefinish"), filter.getDatefinish()));
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        List<InsProduct> insProductList = manager.createQuery(cq).getResultList();
        return insProductList;
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
