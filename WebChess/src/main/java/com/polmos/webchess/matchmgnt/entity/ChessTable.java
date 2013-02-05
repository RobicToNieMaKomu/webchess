/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.matchmgnt.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RobicToNieMaKomu
 */
@Entity
@Table(name = "chess_table", catalog = "web_chess", schema = "webchess_schema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChessTable.findAll", query = "SELECT c FROM ChessTable c"),
    @NamedQuery(name = "ChessTable.count", query = "SELECT COUNT(c) FROM ChessTable c"),
    @NamedQuery(name = "ChessTable.findByTableId", query = "SELECT c FROM ChessTable c WHERE c.tableId = :tableId"),
    @NamedQuery(name = "ChessTable.findByGameTime", query = "SELECT c FROM ChessTable c WHERE c.gameTime = :gameTime"),
    @NamedQuery(name = "ChessTable.findByLastVisitTimestamp", query = "SELECT c FROM ChessTable c WHERE c.lastVisitTimestamp = :lastVisitTimestamp")})
public class ChessTable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "table_id")
    private Integer tableId;
    @Column(name = "game_time")
    private Integer gameTime;
    @Basic(optional = false)
    @Column(name = "last_visit_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitTimestamp;
    @JoinColumn(name = "wplayer", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User wplayer;
    @JoinColumn(name = "bplayer", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User bplayer;
    @Column(name = "game_started")
    private Boolean gameStarted;

    public Boolean getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(Boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public ChessTable() {
    }

    public ChessTable(Integer tableId) {
        this.tableId = tableId;
    }

    public ChessTable(Integer tableId, Date lastVisitTimestamp) {
        this.tableId = tableId;
        this.lastVisitTimestamp = lastVisitTimestamp;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    public void setGameTime(Integer gameTime) {
        this.gameTime = gameTime;
    }

    public Date getLastVisitTimestamp() {
        return lastVisitTimestamp;
    }

    public void setLastVisitTimestamp(Date lastVisitTimestamp) {
        this.lastVisitTimestamp = lastVisitTimestamp;
    }

    public User getWplayer() {
        return wplayer;
    }

    public void setWplayer(User wplayer) {
        this.wplayer = wplayer;
    }

    public User getBplayer() {
        return bplayer;
    }

    public void setBplayer(User bplayer) {
        this.bplayer = bplayer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableId != null ? tableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChessTable)) {
            return false;
        }
        ChessTable other = (ChessTable) object;
        if ((this.tableId == null && other.tableId != null) || (this.tableId != null && !this.tableId.equals(other.tableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polmos.webchess.matchmgnt.entity.ChessTable[ tableId=" + tableId + " ]";
    }
    
}
