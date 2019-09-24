package com.example.assignment1.Model;

import java.util.List;

public class Repo {

    /**
     * author : testerSunshine
     * name : 12306
     * avatar : https://github.com/testerSunshine.png
     * url : https://github.com/testerSunshine/12306
     * description : 12306智能刷票，订票
     * language : Python
     * languageColor : #3572A5
     * stars : 8950
     * forks : 2718
     * currentPeriodStars : 508
     * builtBy : [{"username":"testerSunshine","href":"https://github.com/testerSunshine","avatar":"https://avatars3.githubusercontent.com/u/20162049"},{"username":"MonsterTan","href":"https://github.com/MonsterTan","avatar":"https://avatars1.githubusercontent.com/u/22610809"},{"username":"cclauss","href":"https://github.com/cclauss","avatar":"https://avatars3.githubusercontent.com/u/3709715"},{"username":"stormeyes","href":"https://github.com/stormeyes","avatar":"https://avatars3.githubusercontent.com/u/5072174"},{"username":"BlancRay","href":"https://github.com/BlancRay","avatar":"https://avatars1.githubusercontent.com/u/9410067"}]
     */

    private String author;
    private String name;
    private String avatar;
    private String url;
    private String description;
    private String language;
    private String languageColor;
    private int stars;
    private int forks;
    private int currentPeriodStars;
    private List<BuiltByBean> builtBy;

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

    public int getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(int currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public List<BuiltByBean> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<BuiltByBean> builtBy) {
        this.builtBy = builtBy;
    }

    public static class BuiltByBean {
        /**
         * username : testerSunshine
         * href : https://github.com/testerSunshine
         * avatar : https://avatars3.githubusercontent.com/u/20162049
         */

        private String username;
        private String href;
        private String avatar;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
