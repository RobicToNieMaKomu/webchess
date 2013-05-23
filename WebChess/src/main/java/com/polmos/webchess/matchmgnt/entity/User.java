/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.matchmgnt.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author NWVT34
 */
@Entity
@Table(name = "user", catalog = "web_chess", schema = "webchess_schema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "wplayer", fetch = FetchType.EAGER)
    private Set<ChessTable> chessTableSet;
    @OneToMany(mappedBy = "bplayer", fetch = FetchType.EAGER)
    private Set<ChessTable> chessTableSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wplayer", fetch = FetchType.EAGER)
    private Set<Match> matchSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bplayer", fetch = FetchType.EAGER)
    private Set<Match> matchSet1;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    @JsonIgnore
    public Set<ChessTable> getChessTableSet() {
        return chessTableSet;
    }

    public void setChessTableSet(Set<ChessTable> chessTableSet) {
        this.chessTableSet = chessTableSet;
    }

    @XmlTransient
    @JsonIgnore
    public Set<ChessTable> getChessTableSet1() {
        return chessTableSet1;
    }

    public void setChessTableSet1(Set<ChessTable> chessTableSet1) {
        this.chessTableSet1 = chessTableSet1;
    }

    @XmlTransient
    @JsonIgnore
    public Set<Match> getMatchSet() {
        return matchSet;
    }

    public void setMatchSet(Set<Match> matchSet) {
        this.matchSet = matchSet;
    }

    @XmlTransient
    @JsonIgnore
    public Set<Match> getMatchSet1() {
        return matchSet1;
    }

    public void setMatchSet1(Set<Match> matchSet1) {
        this.matchSet1 = matchSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return login;
    }
    
}
