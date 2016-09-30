package com.example.money.conatusapp.TeamMembers.currentTeam;

/**
 * Created by #money on 9/30/2016.
 */

public class Member {
    private String name;
    private String branch;
    private String year;
    private String domain;
    private String image;

    public Member() {

    }

    public Member(String name, String image, String domain, String year, String branch) {
        this.name = name;
        this.image = image;
        this.domain = domain;
        this.year = year;
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }


}
