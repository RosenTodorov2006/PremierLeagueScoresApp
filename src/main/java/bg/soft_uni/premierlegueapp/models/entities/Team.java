package bg.soft_uni.premierlegueapp.models.entities;

import bg.soft_uni.premierlegueapp.models.entities.enums.TeamNames;
import jakarta.persistence.*;

import java.time.LocalDate;

@Table
@Entity(name = "teams")
public class Team extends BaseEntity{
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamNames name;
    @Column(unique = true)
    private String initials;
    @Column
    private LocalDate created;
    @Column
    private double budget;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Competition competition;
    @Column(nullable = false, columnDefinition = "VARCHAR(2000)", name = "history_small_info")
    private String historySmallInfo;
    @Column(nullable = false, columnDefinition = "VARCHAR(2000)", name = "history_big_info")
    private String historyBigInfo;
    @Column(nullable = false, columnDefinition = "VARCHAR(2000)", name = "fans_small_info")
    private String fansSmallInfo;
    @Column(nullable = false, columnDefinition = "VARCHAR(2000)", name = "fans_big_info")
    private String fansBigInfo;
    @Column(nullable = false, columnDefinition = "VARCHAR(2000)", name = "trophies_small_info")
    private String trophiesSmallInfo;
    @Column(nullable = false, columnDefinition = "VARCHAR(2000)", name = "trophies_big_info")
    private String trophiesBigInfo;
    @ManyToOne
    @JoinColumn(name = "kit_color", referencedColumnName = "id")
    private Color kitColor;
    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    public Team(Competition competition, TeamNames name, String initials, LocalDate created, double budget, String historySmallInfo, String historyBigInfo, String fansSmallInfo, String fansBigInfo, String trophiesSmallInfo, String trophiesBigInfo, Color kitColor, Town town) {
        this.name = name;
        this.initials = initials;
        this.created = created;
        this.budget = budget;
        this.historySmallInfo = historySmallInfo;
        this.historyBigInfo = historyBigInfo;
        this.fansSmallInfo = fansSmallInfo;
        this.fansBigInfo = fansBigInfo;
        this.trophiesSmallInfo = trophiesSmallInfo;
        this.trophiesBigInfo = trophiesBigInfo;
        this.kitColor = kitColor;
        this.town = town;
        this.competition=competition;
    }

    public Team() {

    }

    public TeamNames getName() {
        return name;
    }

    public void setName(TeamNames name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }


    public Color getKitColor() {
        return kitColor;
    }

    public void setKitColor(Color kitColor) {
        this.kitColor = kitColor;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getHistorySmallInfo() {
        return historySmallInfo;
    }

    public void setHistorySmallInfo(String historySmallInfo) {
        this.historySmallInfo = historySmallInfo;
    }

    public String getHistoryBigInfo() {
        return historyBigInfo;
    }

    public void setHistoryBigInfo(String historyBigInfo) {
        this.historyBigInfo = historyBigInfo;
    }

    public String getFansSmallInfo() {
        return fansSmallInfo;
    }

    public void setFansSmallInfo(String fansSmallInfo) {
        this.fansSmallInfo = fansSmallInfo;
    }

    public String getFansBigInfo() {
        return fansBigInfo;
    }

    public void setFansBigInfo(String fansBigInfo) {
        this.fansBigInfo = fansBigInfo;
    }

    public String getTrophiesSmallInfo() {
        return trophiesSmallInfo;
    }

    public void setTrophiesSmallInfo(String trophiesSmallInfo) {
        this.trophiesSmallInfo = trophiesSmallInfo;
    }

    public String getTrophiesBigInfo() {
        return trophiesBigInfo;
    }

    public void setTrophiesBigInfo(String trophiesBigInfo) {
        this.trophiesBigInfo = trophiesBigInfo;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
