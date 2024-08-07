package bg.soft_uni.premierlegueapp.models.entities;

import bg.soft_uni.premierlegueapp.models.entities.enums.CompetitionNames;
import jakarta.persistence.*;

@Table
@Entity(name = "competitions")
public class Competition extends BaseEntity{
    @Column
    @Enumerated(EnumType.STRING)
    private CompetitionNames name;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "trophy_url")
    private String trophyUrl;

    public Competition(CompetitionNames name, String videoUrl, String trophyUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.trophyUrl = trophyUrl;
    }

    public Competition() {

    }

    public CompetitionNames getName() {
        return name;
    }

    public void setName(CompetitionNames name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTrophyUrl() {
        return trophyUrl;
    }

    public void setTrophyUrl(String trophyUrl) {
        this.trophyUrl = trophyUrl;
    }
}
