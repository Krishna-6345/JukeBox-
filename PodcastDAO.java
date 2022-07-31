import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PodcastDAO {

    static Connection con;
    static Scanner sc= new Scanner(System.in);

//    public void addpod(podCast pod) throws SQLException {
//        PreparedStatement ps1=con.prepareStatement("insert into podcast(?,?,?,?,?,?,?)");
//        ps1.setInt(1,pod.getPodId());
//        ps1.setString(2,pod.getPodName());
//        ps1.setTime(3,pod.getPodDur());
//        ps1.setDate(4,pod.getPodDate());
//        ps1.setString(5,pod.getPodGenre());
//        ps1.setString(6, pod.getAlbumName());
//        ps1.setString(7, pod.getArtistName());
//
//        ps1.executeUpdate();
//    }

    public List<podCast> viewPodCast()
    {
        List<podCast> podCastList=new ArrayList<>();

        try
        {
            con=DBConfiguration.getConnection();
            PreparedStatement ps2=con.prepareStatement("select * from podcast;");
            ResultSet rs1= ps2.executeQuery();

            while (rs1.next())
            {
                podCastList.add(new podCast(rs1.getInt(1),rs1.getString(2),rs1.getTime(4),
                        rs1.getDate(3),rs1.getString(7), rs1.getString(6), rs1.getInt(5)));

            }
            System.out.println("Size of podcast list is "+podCastList.size());

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return  podCastList;
    }

    public void displayAllPodCast(List<podCast> podCastList)
    {
        System.out.format("%-10s%-30s%-20s%-20s%-20s%-10s%-10s\n", "podcastId", "podcastName","podcastDuration","podcastdate" ,
                "podpath", "ArtistName", "ArtistID");

        for (podCast pod:podCastList)
        {
            System.out.format("%-10s%-30s%-20s%-20s%-20s%-10s%-10s\n", pod.getPodId(), pod.getPodName(), pod.getPodDur(), pod.getPodDate(),pod.getPodpath(),
                    pod.getArtistName(),pod.getArtistid());

        }

    }

    public List<podCast> searchByCelebrityName(String podArtist, List<podCast> podCastList)
    {
        List<podCast> podByArtist=podCastList.stream().filter(podCast -> podCast.getArtistName().startsWith(podArtist)).collect(Collectors.toList());
        return  podByArtist;
    }

    public  List<podCast> searchByDate(String date, List<podCast> podCastList)
    {
        List<podCast> podByDate=podCastList.stream().filter(podCast -> podCast.getPodDate().equals(Date.valueOf(String.valueOf(date)))).collect(Collectors.toList());
        return  podByDate;
    }

}
