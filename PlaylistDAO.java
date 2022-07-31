
import com.sun.source.tree.WhileLoopTree;

import javax.naming.ldap.PagedResultsResponseControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlaylistDAO {
    Connection con;

    private void addToPlayList(String PlaylistName, int trackid, int user_id) throws SQLException {
        List<Playlist> playlistList=selectplaylist(PlaylistName);
        if(playlistList.isEmpty())
        {
            CreatePlaylist(PlaylistName, trackid,user_id);
        }
        else
            saveToPlaylist(playlistList.get(0).getPlayListId(),PlaylistName,trackid,user_id);

    }

    public List selectplaylist(String playlistName) throws SQLException {
        con=DBConfiguration.getConnection();
        PreparedStatement ps1= con.prepareStatement("select * from playlist where playlist_Name=?;");
        ps1.setString(1,playlistName);
        ResultSet rs1=ps1.executeQuery();

        List<Playlist> playlistList=new ArrayList<>();
        while (rs1.next())
        {
            playlistList.add(new Playlist(rs1.getInt(1),rs1.getString(2),rs1.getInt(3)));
        }
        return playlistList;
    }

    public void CreatePlaylist(String playlistName, int trackid, int user_id) throws SQLException {
        con=DBConfiguration.getConnection();
        PreparedStatement ps2=con.prepareStatement("select max(playlist_Id) from playlist;");
        ResultSet rs2=ps2.executeQuery();
        int playlistId;
        if(rs2.next())
            playlistId=rs2.getInt(1);
        else
            playlistId=1;
        saveToPlaylist(playlistId,playlistName,trackid, user_id);
    }

    public void saveToPlaylist(int playlistId, String PlaylistName, int trackid, int user_id) throws SQLException {
        con=DBConfiguration.getConnection();
        PreparedStatement ps3=con.prepareStatement("insert into playlist(playlist_name, song_id, user_id) values(?,?,?) ;");
        //ps3.setInt(1,playlistId);
        ps3.setString(1,PlaylistName);
        ps3.setInt(2,trackid);
        ps3.setInt(3,user_id);

        if(ps3.executeUpdate()==1)
        {
            System.out.println("Track Added to playlist");
        }
    }

    public void addToPlayListPod(String PlaylistName, int trackid, int user_id) throws SQLException {
        con=DBConfiguration.getConnection();
        PreparedStatement ps3=con.prepareStatement("insert into playlist(playlist_name, podcast_id, user_id) values(?,?,?) ;");
        //ps3.setInt(1,playlistId);
        ps3.setString(1,PlaylistName);
        ps3.setInt(2,trackid);
        ps3.setInt(3,user_id);

        if(ps3.executeUpdate()==1)
        {
            System.out.println("Track Added to playlist");
        }
    }

    public  void displayPlayListName(int userid)
    {
        try
        {
            con=DBConfiguration.getConnection();
            PreparedStatement ps4=con.prepareStatement("select distinct(playlist_Name) from playlist where user_id="+userid);
            ResultSet rs4=ps4.executeQuery();
            while (rs4.next())
            {
                System.out.println(rs4.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Playlist> viewonePlaylist(String playlistname, int userid) throws SQLException {
            con=DBConfiguration.getConnection();
            PreparedStatement ps5=con.prepareStatement("select so.songid,so.song_name,so.song_path * from playlist pl join song so" +
                    "(select * from playlist where playlist_name="+playlistname +" and user_id="+userid+")" +
                    "where pl.song_id=so.songid;");
            ResultSet rs5=ps5.executeQuery();
            List<Playlist> playlistList=new ArrayList<>();
            while (rs5.next())
            {
                playlistList.add(new Playlist(rs5.getInt(1),rs5.getString(2),rs5.getInt(3)));
            }
        return playlistList;
    }

    public void displayPlaylist(List<Playlist> playlistList,List<song> songList,List<podCast> podCastList) {
        System.out.format("%30s%35s%40s%20s\n", "TrackID", "Track Title", "Track Artist", "Track Duration");
        for (Playlist playlist : playlistList) {
            Optional<song> song1 = songList.stream().filter(song -> song.getSongId() == playlist.getTrackId()).findAny();
            Optional<podCast> podcast = podCastList.stream().filter(podCast -> podCast.getPodId() == playlist.getTrackId()).findAny();
            if (song1.isPresent())
            {

                System.out.format("%30s%35s%40s%20s\n", song1.get().getSongId(), song1.get().getSongName(), song1.get().getArtistName(), song1.get().getSongDuration());

            }
            else if (podcast.isPresent()) {
                    //System.out.println("inside playlist");
                    System.out.format("%30s%35s%40s%20s\n", podcast.get().getPodId(), podcast.get().getPodName(), podcast.get().getArtistName(), podcast.get().getPodDur());
            }
            else
                System.out.println("No track available");


        }
    }

    public void chooseTrack(List<song> songList,List<podCast> podcastList,String PlaylistName,int userid) throws SQLException{
        System.out.println("1)Add Song 2) Add Podcast 3) add Album");
        int choice;
        Scanner sc = new Scanner(System.in);
        choice=sc.nextInt();
        switch(choice)
        {
            case 1:
                System.out.println("Enter the SongId");
                int songid;
                songid=sc.nextInt();
                sc.nextLine();
                Optional<song> song =songList.stream().filter(song1 -> song1.getSongId()==songid).findAny();
                if(song.isPresent())
                    addToPlayList(PlaylistName,songid, userid);
                break;
            case 2:
                System.out.println("Enter the PodcastId");
                int podcastid;
                podcastid=sc.nextInt();
                sc.nextLine();
                Optional<podCast> podCast=podcastList.stream().filter(podcast1 -> podcast1.getPodId()==podcastid).findAny();
                if(podCast.isPresent())
                    addToPlayListPod(PlaylistName,podcastid,userid);
                break;
            case 3:
                System.out.println("Enter the Album Name:");
                String album;
                album=sc.nextLine();
                sc.nextLine();
                List<song> albumSong=songList.stream().filter(song1-> song1.getAlbumName().equalsIgnoreCase(album)).collect(Collectors.toList());

                for(song song1:albumSong)
                    addToPlayList(PlaylistName,song1.getSongId(),userid);
                break;


        }
    }

}
