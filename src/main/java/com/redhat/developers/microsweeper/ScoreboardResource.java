package com.redhat.developers.microsweeper;

import com.redhat.developers.microsweeper.model.Score;
import com.redhat.developers.microsweeper.service.ScoreboardService;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/api/scoreboard")
public class ScoreboardResource {

    @Inject
    ScoreboardService scoreboardService;

    final Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    @Timed(description = "Time taken to fetch score from CosmosDB")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Score> getScoreboard() {
        return scoreboardService.getScoreboard();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addScore(Score score) throws Exception {
        scoreboardService.addScore(score);
    }

    @DELETE
    public void clearAll() throws Exception {
        scoreboardService.clearScores();
    }

}
