import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainJukeBox {

    public static void main(String[] args) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Scanner sc = new Scanner(System.in);
        song s = new song();
        podCast p=new podCast();
        SongDAO po = new SongDAO();
        LogIn logIn = new LogIn();
        System.out.println( "===========================================WELCOME TO WORLD OF MUSIC========================================== ");
        List<song> songList = null;
        List<podCast> podcastList;
        List<song> songList2 ;
        List<podCast> podcastList2;
        try
        {
            songList=po.viewsong();
            songList2=po.viewsong();

            String filePath = "H:\\NIIT\\JukeBox\\On The Floor.wav";
            SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer(filePath);

            audioPlayer.play();
            Scanner sc1 = new Scanner(System.in);

            while (true)
            {
                System.out.println("1. pause");
                System.out.println("2. resume");
                System.out.println("3. restart");
                System.out.println("4. stop");
                System.out.println("5. Jump to specific time");
                int c = sc1.nextInt();
                audioPlayer.gotoChoice(c);
                if (c == 4)
                    break;
            }
            //sc.close();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Want to 1:Sign Up        2:Sign In ");
        int ch=sc.nextInt();
        switch (ch)
        {
            case 1:
                System.out.println("Are you ready to share your email_id with us");
                String ans= sc.next();
                if (ans.equalsIgnoreCase("yes"))
                {
                    logIn.createNewAccount();
                }
                else
                {
                    System.out.println("Your Free Trail Period Ended");
                    System.out.println("Thanks for visiting");
                    System.exit(1);
                }
            case 2:
                System.out.println("Enter E-Mail id-");
                String mail=sc.next();
                String msg= logIn.login(mail);
                if(msg.equals("success"))
                {

                        songList2 = po.viewsong();
                        System.out.println( "===========================================Song List============================================");
                        po.displayAllSongs(songList2);

                        PodcastDAO po1=new PodcastDAO();
                        podcastList = po1.viewPodCast();
                        System.out.println( "=========================================PodCast List===========================================");
                        po1.displayAllPodCast(podcastList);

                    Connection con;
                    con=DBConfiguration.getConnection();
                    PreparedStatement ps1= con.prepareStatement("select * from user where user_email=?");
                    ps1.setString(1,mail);
                    ResultSet rs1=ps1.executeQuery();
                    rs1.next();
                    int user_id=rs1.getInt(1);

                    while(true)
                    {
                        System.out.println("=====================ALL OPERATION========================");
                        System.out.println("1.Play from above list");
                        System.out.println("2.Play from existing play list");
                        System.out.println("3.Crete new play list");
                        System.out.println("4.Search song by name");
                        System.out.println("5.Search song by album name");
                        System.out.println("6.Search song by artist name");
                        System.out.println("7.Search song by genere");
                        System.out.println("=============================================================================================================");
                        int choice=sc.nextInt();


                            switch (choice) {
                                case 1:
                                    System.out.println("Select What you want to play 1.Song      2.Podcast");
                                    int seloption=sc.nextInt();
                                if(seloption==1) {
                                    System.out.println("Enter song id to play");
                                    int userSongId = sc.nextInt();
                                    PreparedStatement ps2 = con.prepareStatement("select song_path from song where songid=?");
                                    ps2.setInt(1, userSongId);
                                    ResultSet rs2 = ps2.executeQuery();
                                    rs2.next();
                                    String userFilePath = rs2.getString(1);
                                    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer(userFilePath);
                                    audioPlayer.play();
                                    while (true) {
                                        System.out.println("1. pause");
                                        System.out.println("2. resume");
                                        System.out.println("3. restart");
                                        System.out.println("4. stop");
                                        System.out.println("5. Jump to specific time");
                                        int c = sc.nextInt();
                                        audioPlayer.gotoChoice(c);
                                        if (c == 4)
                                            break;
                                    }
                                }
                                else if(seloption==2)
                                {
                                    System.out.println("Enter pod id to play");
                                    int userpodid = sc.nextInt();
                                    PreparedStatement ps2 = con.prepareStatement("select podcast_path from podcast where podcast_id=?");
                                    ps2.setInt(1, userpodid);
                                    ResultSet rs2 = ps2.executeQuery();
                                    rs2.next();
                                    String userFilePath = rs2.getString(1);
                                    SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer(userFilePath);
                                    audioPlayer.play();
                                    while (true) {
                                        System.out.println("1. pause");
                                        System.out.println("2. resume");
                                        System.out.println("3. restart");
                                        System.out.println("4. stop");
                                        System.out.println("5. Jump to specific time");
                                        int c = sc.nextInt();
                                        audioPlayer.gotoChoice(c);
                                        if (c == 4)
                                            break;
                                    }
                                }
                                else
                                    break;

                                    break;

                                case 2:
                                    PlaylistDAO obj = new PlaylistDAO();
                                    System.out.println("select the playlist to view");
                                    obj.displayPlayListName(user_id);
                                    System.out.println("Enter the playlist name");
                                    String playlistName = sc.next();
                                    System.out.println("=================================================================================================");
                                    obj.displayPlaylist(obj.selectplaylist(playlistName), songList2, podcastList);
                                    System.out.println("=================================================================================================");
                                    System.out.println("Want to add song in this playlist-");
                                    String add=sc.next();
                                    if(add.equalsIgnoreCase("yes"))
                                    {
                                        System.out.println("Enter the Playlistname:");
                                        String name = sc.next();
                                        obj.chooseTrack(songList,podcastList,name,user_id);
                                    }

                                    break;

                                case 3:
                                    PlaylistDAO obj2 = new PlaylistDAO();
                                    System.out.println("Enter the Playlistname:");
                                    String name = sc.next();
                                    obj2.chooseTrack(songList,podcastList,name,user_id);

                                break;

                                case 4:
                                    System.out.println("Enter song name :");
                                    String sname=sc.next();
                                    po.displayAllSongs(po.searchByName(sname,songList2));

                                case 5:
                                    System.out.println("Enter the AlbumName:");
                                    String Album1;
                                    Album1=sc.next();
                                    po.displayAllSongs(po.searchByAlbum(Album1,songList2));
                                break;

                                case 6:
                                    System.out.println("Enter the ArtistName:");
                                    String Artist1;
                                    Artist1=sc.next();
                                    po.displayAllSongs(po.searchByArtist(Artist1,songList2));
                                break;

                                case 7:
                                    System.out.println("Enter the SongGenre:");
                                    String Sgenre;
                                    Sgenre=sc.next();
                                    po.displayAllSongs(po.searchByGenre(Sgenre,songList2));
                                break;

                                default:
                                    System.exit(1);


                            }

                    }


                }
                else
                {
                    System.out.println("Log in Failed");
                }
        }


    }

}
