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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RobicToNieMaKomu
 */
@Entity
@Table(name = "match", catalog = "web_chess", schema = "webchess_schema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Match.findAll", query = "SELECT m FROM Match m"),
    @NamedQuery(name = "Match.findByMatchId", query = "SELECT m FROM Match m WHERE m.matchId = :matchId"),
    @NamedQuery(name = "Match.findByProgress", query = "SELECT m FROM Match m WHERE m.progress = :progress")})
public class Match implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "match_id")
    private Integer matchId;
    @Size(max = 2147483647)
    @Column(name = "progress")
    private String progress;
    @JoinColumn(name = "bplayerid", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User bplayerid;
    @JoinColumn(name = "wplayerid", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User wplayerid;

    public Match() {
    }

    public Match(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public User getBplayerid() {
        return bplayerid;
    }

    public void setBplayerid(User bplayerid) {
        this.bplayerid = bplayerid;
    }

    public User getWplayerid() {
        return wplayerid;
    }

    public void setWplayerid(User wplayerid) {
        this.wplayerid = wplayerid;
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
