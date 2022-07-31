public class Playlist {
    int PlayListId;
    String PlayListName;
    int trackId;

    public int getPlayListId() {
        return PlayListId;
    }

    public void setPlayListId(int playListId) {
        PlayListId = playListId;
    }

    public String getPlayListName() {
        return PlayListName;
    }

    public void setPlayListName(String playListName) {
        PlayListName = playListName;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public Playlist(int playListId, String playListName, int trackId) {
        PlayListId = playListId;
        PlayListName = playListName;
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "PlayListId=" + PlayListId +
                ", PlayListName='" + PlayListName + '\'' +
                ", trackId=" + trackId +
                '}';
    }
}
