package com.example.assignment1.Model;
/*
import org.litepal.crud.LitePalSupport;

public class Repo extends LitePalSupport {
    private String author;
    private String name;
    private String avatar;
    private String url;
    private String description;
    private String language;
    private String languageColor;
    private int stars;
    private int forks;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }
}
*/

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
/**
 * Immutable model class for a Repository.
 * Repository的不可变模型类。
 */
@Entity(tableName = "repos")
public final class Repo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    private final String mUrl;

    @ColumnInfo(name = "name")
    private final String mName;

    @Nullable
    @ColumnInfo(name = "author")
    private final String mAuthor;

    @Nullable
    @ColumnInfo(name = "avatar")
    private final String mAvatar;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "language")
    private final String mLanguage;

    @Nullable
    @ColumnInfo(name = "languageColor")
    private final String mLanguageColor;

    @ColumnInfo(name = "stars")
    private final int mStars;

    @ColumnInfo(name = "forks")
    private final int mForks;

    /**
     * @param author  the name of the repository's author
     * @param avatar the avatar of the author of the repository
     * @param name the name of the repository
     * @param url the url of the repository
     * @param description the description of the repository
     * @param language the name of language
     * @param languageColor the color rgb of language
     * @param stars the number of stars
     * @param forks the number of forks
     */
    public Repo(@Nullable String author, @Nullable String avatar, @Nullable String name, @NonNull String url, @Nullable String description, @Nullable String language, @Nullable String languageColor, int stars, int forks) {
        mAuthor = author;
        mAvatar = avatar;
        mDescription = description;
        mName = name;
        mForks = forks;
        mStars = stars;
        mUrl = url;
        mLanguage = language;
        mLanguageColor = languageColor;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    public String getName() {
        return mName;
    }

    @Nullable
    public String getAuthor() {
        return mAuthor;
    }

    @Nullable
    public String getAvatar() {
        return mAvatar;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getLanguage() {
        return mLanguage;
    }

    @Nullable
    public String getLanguageColor() {
        return mLanguageColor;
    }

    public int getStars() {
        return mStars;
    }

    public int getForks() {
        return mForks;
    }
}