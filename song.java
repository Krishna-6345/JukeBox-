import java.sql.Time;

public class song {
    int SongId;
    String SongName;
    Time SongDuration;
    String SongGenre;
    String ArtistName;
    String AlbumName;

    public String getSongpath() {
        return Songpath;
    }

    public void setSongpath(String songpath) {
        Songpath = songpath;
    }

    String Songpath;

    public int getSongId() {
        return SongId;
    }

    public void setSongId(int songId) {
        SongId = songId;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public Time getSongDuration() {
        return SongDuration;
    }

    public void setSongDuration(Time songDuration) {
        SongDuration = songDuration;
    }

    public String getSongGenre() {
        return SongGenre;
    }

    public void setSongGenre(String songGenre) {
        SongGenre = songGenre;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public song(int songId, String songName, Time songDuration, String songGenre, String artistName, String albumName,String songpath)
    {
        SongId = songId;
        SongName = songName;
        SongDuration = songDuration;
        SongGenre = songGenre;
        ArtistName = artistName;
        AlbumName = albumName;
        Songpath=songpath;
    }

    public song()
    {

    }

    @Override
    public String toString() {
        return "song{" +
                "SongId=" + SongId +
                ", SongName='" + SongName + '\'' +
                ", SongDuration=" + SongDuration +
                ", SongGenre='" + SongGenre + '\'' +
                ", ArtistName='" + ArtistName + '\'' +
                ", AlbumName='" + AlbumName + '\'' +
                '}';
    }
}
