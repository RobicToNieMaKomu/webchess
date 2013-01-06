package com.polmos.webchess.web.controllers;

import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import com.polmos.webchess.matchmgnt.service.MatchService;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author RobicToNieMaKomu
 */
@Controller
public class MatchManagerController {
    
    @Autowired
    private MatchService matchService;
    
    @ModelAttribute("allMatches")
    public List<Match> populateAllMatches() {
        return matchService.findAllMatches();
    }
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String showAllMatches() {
        return "welcome";
    }
    private static int count = 10;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.POST)
    public @ResponseBody
    List<Match> showCurrentMatches() {
        Match match = new Match();
        match.setMatchId(count);
        match.setProgress("progres!" + new Long(Calendar.getInstance().getTimeInMillis()).toString());
        count++;
        matchService.addMatch(match);
        return matchService.findAllMatches();
    }
}
