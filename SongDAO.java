import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SongDAO {
    static Connection con;

    static Scanner sc= new Scanner(System.in);

    public List<song> viewsong()
    {
        List<song> songList=new ArrayList<>();

        try
        {
            con=DBConfiguration.getConnection();
            PreparedStatement ps1=con.prepareStatement("select * from song;");
            ResultSet rs1=ps1.executeQuery();

            while(rs1.next())
            {
                songList.add(new song(rs1.getInt(1),rs1.getString(2),rs1.getTime(3),rs1.getString(4),
                        rs1.getString(7),rs1.getString(8), rs1.getString(9)));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return songList;
    }

    public  List<song> searchByArtist(String artistName, List<song> list1)
    {
        List<song> songByArtist=list1.stream().filter(song->song.getSongpath().equalsIgnoreCase(artistName)).collect(Collectors.toList());
        return songByArtist;
    }

    public  List<song> searchByName(String artistName, List<song> list1)
    {
        List<song> songByName=list1.stream().filter(song->song.getSongName().equalsIgnoreCase(artistName)).collect(Collectors.toList());
        return songByName;
    }

    public List<song> searchByAlbum(String albumName,List<song> songList)
    {
        List <song> songByAlbum=songList.stream().filter(song ->song.getAlbumName().equalsIgnoreCase(albumName)).collect(Collectors.toList());
        return songByAlbum;
    }

    public List<song> searchByGenre(String genreName, List<song> songList)
    {
        List<song> songByGenre=songList.stream().filter(song ->song.getSongGenre().equalsIgnoreCase(genreName)).collect(Collectors.toList());
        return songByGenre;
    }

    public void  displayAllSongs(List<song> songList)
    {
        System.out.format("%-10s%-30s%-20s%-20s%-30s%-10s%-30s\n\n", "SongId", "SongName", "SongDuration", "SongGenre", "AlbumName", "ArtistName", "SongPath");

        for(song s:songList)
        {
            System.out.format("%-10s%-30s%-20s%-20s%-30s%-10s%-30s\n", s.getSongId(), s.getSongName(), s.getSongDuration(), s.getSongGenre(), s.getAlbumName(), s.getSongpath(),s.getArtistName());
        }
    }

}
