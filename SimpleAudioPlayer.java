import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SimpleAudioPlayer {

    //to store current position
    Long currentFrame;
    Clip clip;

    // To store current status of clip

    String status;

    AudioInputStream audioInputStream;
    private  String filePath;

    //constructor to initialize streams and clips

    public SimpleAudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        // create AudioInputStream object
        audioInputStream= AudioSystem.getAudioInputStream(new File(filePath));

        //create clip reference
        clip=AudioSystem.getClip();

        this.filePath=filePath;

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void gotoChoice(int c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {


            switch (c) {
                case 1:
                    pause();
                    break;

                case 2:

                    resumeAudio();
                    break;

                case 3:
                    restart();
                    break;

                case 4:
                    stop();
                    break;

                case 5:
                    System.out.println("Enter time (" + 0 + "," + clip.getMicrosecondLength() + ")");
                    Scanner sc = new Scanner(System.in);
                    long c1 = sc.nextLong();
                    jump(c1);
                    break;

            }

    }

    // method to play audio

    public void play()
    {
        clip.start();
        status="play";
    }

    // method to pause the audio

    public void pause()
    {
        if(status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame=this.clip.getMicrosecondPosition();
        clip.stop();
        status="paused";
    }

    //Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (status.equals("play"))
        {
            System.out.println("audio is already being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio
    public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame=0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    //Method to stop the audio

    public void stop()
    {
        currentFrame=0L;
        clip.stop();
        clip.close();
    }

    // Jump method over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(c>0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame=c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    // Method to reset audio stream

    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream=AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
