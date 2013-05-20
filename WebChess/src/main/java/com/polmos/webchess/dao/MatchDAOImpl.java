package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.Match;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Piotrek
 */
@Service(value = "matchDAO")
public class MatchDAOImpl implements MatchDAO{

    private static Logger logger = Logger.getLogger(UserDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public Integer saveMatch(Match match) {
        entityManager.persist(match);
        entityManager.flush();
        logger.debug(match + " saved");
        return match.getMatchId();
    }

    @Transactional
    @Override
    public Match findMatchById(Integer id) {
        Match result = entityManager.find(Match.class, id);
        return result;
    }

    @Transactional
    @Override
    public Set<Match> findAllMatchs() {
        Set<Match> result = new HashSet<>();
        Query createNamedQuery = entityManager.createNamedQuery("Match.findAll");
        createNamedQuery.getResultList();
       
        return result;
    }

    @Transactional
    @Override
    public void removeMatch(Match match) {
        Match merge = entityManager.merge(match);
        entityManager.remove(merge);
        logger.debug(match + " removed");
    }

    @Transactional
    @Override
    public Match findMatchByTableId(Integer tableId) {
        Match result = null;
        Query query = entityManager.createNamedQuery("Match.findByTableId");
        query.setParameter("tableid", tableId);
        List<Match> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            // ReturnList should always have at most 1 element (0 or 1)
            result = resultList.get(0);
        }
        return result;
    }
    
}
