package com.decimeshi.internal.decisions;

import com.decimeshi.internal.decisions.model.*;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/internal/decisions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InternalDecisionResource {

    @Inject
    DecisionService service;

    // ---------------------------------------------------------
    // Single
    // ---------------------------------------------------------

    @GET
    @Path("/{id}")
    public DecisionEntity get(@PathParam("id") UUID id) {
        return service.getDecision(id);
    }

    // ---------------------------------------------------------
    // Bulk
    // ---------------------------------------------------------

    @POST
    @Path("/bulk")
    public List<DecisionEntity> bulk(BulkDecisionRequest request) {
        return service.getBulk(request);
    }

    // ---------------------------------------------------------
    // Search
    // ---------------------------------------------------------

    @POST
    @Path("/search")
    public List<DecisionEntity> search(DecisionSearchRequest request) {
        return service.search(request);
    }

    // ---------------------------------------------------------
    // Purge
    // ---------------------------------------------------------

    @POST
    @Path("/purge")
    public PurgeResult purge(PurgeRequest request) {
        return service.purge(request);
    }
}
