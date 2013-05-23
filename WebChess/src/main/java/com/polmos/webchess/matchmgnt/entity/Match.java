/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.matchmgnt.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author NWVT34
 */
@Entity
@Table(name = "match", catalog = "web_chess", schema = "webchess_schema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Match.findAll", query = "SELECT m FROM Match m"),
    @NamedQuery(name = "Match.findByMatchId", query = "SELECT m FROM Match m WHERE m.matchId = :matchId"),
    @NamedQuery(name = "Match.findByTableId", query = "SELECT m FROM Match m WHERE m.tableid = :tableid"),
    @NamedQuery(name = "Match.findNotEndedByTableId", query = "SELECT m FROM Match m WHERE m.hasended=FALSE AND m.tableid=:tableid"),
    @NamedQuery(name = "Match.findByBplayerTime", query = "SELECT m FROM Match m WHERE m.bplayerTime = :bplayerTime"),
    @NamedQuery(name = "Match.findByHasended", query = "SELECT m FROM Match m WHERE m.hasended = :hasended"),
    @NamedQuery(name = "Match.findByProgress", query = "SELECT m FROM Match m WHERE m.progress = :progress"),
    @NamedQuery(name = "Match.findByWplayerTime", query = "SELECT m FROM Match m WHERE m.wplayerTime = :wplayerTime")})
public class Match implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "match_id")
    private Integer matchId;
    @Basic(optional = false)
    @Column(name = "bplayer_time")
    private int bplayerTime;
    @Basic(optional = false)
    @Lob
    @Column(name = "chessboard")
    private byte[] chessboard;
    @Column(name = "hasended")
    private Boolean hasended;
    @Column(name = "progress")
    private String progress;
    @Basic(optional = false)
    @Column(name = "wplayer_time")
    private int wplayerTime;
    @JoinColumn(name = "wplayer", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User wplayer;
    @JoinColumn(name = "bplayer", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User bplayer;
    @JoinColumn(name = "tableid", referencedColumnName = "table_id")
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private ChessTable tableid;

    public Match() {
    }

    public Match(Integer matchId) {
        this.matchId = matchId;
    }

    public Match(Integer matchId, int bplayerTime, byte[] chessboard, int wplayerTime) {
        this.matchId = matchId;
        this.bplayerTime = bplayerTime;
        this.chessboard = chessboard;
        this.wplayerTime = wplayerTime;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public int getBplayerTime() {
        return bplayerTime;
    }

    public void setBplayerTime(int bplayerTime) {
        this.bplayerTime = bplayerTime;
    }

    public byte[] getChessboard() {
        return chessboard;
    }

    public void setChessboard(byte[] chessboard) {
        this.chessboard = chessboard;
    }

    public Boolean getHasended() {
        return hasended;
    }

    public void setHasended(Boolean hasended) {
        this.hasended = hasended;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getWplayerTime() {
        return wplayerTime;
    }

    public void setWplayerTime(int wplayerTime) {
        this.wplayerTime = wplayerTime;
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

    public ChessTable getTableid() {
        return tableid;
    }

    public void setTableid(ChessTable tableid) {
        this.tableid = tableid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matchId != null ? matchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if ((this.matchId == null && other.matchId != null) || (this.matchId != null && !this.matchId.equals(other.matchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polmos.webchess.matchmgnt.entity.Match[ matchId=" + matchId + " ]";
    }
    
}
