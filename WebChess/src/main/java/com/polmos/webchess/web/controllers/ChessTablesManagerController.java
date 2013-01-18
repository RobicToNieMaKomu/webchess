package com.polmos.webchess.web.controllers;

import com.polmos.webchess.matchmgnt.entity.ChessTable;
import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.service.ChessTableService;
import com.polmos.webchess.matchmgnt.service.MatchService;
import java.util.Date;
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
public class ChessTablesManagerController {
    
    @Autowired
    private ChessTableService chessTableService;
    
    @ModelAttribute("allChessTables")
    public List<ChessTable> populateAllChessTables() {
        return chessTableService.getAllChessTables();
    }
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String showAllChessTables() {
        return "welcome";
    }
  

    @RequestMapping(value = {"/allTables", "/welcome/allTables"}, method = RequestMethod.GET)
    public @ResponseBody
    List<ChessTable> showAllVisibleChessTables() {
        return chessTableService.getAllChessTables();
    }
    
    @RequestMapping(value = {"/createTable", "/welcome/createTable"}, method = RequestMethod.POST)
    public @ResponseBody Integer createChessTable() {
        Integer chessTableId = chessTableService.createNewChessTable(new Date());
        return chessTableId;
    }
    
    @RequestMapping(value = {"/main", "/welcome/main"}, method = RequestMethod.GET)
    public String mainPage() {
        return "main";
    }
    
    @RequestMapping(value = {"/table", "/welcome/table"}, method = RequestMethod.GET)
    public String createTable() {
        return "table";
    }
}