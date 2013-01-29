package com.polmos.webchess.web.controllers;

import com.polmos.webchess.enums.HtmlElements;
import com.polmos.webchess.matchmgnt.entity.ChessTable;
import com.polmos.webchess.matchmgnt.service.ChessTableService;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author RobicToNieMaKomu
 */
@Controller
public class ChessTablesManagerController {

    private final static Logger logger = Logger.getLogger(ChessTablesManagerController.class);
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

    /*
     * TODO - check if this method is really needed
     */
    @RequestMapping(value = {"/createTable", "/welcome/createTable"}, method = RequestMethod.POST)
    public @ResponseBody
    Integer createChessTable() {
        Integer chessTableId = chessTableService.createNewChessTable(new Date());
        return chessTableId;
    }

    @RequestMapping(value = "/table", method = RequestMethod.GET)
    public String getChessTable(@RequestParam(value = "chessTableId") String chessTableId, final ModelMap model) {
        // This stands for .../table?chessTableId=X in the URL
        try {
            Integer tableId = Integer.parseInt(chessTableId);
            ChessTable chessTable = chessTableService.findChessTable(tableId);
            if (chessTable != null) {
                model.addAttribute(HtmlElements.TABLE_ID, chessTableId);
                model.addAttribute(HtmlElements.TABLE_GAME_TIME, chessTable.getGameTime());
                model.addAttribute(HtmlElements.TABLE_WPLAYER, chessTable.getWplayer());
                model.addAttribute(HtmlElements.TABLE_BPLAYER, chessTable.getBplayer());
                model.addAttribute(HtmlElements.TABLE_ERROR, false);
            } else {
                model.addAttribute(HtmlElements.TABLE_ERROR, true);
            }
        } catch (Exception ex) {
            logger.error("Exception caught during retrieving info about table: " + ex);
        }
        return "table";
    }

    @RequestMapping(value = {"/login", "/welcome/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = {"/logout", "/welcome/logout"}, method = RequestMethod.GET)
    public String logoutPage() {
        return "logout";
    }

    @RequestMapping(value = {"/loginfailed", "/welcome/loginfailed"}, method = RequestMethod.GET)
    public String loginFailed(final ModelMap model) {
        model.addAttribute("loginFailed", true);
        return "login";
    }
}
