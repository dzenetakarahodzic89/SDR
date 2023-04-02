package ba.com.zira.sdr.api;

import java.util.ArrayList;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.message.response.PayloadResponse;
import ba.com.zira.sdr.api.model.battle.ActivePlayerTeamUpdateRequest;
import ba.com.zira.sdr.api.model.battle.EligibleArtistsInformation;
import ba.com.zira.sdr.api.model.battle.TeamInformation;
import ba.com.zira.sdr.api.model.battle.TurnCombatState;

public interface BattleTurnService {

    /**
     * Gets the playing team by turn id.
     *
     * @param request
     *            the request
     * @return the playing team by turn id
     * @throws ApiException
     *             the api exception
     */
    PayloadResponse<TeamInformation> getPlayingTeamByBattleId(EntityRequest<Long> request) throws ApiException;

    /**
     * Gets the eligible artists information by country id.
     *
     * @param request
     *            the request
     * @return the eligible artists information by country id
     * @throws ApiException
     *             the api exception
     */
    public PayloadResponse<EligibleArtistsInformation> getEligibleArtistsInformationByCountryId(EntityRequest<ArrayList<Long>> request)
            throws ApiException;

    public PayloadResponse<String> updateTeam(EntityRequest<ActivePlayerTeamUpdateRequest> request) throws ApiException;

    public PayloadResponse<TurnCombatState> getBattleLogs(EntityRequest<Long> request) throws ApiException;
}
