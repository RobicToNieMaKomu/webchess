package com.polmos.webchess.web.controllers;

import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.service.MatchService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    @RequestMapping({"/","/welcome"})
    public String showMatches() {
        return "welcome";
    }
}
