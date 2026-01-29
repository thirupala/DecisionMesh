package com.decimeshi.api.resource;

import com.decimeshi.internal.decisions.DecisionService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

@Path("/v1/decisions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Decisions", description = "Decision management endpoints")
public class DecisionResource {

    private static final Logger LOG = Logger.getLogger(DecisionResource.class);

    @Inject
    DecisionService decisionService;

    @POST
    @Operation(
            summary = "Create a new decision",
            description = "Submit a decision request for AI processing"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "202",
                    description = "Decision accepted for processing",
                    content = @Content(schema = @Schema(implementation = DecisionResponse.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid request"
            )
    })
    public Response createDecision(@Valid DecisionRequest request) {
        LOG.infof("Received decision request: %s", request.getDecisionId());

        DecisionResponse response = decisionService.processDecision(request);

        return Response.status(Response.Status.ACCEPTED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get decision by ID",
            description = "Retrieve the status and result of a decision"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Decision found",
                    content = @Content(schema = @Schema(implementation = DecisionResponse.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Decision not found"
            )
    })
    public Response getDecision(
            @Parameter(description = "Decision ID", required = true)
            @PathParam("id") String id
    ) {
        LOG.infof("Get decision: %s", id);

        DecisionResponse response = new DecisionResponse(
                id,
                "pending",
                "Decision retrieval not yet implemented"
        );

        return Response.ok(response).build();
    }
}