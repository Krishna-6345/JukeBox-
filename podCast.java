import java.sql.Date;
import java.sql.Time;


public class podCast {
    String PodName;
    Time PodDur;
    int PodId;
    Date PodDate;
    String podpath;
    String ArtistName;
    int artistid;

    public String getPodName() {
        return PodName;
    }

    public void setPodName(String podName) {
        PodName = podName;
    }

    public Time getPodDur() {
        return PodDur;
    }

    public void setPodDur(Time podDur) {
        PodDur = podDur;
    }

    public int getPodId() {
        return PodId;
    }

    public void setPodId(int podId) {
        PodId = podId;
    }

    public Date getPodDate() {
        return PodDate;
    }

    public void setPodDate(Date podDate) {
        PodDate = podDate;
    }

    public String getPodpath() {
        return podpath;
    }

    public void setPodpath(String podGenre) {
        podpath = podGenre;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public int getArtistid() {
        return artistid;
    }

    public void setAlbumName(String artistid) {
        artistid = artistid;
    }

    public podCast(int podId,String podName, Time podDur , Date podDate, String podpath, String artistName, int artistid) {
        PodName = podName;
        PodDur = podDur;
        PodId = podId;
        PodDate = podDate;
        this.podpath = podpath;
        ArtistName = artistName;
        this.artistid = artistid;
    }

    public podCast()
    {

    }

    @Override
    public String toString() {
        return "podCast{" +
                "PodName='" + PodName + '\'' +
                ", PodDur=" + PodDur +
                ", PodId=" + PodId +
                ", PodDate=" + PodDate +
                ", PodGenre='" + podpath + '\'' +
                ", ArtistName='" + ArtistName + '\'' +
                ", AlbumName='" + podpath + '\'' +
                '}';
    }
}
