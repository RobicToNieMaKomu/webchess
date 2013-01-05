package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author RobicToNieMaKomu
 */
@Service("matchService")
public class MatchServiceImpl implements MatchService{

    @Override
    public List<Match> findAllMatches() {
        // TODO - add db handling
        List<Match> result = new ArrayList<Match>();
        User user = new User();
        user.setId(1);
        user.setLogin("Zdzislaw");
        for (int i = 0; i < 10; i++){
            Match match = new Match();
            match.setBplayerid(user);
            match.setMatchId(i);
            match.setProgress(new Long(Calendar.getInstance().getTimeInMillis()).toString());
            result.add(match);
        }
        return result;
    }
    
}
