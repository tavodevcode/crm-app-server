package com.crmapp.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "content", length = 150, nullable = false)
    private String content;

    @Column(name = "date", length = 150, nullable = false)
    private String date;

    @Column(name = "url", length = 150, nullable = false)
    private String url;

    @Column(name = "likes", nullable = false)
    private int likes;

    @Column(name = "shares", nullable = false)
    private int shares;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    // constructors
    public Post() {
    }

    public Post(UUID id, String title, String content, String date, String url, int likes, int shares, UUID user_id,
            User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.url = url;
        this.likes = likes;
        this.shares = shares;
        this.user = user;
    }

    // getters and setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;

    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return this.date;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return this.url;

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLikes() {
        return this.likes;

    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return this.shares;

    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
