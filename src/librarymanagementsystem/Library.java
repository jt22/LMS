/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author justine
 */
@Entity
@Table(name = "library", catalog = "appdev", schema = "")
@NamedQueries({
    @NamedQuery(name = "Library.findAll", query = "SELECT l FROM Library l")
    , @NamedQuery(name = "Library.findByIsbn", query = "SELECT l FROM Library l WHERE l.isbn = :isbn")
    , @NamedQuery(name = "Library.findByTitle", query = "SELECT l FROM Library l WHERE l.title = :title")
    , @NamedQuery(name = "Library.findByAuthor", query = "SELECT l FROM Library l WHERE l.author = :author")
    , @NamedQuery(name = "Library.findByPublisher", query = "SELECT l FROM Library l WHERE l.publisher = :publisher")})
public class Library implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ISBN")
    private Integer isbn;
    @Basic(optional = false)
    @Column(name = "Title")
    private String title;
    @Basic(optional = false)
    @Column(name = "Author")
    private String author;
    @Basic(optional = false)
    @Column(name = "Publisher")
    private String publisher;

    public Library() {
    }

    public Library(Integer isbn) {
        this.isbn = isbn;
    }

    public Library(Integer isbn, String title, String author, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        Integer oldIsbn = this.isbn;
        this.isbn = isbn;
        changeSupport.firePropertyChange("isbn", oldIsbn, isbn);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldTitle = this.title;
        this.title = title;
        changeSupport.firePropertyChange("title", oldTitle, title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        String oldAuthor = this.author;
        this.author = author;
        changeSupport.firePropertyChange("author", oldAuthor, author);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        String oldPublisher = this.publisher;
        this.publisher = publisher;
        changeSupport.firePropertyChange("publisher", oldPublisher, publisher);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Library)) {
            return false;
        }
        Library other = (Library) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "librarymanagementsystem.Library[ isbn=" + isbn + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
