package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.ChessTable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RobicToNieMaKomu
 */
@Transactional
@Service("chessTableDAO")
public class ChessTableDAOImpl implements ChessTableDAO {

    private static Logger logger = Logger.getLogger(UserDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    public Integer createChessTable(ChessTable chessTable) {
        entityManager.persist(chessTable);
        entityManager.flush();
        Integer tableId = chessTable.getTableId();
        return tableId;
    }
    
    public void updateChessTable(ChessTable chessTable) {
        if (entityManager.find(ChessTable.class, chessTable.getTableId()) != null) {
            entityManager.persist(chessTable);
        }
    }

    public ChessTable findChessTableById(Integer id) {
        ChessTable result = entityManager.find(ChessTable.class, id);
        return result;
    }

    public List<ChessTable> findAllChessTables() {
        Query createNamedQuery = entityManager.createNamedQuery("ChessTable.findAll");
        List<ChessTable> result = createNamedQuery.getResultList();
        return result;
    }

    public void removeChessTable(ChessTable chessTable) {
        ChessTable merge = entityManager.merge(chessTable);
        entityManager.remove(merge);
        logger.debug(chessTable + " removed");
    }
}
